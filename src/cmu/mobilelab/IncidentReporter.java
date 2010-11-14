package cmu.mobilelab;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.widget.TabHost;


public class IncidentReporter extends TabActivity {
	
	private final static String FORM_TAG = "form"; 
	private final static String SUMMARY_TAG = "summary"; 
	private final static String LIST_TAG = "reports"; 
	
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
        spec = tabHost.newTabSpec(FORM_TAG).setIndicator("Submit Form",
                          res.getDrawable(R.drawable.ic_menu_add))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, IncidentListView.class);
        spec = tabHost.newTabSpec(LIST_TAG).setIndicator("List Reports",
                          res.getDrawable(R.drawable.ic_menu_friendslist))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, IncidentReportSummary.class);
        spec = tabHost.newTabSpec(SUMMARY_TAG).setIndicator("Incident Summary Report",
                          res.getDrawable(R.drawable.ic_menu_mapmode))
                      .setContent(intent);
        tabHost.addTab(spec);

        Intent startIntent = this.getIntent(); 
        onNewIntent(startIntent); 
    }
    
    @Override
    public void onNewIntent (Intent intent) {
      super.onNewIntent(intent);
  
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
		}
    }
    
    private static String lastTab = FORM_TAG; 
    public static String getLastTab()
    {
    	if(lastTab == FORM_TAG)
    		return SubmitForm.class.toString(); 
    	else if(lastTab == SUMMARY_TAG)
    		return IncidentReportSummary.class.toString(); 
    	else if(lastTab == LIST_TAG)
    		return IncidentListView.class.toString(); 
    	else
    		return ""; //this should throw an exception actually
    }    
}