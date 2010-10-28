package cmu.mobilelab;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.widget.TabHost;


public class IncidentReporter extends TabActivity {
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
        spec = tabHost.newTabSpec("form").setIndicator("Submit Form",
                          res.getDrawable(R.drawable.ic_menu_add))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, SahanaListView.class);
        spec = tabHost.newTabSpec("reports").setIndicator("List Reports",
                          res.getDrawable(R.drawable.ic_menu_friendslist))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MapList.class);
        spec = tabHost.newTabSpec("map").setIndicator("Map Reports",
                          res.getDrawable(R.drawable.ic_menu_mapmode))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

    }
}