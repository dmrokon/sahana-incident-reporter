package cmu.mobilelab;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

public class IncidentMap extends MapActivity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ir_map);

        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.map_marker);
        IncidentItemizedOverlay itemizedoverlay = new IncidentItemizedOverlay(drawable, this);
        
        // TODO : get current location and center over that? do we want to do that?
        // get list of incidents and add all of them to map? why do we want current incident
        
        DatabaseAdapter db = new DatabaseAdapter(this); 
        db.open(); 
        ArrayList<IncidentReport> reports = db.getReports(); 
        db.close(); 
        
        for(IncidentReport ir : reports)
        {
        	IncidentLocation sl = ir.getIncidentLocation(); 
        	 // degrees * 1e6
             GeoPoint point = new GeoPoint((int)((sl.getLatitude())*1e6),(int)((sl.getLongitude())*1e6));
             OverlayItem overlayitem = 
            	 new OverlayItem(point, 
            			 ir.getIncidentCategory() + " on " + ir.getIncidentDate().toString(), 
            			 ir.getIncidentImpact().toString());
            
        	 itemizedoverlay.addOverlay(overlayitem);
        }
        
        mapOverlays.add(itemizedoverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}