package cmu.mobilelab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MapList extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    //setContentView(R.layout.maplist);
		TextView textview = new TextView(this);
        textview.setText("Map");
        setContentView(textview);

	}
}
