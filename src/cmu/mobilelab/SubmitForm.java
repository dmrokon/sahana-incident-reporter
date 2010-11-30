package cmu.mobilelab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableRow.LayoutParams;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import cmu.mobilelab.ImageAdapter;

public class SubmitForm extends Activity{
	
	/*
     * PHOTO GALLERY CONTENT
     */
    
    private static final String TAG = "CameraGallery";
    private static final int CAMERA_ACTIVITY = 0;
    private ArrayList<String> attachedPhotoUris = new ArrayList<String>();
	
	/**
	 * GPS CONTENT
	 */

	private static int UPDATE_TIME = 30000;
	private static int UPDATE_DISTANCE = 500;
	private static final int UPDATE_INTERVAL = 1000 * 60 * 1;
	private Location mCurrentBestLocation = null;
	private LocationManager mLocationManager = null;
	private LocationListener mLocationListener = null;

	private void doGpsSetup() {
		// Acquire a reference to the system Location Manager
		mLocationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		mLocationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				processNewLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				UPDATE_TIME, UPDATE_DISTANCE, mLocationListener);
	}

	public void processNewLocation(Location loc) {
		if (isBetterLocation(loc, mCurrentBestLocation)) {
			mCurrentBestLocation = loc;
		}
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > UPDATE_INTERVAL;
		boolean isSignificantlyOlder = timeDelta < -UPDATE_INTERVAL;
		boolean isNewer = timeDelta > 0;

		// If it's been more than one minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than one minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		}
		return false;
	}
	
	public IncidentLocation getLocationFromAddress(String address)
	{
		IncidentLocation resIncidentLocation = null; 
		
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());    
        try {
            List<Address> addresses = geoCoder.getFromLocationName(
                address, 5);
            String add = "";
            if (addresses.size() > 0) {
            	resIncidentLocation = 
            		new IncidentLocation(address, 
                        (int) (addresses.get(0).getLatitude()), 
                        (int) (addresses.get(0).getLongitude()));
               // mc.animateTo(p);    
               // mapView.invalidate();
            }    
        } catch (IOException e) {
        	Log.i("IO Exception in Reverse Geocoder", e.toString()); 
        }
		
		return resIncidentLocation; 
	}

	public IncidentLocation getCurrentBestLocation() {
		
		IncidentLocation currBestLocation = new IncidentLocation(0,0); 
		
		if (mCurrentBestLocation != null)
		{
			currBestLocation = new IncidentLocation(mCurrentBestLocation.getLatitude(),
					mCurrentBestLocation.getLongitude());
		}
		else {
			// this is just so we can immediately get some location even if it
			// isn't accurate
			Location lastKnownLocation = mLocationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (lastKnownLocation != null)
			{
				currBestLocation = new IncidentLocation(lastKnownLocation.getLatitude(),
						lastKnownLocation.getLongitude());
			}
		}
		
		Geocoder geoCoder = new Geocoder(
                getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                currBestLocation.getLatitude(),
                currBestLocation.getLongitude(), 1); 

            String add = "";
            if (addresses.size() > 0) 
            {
                for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                     i++)
                   add += addresses.get(0).getAddressLine(i) + "\n";
            }

            currBestLocation.setLocationName(add); 
            Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) 
        {                
            Log.i("IO Exception in Geocoder", e.toString()); 
        }
        
        return currBestLocation;     
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLocationManager.removeUpdates(mLocationListener);
	}

	/**
	 * END GPS CONTENT
	 */
	
	public static final String SHARED_PREFERENCES = "incidentreporter_prefs";
	private static final int RESULT_IMAGE_RETURNED = 1;
	
	private IncidentReport newIncidentReport;
	private Impact newImpact = new Impact();
	private IncidentReport.Category newCategory;
	private Reporter newReporter = new Reporter();
	private IncidentLocation newLocation = new IncidentLocation();
	private DatabaseAdapter db = new DatabaseAdapter(this);
	private ArrayList<String> ThumbnailsArray = new ArrayList<String>();
	private ArrayList<String> PicturesArray = new ArrayList<String>();
	private static IncidentReport lastIncidentReportSubmitted = null; 
	
	EditText reporter_name_edittext;
	EditText reporter_contact_edittext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.form);
	    
	    //newImpact = new Impact();
	    doGpsSetup(); 
	    
	    //TODO:Instantiate Database Connection
	    db.open();
	    
	    //Place images
	    if (ThumbnailsArray.size() > 0) {
	    	//loadAttachedImages();
	    }
	    
        //Set Current Timestamp
        TextView timestamp_TextView = (TextView)findViewById(R.id.timestampText);
        Date timestamp = new Date();
        String timestamp_string = timestamp.toString();
        Log.i("timestamp", timestamp_string);
        timestamp_TextView.setText(timestamp_string);
        
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        
        //Create category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        //Add an impact button listener
        Button impactButton = (Button)findViewById(R.id.impact_button);
        impactButton.setOnClickListener(new OnClickListener(){
        	public void onClick(final View view) {
        		
        		//Build impact spinner
        		final Spinner impact_type_spinner = new Spinner(view.getContext());
	        			impact_type_spinner.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT ));
	
	    	        //array adapter for spinner
	    	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	    	                view.getContext(), R.array.impact_array, android.R.layout.simple_spinner_item);
	    	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	        impact_type_spinner.setAdapter(adapter);
    			
	    	    //build impact value edittext
        		final EditText impact_value_edittext = new EditText(view.getContext());
        		impact_value_edittext.addTextChangedListener(new TextWatcher() {

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						String changedText = s.toString();
						if (!(changedText.matches("\\d*"))) {
							String removedDigits = changedText.replaceAll(
									"\\D", "");

							Toast toast = Toast.makeText(view.getContext(),
									"Cannot have non-integer values",
									Toast.LENGTH_SHORT);
							toast.show();

							impact_value_edittext.setText(removedDigits);
						}
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub
					}
				});
        		
        		//create textview labels
        		TextView impact_type_label = new TextView(view.getContext());
        		impact_type_label.setText("Impact type");
        		
        		TextView impact_value_label = new TextView(view.getContext());
        		impact_value_label.setText("Number affected");
        		
        		//programatically implement layout widget
        		LinearLayout impact_widget = new LinearLayout(view.getContext());
        		impact_widget.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT ));
        		impact_widget.setOrientation(LinearLayout.VERTICAL);
        		impact_widget.setPadding(5,5,5,5);
        		impact_widget.addView(impact_type_label);
        		impact_widget.addView(impact_type_spinner);
        		impact_widget.addView(impact_value_label);
        		impact_widget.addView(impact_value_edittext);
        		
        		AlertDialog.Builder impact_builder = new AlertDialog.Builder(view.getContext());
        		impact_builder.setMessage("Please add an impact.")
        		       .setCancelable(true)
        		       .setPositiveButton("Add", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		        	   //Convert dialog data to strings
        		        	   String selected_impact_type_string = impact_type_spinner.getSelectedItem().toString();
        		        	   String selected_impact_value_string = (String)impact_value_edittext.getText().toString();
        		        	   
        		        	   Impact.ImpactType impact_type_enum = Impact.ImpactType.values()[impact_type_spinner.getSelectedItemPosition()];
        		        	   selected_impact_value_string = impact_value_edittext.getText().toString();
        		        	   int impact_value_int = Integer.parseInt(selected_impact_value_string);
        		        	   newImpact.setImpact(impact_type_enum, impact_value_int);
        		        	   
        		        	   //generate new table row
        		        	   final TableRow newRow = new TableRow(view.getContext());
        		        	   
        		        	   //generate textview for type
        		        	   TextView impact_type_view = new TextView(view.getContext());
        		        	   impact_type_view.setText(selected_impact_type_string);
        		        	   impact_type_view.setLayoutParams(new TableRow.LayoutParams(1));
        		        	   impact_type_view.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   impact_type_view.setTextAppearance(getParent(), R.style.Body);
        		        	   
        		        	   //generate textview for value
        		        	   TextView impact_value_view = new TextView(view.getContext());
        		        	   impact_value_view.setText(selected_impact_value_string);
        		        	   impact_value_view.setTextAppearance(getParent(), R.style.Body);
        		        	   impact_value_view.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   impact_value_view.setGravity(Gravity.RIGHT);
        		        	   
        		        	   //add textviews to new row
        		        	   newRow.addView(impact_type_view);
        		        	   newRow.addView(impact_value_view);
        		        	   
        		        	   //TODO: Provide undo for added impacts
        		        	   newRow.setOnLongClickListener(new OnLongClickListener(){
	    		        		   public boolean onLongClick(View view){
					       				AlertDialog.Builder long_alert_builder = new AlertDialog.Builder(view.getContext());
					            		long_alert_builder.setMessage("Remove Impact?")
					            		       .setCancelable(true)
					            		       .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
					            		           public void onClick(DialogInterface dialog, int id) {
					            						//i.setVisibility(View.GONE);
					            						//newRow.removeView(view);
					        		        			TableLayout impact_table = (TableLayout)findViewById(R.id.impact_table);
					        		        			impact_table.removeView(newRow);
					            		          }
					            		       })
					            		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					            		           public void onClick(DialogInterface dialog, int id) {
					            		               //return nothing if the dialog is canceled 
					            		        	   dialog.cancel();
					            		           }
					            		       });
					            		
					            		AlertDialog remove_impact_alert = long_alert_builder.create();
					            		remove_impact_alert.show();
					            		return true;
        		        		   }
        		        	   });
        		        	   
        		        	   //add new row to impact table
        		        	   TableLayout impact_table = (TableLayout)findViewById(R.id.impact_table);		        	   
        		        	   impact_table.addView(newRow, new TableLayout.LayoutParams(
        		                       LayoutParams.FILL_PARENT,
        		                       LayoutParams.WRAP_CONTENT));
        		        	   impact_table.setPadding(10, 10, 10, 10);
        		        	   
        		           }
        		       })
        		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		               //return nothing if the dialog is canceled 
        		        	   dialog.cancel();
        		           }
        		       })
        		       .setView(impact_widget); //add the display widget to the dialog
        		AlertDialog impact_alert = impact_builder.create();
        		//launch the alert dialog
        		impact_alert.show();
        	}
        	
        	
        });
        
        //Photo OnClick
        Button photoButton = (Button)findViewById(R.id.photo_add_button);
        photoButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		
        		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);			
	
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
				startActivityForResult(cameraIntent, CAMERA_ACTIVITY);
        		//TODO:Display image files in table, add remove function
        	}
        });
        
      //GPS OnClick
        Button currLocationButton = (Button)findViewById(R.id.currLocButton);
        currLocationButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		newLocation = getCurrentBestLocation(); 
        		TextView currLocationTextView = (TextView)findViewById(R.id.currLocTextView); 
        		currLocationTextView.setText(newLocation.toString());
        	}
        });
        
        //load Reporter preferences
    	final Button add_reporter = (Button)findViewById(R.id.reporter_add);
    	add_reporter.setOnClickListener(new OnClickListener(){
        	public void onClick(final View view) {
	    	    //build reporter name edittext
        		reporter_name_edittext = new EditText(view.getContext());
        		reporter_contact_edittext = new EditText(view.getContext());
        		
        		//create textview labels
        		TextView reporter_name_label = new TextView(view.getContext());
        		reporter_name_label.setText("Name");
        		
        		TextView reporter_contact_label = new TextView(view.getContext());
        		reporter_contact_label.setText("Email Address");
        		
        		//programatically implement layout widget
        		LinearLayout reporter_widget = new LinearLayout(view.getContext());
        		reporter_widget.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT ));
        		reporter_widget.setOrientation(LinearLayout.VERTICAL);
        		reporter_widget.setPadding(5,5,5,5);
        		reporter_widget.addView(reporter_name_label);
        		reporter_widget.addView(reporter_name_edittext);
        		reporter_widget.addView(reporter_contact_label);
        		reporter_widget.addView(reporter_contact_edittext);
        		
        		AlertDialog.Builder reporter_builder = new AlertDialog.Builder(view.getContext());
        		reporter_builder.setMessage("Please add your information")
        		       .setCancelable(true)
        		       .setPositiveButton("Add", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		        	   String reporter_name = reporter_name_edittext.getText().toString();
        		        	   String reporter_contact = reporter_contact_edittext.getText().toString();
        		        	   //Log.i("reporter_contact", reporter_contact);
        		        	   newReporter.setReporterName(reporter_name);
        		        	   newReporter.setContactDetails(reporter_contact);
        		        	   
        		        	   displayReporter();
        		        	   add_reporter.setVisibility(View.GONE);


        		          }
        		       })
        		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		               //return nothing if the dialog is canceled 
        		        	   dialog.cancel();
        		           }
        		       })
        		       .setView(reporter_widget); //add the display widget to the dialog
        		
        		AlertDialog reporter_alert = reporter_builder.create();
        		//launch the alert dialog
        		reporter_alert.show();
        	}	
        });
    	
    	
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES, 0);
        String reporter_name = settings.getString("reporter_name", null);
        String reporter_contact = settings.getString("reporter_contact", null);
        Log.i("reporter_name", reporter_name);
        
        if (reporter_name.length() < 1) {
        	add_reporter.setVisibility(View.VISIBLE);
        }
        else{
        	add_reporter.setVisibility(View.GONE);
        	newReporter.setReporterName(reporter_name);
        	newReporter.setContactDetails(reporter_contact);        	
        	displayReporter();
        }
        
        //Submission OnClick
        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Spinner category_spinner = (Spinner)findViewById(R.id.category_spinner);
				newCategory = IncidentReport.Category.values()[category_spinner.getSelectedItemPosition()];
				
				newIncidentReport = new IncidentReport();
				newIncidentReport.setIncidentDate(new Date());
				newIncidentReport.setIncidentReporter(newReporter);
				newIncidentReport.setIncidentLocation(newLocation);
				newIncidentReport.setIncidentCategory(newCategory);
				newIncidentReport.setIncidentComments(((EditText)findViewById(R.id.comments_text)).getText().toString());
				newIncidentReport.setIncidentImpact(newImpact);
				newIncidentReport.setIncidentReporter(newReporter);
				newIncidentReport.setPhotoFileLocations(attachedPhotoUris);

				db.insertReport(newIncidentReport);
				view.setClickable(false);
				
				lastIncidentReportSubmitted = newIncidentReport; 
			
				//Launch Individual Report Page
				Intent intent = new Intent(SubmitForm.this, IncidentReporter.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("switch_to", IncidentReportSummary.class.toString()); 
				intent.setAction(Intent.ACTION_VIEW);
				startActivity(intent);
			}
		});
        
    }
    
    
    public static IncidentReport getLastIncidentReportSubmitted()
    {
    	return lastIncidentReportSubmitted; 
    }
    
    @Override
    public void onPause() {
	    super.onPause();
	    //TODO:Close DB connection
	    db.close();
	    Log.i("db", "close");
    }
    

    @Override
    public void onResume() {
	    super.onResume();
	    //TODO:Open DB connection
	    db = new DatabaseAdapter(this);
	    db.open();
	    Log.i("db", "open");
	    
	    //Display attached photos
	    displayGallery();
    }

    @Override
    protected void onStop(){
       super.onStop();

	   // Save reporter in shared preferences
	   /*SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES, 0);
	   SharedPreferences.Editor editor = settings.edit();
	   editor.putString("reporter_name", newReporter.getReporterName());
	   editor.putString("reporter_contact", newReporter.getContactDetails());
	   editor.commit();*/
    }

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		else {
			switch (requestCode) {
		
			case CAMERA_ACTIVITY: 
				Bundle b = data.getExtras();
				//Uri photoUri = data.getData();
				Bitmap bm = (Bitmap) b.get("data");
				//mImageView.setImageBitmap(bm); // Display image in the Vie
				//MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null);
				Uri photoUri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null));
				Log.i("image_taken_uri", photoUri.toString());
				attachedPhotoUris.add(photoUri.toString());
				break;
			}
			//displayGallery();
		}
	}	
    
    public void displayReporter() {
	    String reporter_name = newReporter.getReporterName();
	    String reporter_contact = newReporter.getContactDetails();
	    //Log.i("reporter_contact", reporter_contact);
	   
	    //generate textview for name
	    TextView reporter_name_view = new TextView(this);
	    reporter_name_view.setText(reporter_name);
	    reporter_name_view.setTextAppearance(getParent(), R.style.Body);
	    reporter_name_view.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	    
 	    //generate textview for contact
 	    TextView reporter_contact_view = new TextView(this);
	    reporter_contact_view.setText(reporter_contact);
	    reporter_contact_view.setTextAppearance(getParent(), R.style.Body);
	    reporter_contact_view.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
 	   
	    LinearLayout reporter_container = (LinearLayout)findViewById(R.id.reporter_container);
 	   
	    reporter_container.addView(reporter_name_view);
	    reporter_container.addView(reporter_contact_view);
    }
    
    private void displayGallery() {
    	if (attachedPhotoUris.size() > 0) {
	        Gallery g = (Gallery) findViewById(R.id.picturesTaken);
	        g.setAdapter(new ImageAdapter(attachedPhotoUris, this));
    	}
    }

}
