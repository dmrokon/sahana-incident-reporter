package cmu.mobilelab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TableRow.LayoutParams;
import android.view.View;
import android.widget.EditText;
import java.util.Date;

public class SubmitForm extends Activity {
	
	static int RESULT_IMAGE_RETURNED = 1;
	
	private IncidentReport newIncidentReport;
	private Impact newImpact;
	private IncidentReport.Category newCategory;
	private Reporter newReporter;
	private Location newLocation;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.form);
	    
	    //TODO:Instantiate Database Connection
	    
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
        		
        		AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        		builder.setMessage("Please add an impact")
        		       .setCancelable(true)
        		       .setPositiveButton("Add", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		        	   //Convert dialog data to strings
        		        	   String selected_impact_type_string = impact_type_spinner.getSelectedItem().toString();
        		        	   String selected_impact_value_string = (String)impact_value_edittext.getText().toString();
        		        	   
        		        	   /* TODO:Convert to impact instance
        		        	    * Impact.ImpactType impact_type_enum = Impact.ImpactType.values()[impact_type_spinner.getSelectedItemPosition()];
        		        	   	* newImpact = new Impact(impact_type_enum, Integer.getInteger(impact_value_edittext.getText().toString()));
        		        	   */
        		        	   
        		        	   Log.i("impact_type", selected_impact_type_string);
        		        	   Log.i("impact_value", selected_impact_value_string);
        		        	   
        		        	   //generate new table row
        		        	   TableRow newRow = new TableRow(view.getContext());
        		        	   
        		        	   //generate textview for type
        		        	   TextView impact_type_view = new TextView(view.getContext());
        		        	   impact_type_view.setText(selected_impact_type_string);
        		        	   impact_type_view.setLayoutParams(new TableRow.LayoutParams(1));
        		        	   impact_type_view.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   
        		        	   //generate textview for value
        		        	   TextView impact_value_view = new TextView(view.getContext());
        		        	   impact_value_view.setText(selected_impact_value_string);
        		        	   impact_value_view.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        		        	   
        		        	   //add textviews to new row
        		        	   newRow.addView(impact_type_view);
        		        	   newRow.addView(impact_value_view);
        		        	   
        		        	   //add new row to impact table
        		        	   TableLayout impact_table = (TableLayout)findViewById(R.id.impact_table);		        	   
        		        	   impact_table.addView(newRow, new TableLayout.LayoutParams(
        		                       LayoutParams.FILL_PARENT,
        		                       LayoutParams.WRAP_CONTENT));
        		        	   
        		        	   //TODO: Provide undo for added impacts
        		           }
        		       })
        		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		               //return nothing if the dialog is canceled 
        		        	   dialog.cancel();
        		           }
        		       })
        		       .setView(impact_widget); //add the display widget to the dialog
        		AlertDialog alert = builder.create();
        		//launch the alert dialog
        		alert.show();
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
        
        //Submission OnClick
        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Spinner category_spinner = (Spinner)findViewById(R.id.category_spinner);
				newCategory = IncidentReport.Category.values()[category_spinner.getSelectedItemPosition()];
				
				//TODO:set IncidentReport instance
				
				newIncidentReport = new IncidentReport();
				newIncidentReport.setIncidentDate(new Date());
				//newIncidentReport.setIncidentReporter(newReporter);
				//newIncidentReport.setLocation(newLocation);
				newIncidentReport.setIncidentCategory(newCategory);
				newIncidentReport.setIncidentComments(((EditText)findViewById(R.id.comments_text)).toString());
				newIncidentReport.setIncidentImpact(newImpact);
				//newIncidentReport.setPhotoFileLocations(photoFileLocations);
		
				//TODO:Store into Database
				
				//TODO:Launch Individual Report Page
				/*
				 * Intent reportPageIntent = new Intent(view.getContext(), IndividualReport.class);
				 * startActivity(reportPageIntent);
				 */
			}
		});
        
    }
    public void onPause() {
	    super.onPause();
	    //TODO:Close DB connection
    }
    public void onResume() {
	    super.onResume();
	    //TODO:Open DB connection
    }
	
    public void onActivityResult (int requestCode, int resultCode, Intent data){
    	
    	if (requestCode == RESULT_IMAGE_RETURNED){
    		if (resultCode == RESULT_OK) {
    		//TODO:Handle returned image
    		}
    	}
    }
}
