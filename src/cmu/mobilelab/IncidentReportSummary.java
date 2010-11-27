package cmu.mobilelab;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import cmu.mobilelab.Impact.ImpactType;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class IncidentReportSummary extends Activity {

	private ArrayList<String> imageUris = new ArrayList<String>(); 
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ir_summary);
        

        // This is what will be used to populate the summary form
        IncidentReport reportToShow = null; 
        
        if (IncidentReporter.getLastTab().equals(SubmitForm.class.toString()))
        {// check if this was a result of a submit form action
        	reportToShow = SubmitForm.getLastIncidentReportSubmitted();
        }
        else if(IncidentReporter.getLastTab().equals(IncidentListView.class.toString()));
        {// check if this was a result of a list incident clicked action
        	reportToShow = IncidentListView.getLastSelectedIncident(); 
        }
        
        // TODO: check if reportToShow is not null, then fill summary with data from it
        
        if (reportToShow != null) {
			//Date date = reportToShow.getIncidentDate();
			//Reporter reporter = reportToShow.getIncidentReporter();
			//Integer category = (Integer)reportToShow.getIncidentCategory().ordinal();
			//IncidentLocation location = reportToShow.getIncidentLocation(); 
			//Double latitude = location.getLatitude();
			//Double longitude = location.getLongitude();
			
			String comments = reportToShow.getIncidentComments();
			
			imageUris = reportToShow.getPhotoFileLocations();
			// Map<ImpactType, Integer> impactMap = reportToShow.getIncidentImpact().getImpact();
			// Iterator impactIterator = impactMap.entrySet().iterator();
			
			TextView category_view = (TextView)findViewById(R.id.category);
			// category_view.setText(IncidentReport.Category.values()[category].toString());
			category_view.setText(reportToShow.getIncidentCategory().toString()); 
			
			TextView date_view = (TextView)findViewById(R.id.incidentDate);
			date_view.setText(reportToShow.getIncidentDate().toLocaleString());
			
			TextView location_view = (TextView)findViewById(R.id.incidentLocation);
			// location_view.setText(latitude.toString() + ", " + longitude.toString());
			location_view.setText(reportToShow.getIncidentLocation().toString()); 
			
			TextView impacts_view = (TextView)findViewById(R.id.impacts); 
			impacts_view.setText(reportToShow.getIncidentImpact().toString()); 
			
			TextView reporter_view = (TextView)findViewById(R.id.reporterInfo); 
			reporter_view.setText(reportToShow.getIncidentReporter().toString()); 
			
	        GridView gridView = (GridView) findViewById(R.id.photo_grid);
	        gridView.setAdapter(new ImageAdapter(this));     
			
			TextView comments_view = (TextView)findViewById(R.id.comments);
			comments_view.setText(comments);
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
            return imageUris.size();
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
        	final String photo = imageUris.get(position);
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageURI(Uri.parse(photo));
            
            Log.i("photo_uri", photo);
            
            imageView.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				Intent intent = new Intent();  
    				intent.setAction(android.content.Intent.ACTION_VIEW);    
    				intent.setDataAndType(Uri.parse(photo), "image/*");
    				context.startActivity(intent);
    			}
    			
    		});
            
            return imageView;
        }
    }
    
}