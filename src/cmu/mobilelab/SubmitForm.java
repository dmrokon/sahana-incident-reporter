package cmu.mobilelab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TableRow.LayoutParams;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.Date;

public class SubmitForm extends Activity {
	
	public static final String SHARED_PREFERENCES = "incidentreporter_prefs";
	static int RESULT_IMAGE_RETURNED = 1;
	
	private IncidentReport newIncidentReport;
	private Impact newImpact = new Impact();
	private IncidentReport.Category newCategory;
	private Reporter newReporter;
	private Location newLocation;
	private DBConnector db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.form);
	    
	    //newImpact = new Impact();
	    
	    //TODO:Instantiate Database Connection
	    db = new DBConnector(this);
	    
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
        Log.i("adapter", adapter.toString());
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
        		impact_builder.setMessage("Please add an impact")
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
        		        	   
        		        	   //generate textview for value
        		        	   TextView impact_value_view = new TextView(view.getContext());
        		        	   impact_value_view.setText(selected_impact_value_string);
        		        	   impact_value_view.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   impact_value_view.setGravity(Gravity.RIGHT);
        		        	   
        		        	   //add textviews to new row
        		        	   newRow.addView(impact_type_view);
        		        	   newRow.addView(impact_value_view);
        		        	   
        		        	   //TODO: Provide undo for added impacts
        		        	   /* newRow.setOnLongClickListener(new OnLongClickListener(){
        		        		   public boolean onLongClick(View view){
        		        			   newRow.removeView(view);
        		        			   return true;
        		        		   }
        		        	   });*/
        		        	   
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
        		Intent photoIntent = new Intent(Intent.ACTION_GET_CONTENT );
        		photoIntent.setType("image/jpeg");
        		startActivityForResult(photoIntent, RESULT_IMAGE_RETURNED);
        		
        		//TODO:Display image files in table, add remove function
        	}
        });
        
        //load Reporter preferences
    	final Button add_reporter = (Button)findViewById(R.id.reporter_add);
    	add_reporter.setOnClickListener(new OnClickListener(){
        	public void onClick(final View view) {
	    	    //build reporter name edittext
        		final EditText reporter_name_edittext = new EditText(view.getContext());
        		final EditText reporter_contact_edittext = new EditText(view.getContext());
        		
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
        		
        		AlertDialog.Builder impact_builder = new AlertDialog.Builder(view.getContext());
        		impact_builder.setMessage("Please add your information")
        		       .setCancelable(true)
        		       .setPositiveButton("Add", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		        	   String reporter_name = reporter_name_edittext.getText().toString();
        		        	   String reporter_contact = reporter_contact_edittext.getText().toString();
        		        	   Log.i("reporter_contact", reporter_contact);
        		        	   newReporter = new Reporter(reporter_name, reporter_contact);
        		        	   //LinearLayout reporter_container = new LinearLayout(view.getContext());
        		        	   //reporter_container.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   
        		        	   //generate textview for name
        		        	   TextView reporter_name_view = new TextView(view.getContext());
        		        	   reporter_name_view.setText(reporter_name);
        		        	   reporter_name_view.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   
        		        	   //generate textview for contact
        		        	   TextView reporter_contact_view = new TextView(view.getContext());
        		        	   reporter_contact_view.setText(reporter_contact);
        		        	   reporter_contact_view.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   
        		        	   LinearLayout reporter_container = (LinearLayout)findViewById(R.id.reporter_container);
        		        	   
        		        	   reporter_container.addView(reporter_name_view);
        		        	   reporter_container.addView(reporter_contact_view);

        		        	   add_reporter.setVisibility(View.GONE);

        		        	   // Save reporter in shared preferences
        		        	   SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES, 0);
        		        	   SharedPreferences.Editor editor = settings.edit();
        		        	   editor.putString("reporter_name", reporter_name);
        		        	   editor.putString("reporter_contact", reporter_contact);
        		        	   editor.commit();
        		          }
        		       })
        		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		               //return nothing if the dialog is canceled 
        		        	   dialog.cancel();
        		           }
        		       })
        		       .setView(reporter_widget); //add the display widget to the dialog
        		
        		AlertDialog reporter_alert = impact_builder.create();
        		//launch the alert dialog
        		reporter_alert.show();
        	}	
        });
    	
    	
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES, 0);
        String reporter_name = settings.getString("reporter_name", null);
        
        if (reporter_name == null) {
        	add_reporter.setVisibility(View.VISIBLE);
        }
        
        //Submission OnClick
        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Spinner category_spinner = (Spinner)findViewById(R.id.category_spinner);
				newCategory = IncidentReport.Category.values()[category_spinner.getSelectedItemPosition()];
				
				//TODO:set IncidentReport instance
				
				newIncidentReport = new IncidentReport();
				newIncidentReport.setIncidentDate(new Date());
				newIncidentReport.setIncidentReporter(new Reporter("Mark", "mshuster@cmu.edu"));
				//newIncidentReport.setLocation(newLocation);
				newIncidentReport.setIncidentCategory(newCategory);
				newIncidentReport.setIncidentComments(((EditText)findViewById(R.id.comments_text)).toString());
				newIncidentReport.setIncidentImpact(newImpact);
				newIncidentReport.setIncidentReporter(newReporter);
				//newIncidentReport.setPhotoFileLocations(photoFileLocations);

				db.insertReport(newIncidentReport);
				
				//TODO:Launch Individual Report Page
				/*
				 * Intent reportPageIntent = new Intent(view.getContext(), IndividualReport.class);
				 * startActivity(reportPageIntent);
				 */
			}
		});
        
    }
    
    @Override
    public void onPause() {
	    super.onPause();
	    //TODO:Close DB connection
	    db.close();
    }
    
    @Override
    public void onResume() {
	    super.onResume();
	    //TODO:Open DB connection
	    db.open();
    }

    /*
    @Override
    protected void onStop(){
       super.onStop();

    }
    */
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_about:
            //TODO:About activity
            return true;
        case R.id.menu_preferences:
            //TODO:preferences activity
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
