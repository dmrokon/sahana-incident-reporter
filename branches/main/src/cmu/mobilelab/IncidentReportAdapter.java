package cmu.mobilelab;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class IncidentReportAdapter extends ArrayAdapter<IncidentReport> {

    private ArrayList<IncidentReport> items;

    public IncidentReportAdapter(Context context, int textViewResourceId, ArrayList<IncidentReport> items) {
            super(context, textViewResourceId, items);
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) { 
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.incident_listitem, null);
            }
            IncidentReport ir = items.get(position);
            if (ir != null) {
            	ImageView iv = (ImageView) v.findViewById(R.id.photo); 
                TextView tt = (TextView) v.findViewById(R.id.body);
                TextView bt = (TextView) v.findViewById(R.id.header);
                
            	Resources res = v.getContext().getResources();
            	Drawable drawable = res.getDrawable(R.drawable.ic_menu_camera);
            	iv.setImageDrawable(drawable);
                
                if (iv != null && ir.getPhotoFileLocations().size() != 0)
                {
                
                	//String ir_str = ir.getPhotoFileLocations().get(0); 
                	//Uri uri = Uri.parse(ir_str); 
                	//iv.setImageURI(uri); 
                	
                }
                if (tt != null) {
                      tt.setText("Date: "+ ir.getIncidentDate().toString() + 
                    		  ", Location: " + ir.getIncidentLocation().toString());                           
                      }
                if(bt != null){
                      bt.setText("Status: "+ ir.getIncidentImpact().toString() +  
                    		  ", Reporter: " + ir.getIncidentReporter().toString() + 
                    		  ", Category: " + ir.getIncidentCategory().toString());
                }
            }
            return v;
    }
}