package cmu.mobilelab;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class IncidentReportSummary extends Activity {

    //---the images to display---
    Integer[] imageIDs = {
           R.drawable.ic_menu_camera, 
           R.drawable.ic_menu_camera
    };

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ir_summary);
 
        // This is what will be used to populate the summary form
        IncidentReport reportToShow = null; 
        
        Log.i("condition1part1", IncidentReporter.getLastTab());
        Log.i("condition1part2", SubmitForm.class.toString());
        
        if (IncidentReporter.getLastTab().equalsIgnoreCase(SubmitForm.class.toString()))
        {// check if this was a result of a submit form action
        	reportToShow = SubmitForm.getLastIncidentReportSubmitted(); 
        	Log.i("condition1true", SubmitForm.class.toString());
        }
        else if(IncidentReporter.getLastTab().equalsIgnoreCase(IncidentListView.class.toString()))
        {// check if this was a result of a list incident clicked action
        	reportToShow = IncidentListView.getLastSelectedIncident(); 
        	Log.i("condition2true", IncidentListView.class.toString());
        }
        
        // TODO: check if reportToShow is not null, then fill summary with data from it
        if (reportToShow != null)
        {
			Toast.makeText(IncidentReportSummary.this, "report!", Toast.LENGTH_SHORT).show();
			
        	//display category
        	TextView category = (TextView) findViewById(R.id.category);
        	category.setText(reportToShow.getIncidentCategory().toString());
        	
        	//display time/date
        	TextView time = (TextView) findViewById(R.id.incidentDate);
        	time.setText(reportToShow.getIncidentDate().toString());
        	
        	//display place
        	TextView place = (TextView) findViewById(R.id.incidentLocation);
        	place.setText(reportToShow.getIncidentLocation().toString());
        	
        	//display comments
        	TextView comments = (TextView) findViewById(R.id.comments);
        	comments.setText(reportToShow.getIncidentComments().toString());
        	
        	//display photos
        	//IN PROGRESS... 11/16/10 - SONY
        	
        	//reportToShow.getPhotoFileLocations();
        	//returns array of URIs. make imageID from that to display in grid.
        
	        GridView gridView = (GridView) findViewById(R.id.photo_grid);
	        gridView.setAdapter(new ImageAdapter(this));
	 
	        gridView.setOnItemClickListener(new OnItemClickListener() 
	        {
	            public void onItemClick(AdapterView parent, 
	            View v, int position, long id) 
	            {                
	                Toast.makeText(getBaseContext(), 
	                        "pic" + (position + 1) + " selected", 
	                        Toast.LENGTH_SHORT).show();
	            }
	        });
        }
        else
        {
        	Toast.makeText(IncidentReportSummary.this, "no report!", Toast.LENGTH_SHORT).show();
        }
    }
 
    public class ImageAdapter extends BaseAdapter 
    {
        private Context context;
 
        public ImageAdapter(Context c) 
        {
            context = c;
        }
 
        //---returns the number of images---
        public int getCount() {
            return imageIDs.length;
        }
 
        //---returns the ID of an item--- 
        public Object getItem(int position) {
            return position;
        }
 
        public long getItemId(int position) {
            return position;
        }
 
        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) 
        {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imageIDs[position]);
            return imageView;
        }
    }    
}