package cmu.mobilelab;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableRow.LayoutParams;


public class IncidentReporter extends TabActivity {
	
	private final static String FORM_TAG = "form"; 
	private final static String SUMMARY_TAG = "summary"; 
	private final static String LIST_TAG = "reports"; 
	private final static String MAP_TAG = "maps";
	public static final String SHARED_PREFERENCES = "incidentreporter_prefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, SubmitForm.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec(FORM_TAG).setIndicator("New",
                          res.getDrawable(R.drawable.ic_tab_addform))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, IncidentReportSummary.class);
        spec = tabHost.newTabSpec(SUMMARY_TAG).setIndicator("View Last",
                          res.getDrawable(R.drawable.ic_tab_viewform))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        // Do the same for the other tabs
        intent = new Intent().setClass(this, IncidentListView.class);
        spec = tabHost.newTabSpec(LIST_TAG).setIndicator("List All",
                          res.getDrawable(R.drawable.ic_tab_listview))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, IncidentMap.class);
        spec = tabHost.newTabSpec(MAP_TAG).setIndicator("Map All",
                          res.getDrawable(R.drawable.ic_tab_mapview))
                      .setContent(intent);
        tabHost.addTab(spec);
        
      /* tabHost.setOnTabChangedListener(new OnTabChangeListener()
        {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				lastTab = currTab; 
				currTab = getTabHost().getCurrentTabTag(); 
			}
        	
        }); */
     
        Intent startIntent = this.getIntent(); 
        onNewIntent(startIntent); 
        
        
    }
    
    @Override
    public void onNewIntent (Intent intent) {
      super.onNewIntent(intent);
  
      //	lastTab = currTab; 
      //	currTab = getTabHost().getCurrentTabTag(); 
      	lastTab =  getTabHost().getCurrentTabTag(); 
		
      	// get the result passed in: last tab and next tab
		Bundle dataBundle = intent.getExtras(); 
		
		if(dataBundle == null)
		{
			getTabHost().setCurrentTab(0);
			return;
		}
		
		String switchToTab = dataBundle.getString("switch_to"); 
		
		if(switchToTab != null)
		{
			if(switchToTab.equals(IncidentReportSummary.class.toString()))
			{
				getTabHost().setCurrentTabByTag(SUMMARY_TAG); 
			}
			else if (switchToTab.equals(IncidentListView.class.toString()))
			{
				getTabHost().setCurrentTabByTag(LIST_TAG); 
			}
			else if (switchToTab.equals(SubmitForm.class.toString()))
			{
				getTabHost().setCurrentTabByTag(FORM_TAG); 
			}
			else if (switchToTab.equals(IncidentMap.class.toString()))
			{
				getTabHost().setCurrentTabByTag(MAP_TAG); 
			}
		}
    }
    
    private static String currTab = FORM_TAG; 
    private static String lastTab = currTab; 
    public static String getLastTab()
    {
    	if(lastTab == FORM_TAG)
    		return SubmitForm.class.toString(); 
    	else if(lastTab == SUMMARY_TAG)
    		return IncidentReportSummary.class.toString(); 
    	else if(lastTab == LIST_TAG)
    		return IncidentListView.class.toString();
    	else if(lastTab == MAP_TAG)
    		return IncidentMap.class.toString(); 
    	else
    		return ""; //this should throw an exception actually
    }    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        return true;
    }
    

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return true;	
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_about:
            //TODO:About activity
        	AlertDialog.Builder about_builder = new AlertDialog.Builder(this);
    		about_builder.setCancelable(true).setTitle("About").setMessage(R.string.about);
    		AlertDialog about_alert = about_builder.create();
    		about_alert.show();
            return true;
        case R.id.menu_preferences:
            //TODO:preferences activity
    	    
            SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES, 0);
            String reporter_name = settings.getString("reporter_name", null);
            String reporter_contact = settings.getString("reporter_contact", null);
            Log.i("reporter_name", reporter_name);
            
        	
    		final EditText reporter_name_edittext = new EditText(this);
    		final EditText reporter_contact_edittext = new EditText(this);
            
            if (reporter_name.length() > 1) {
            	reporter_name_edittext.setText(reporter_name);
            	reporter_contact_edittext.setText(reporter_contact);
            }

    		//create textview labels
    		TextView reporter_name_label = new TextView(this);
    		reporter_name_label.setText("Name");
    		
    		TextView reporter_contact_label = new TextView(this);
    		reporter_contact_label.setText("Email Address");
    		
    		//programatically implement layout widget
    		LinearLayout reporter_widget = new LinearLayout(this);
    		reporter_widget.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT ));
    		reporter_widget.setOrientation(LinearLayout.VERTICAL);
    		reporter_widget.setPadding(5,5,5,5);
    		reporter_widget.addView(reporter_name_label);
    		reporter_widget.addView(reporter_name_edittext);
    		reporter_widget.addView(reporter_contact_label);
    		reporter_widget.addView(reporter_contact_edittext);
    		
    		AlertDialog.Builder reporter_builder = new AlertDialog.Builder(this);
    		reporter_builder.setMessage("Please save your contact information")
    		       .setCancelable(true)
    		       .setPositiveButton("Save", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		        	   String reporter_name = reporter_name_edittext.getText().toString();
    		        	   String reporter_contact = reporter_contact_edittext.getText().toString();
    		        	   
    		        	   SharedPreferences settings = getSharedPreferences(SHARED_PREFERENCES, 0);
    		        	   SharedPreferences.Editor editor = settings.edit();
    		        	   editor.putString("reporter_name", reporter_name);
    		        	   editor.putString("reporter_contact", reporter_contact);
    		        	   editor.commit();
    		        	   
    		        	   //TODO:Set Shared Preferences    		        	   
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
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}