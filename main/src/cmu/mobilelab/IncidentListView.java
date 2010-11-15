package cmu.mobilelab;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class IncidentListView extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incidentlist);
        
        // Create an array of to-do items
        MockObjectConnector conn = new MockObjectConnector();
        conn.open(); 
        final ArrayList<IncidentReport> items = conn.getReports(5); 
        conn.close(); 
        
        // Create the array adapter to bind the array to the listview
        final IncidentReportAdapter arrayAdapter;
        
        // The ArrayAdapter constructor expects 3 parameters: current class 
        // instance (this), display layout to use for each list item, and the array of items respectively. 
        // Android comes with a few built-in layouts available under android.R.layout and 
        // android.R.layout.simple_list_item_1 displays a single string. 
        arrayAdapter = new IncidentReportAdapter(this, R.layout.incidentlist, items);
        
        ListView lv = getListView();
        
        // Connect the array adapter to the list view
        setListAdapter(arrayAdapter);

        // lv.setTextFilterEnabled(true);
        
        // items.add(0, myEditText.getText().toString());
		// arrayAdapter.notifyDataSetChanged();
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            	currentSelectedIncident = items.get(position);
            	
            	//Launch Individual Report Page
				Intent intent = new Intent(IncidentListView.this, IncidentReporter.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				intent.putExtra("switch_to", IncidentReportSummary.class.toString()); 
				intent.setAction(Intent.ACTION_VIEW);
				startActivity(intent);
            }
          });
    }
    
    private static IncidentReport currentSelectedIncident = null; 
    public static IncidentReport getLastSelectedIncident()
    {
    	return currentSelectedIncident; 
    }
}