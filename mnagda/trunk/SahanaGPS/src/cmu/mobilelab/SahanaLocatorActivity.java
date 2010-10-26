//package cmu.mobilelab;
//
//import android.app.Activity;
//import android.content.Context;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.widget.TextView;
//
//public class SahanaLocatorActivity extends Activity {
//	
//	private static int UPDATE_TIME = 30000; 
//	private static int UPDATE_DISTANCE = 500; 
//	private static final int UPDATE_INTERVAL = 1000 * 60 * 1;
//	private Location mCurrentBestLocation = null;
//	private LocationManager mLocationManager = null; 
//	private LocationListener mLocationListener = null; 
//	
//	/** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        
//        doGpsSetup(); 
//        
//
////      for(int i=0; i < 2000000; i++)
////      {
////      	TextView myEditText = (TextView) findViewById(R.id.TextView01); 
////      	myEditText.setText(getCurrentBestLocation().toString()); 
////      	try
////      	{
////      		Thread.sleep(200); 
////      	}
////      	catch(Exception ex){}
////      }
//        
//    }
//    
//    private void doGpsSetup()
//    {
//    	// Acquire a reference to the system Location Manager
//        mLocationManager = 
//        	(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        
//        // Define a listener that responds to location updates
//        mLocationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//              // Called when a new location is found by the network location provider.
//              processNewLocation(location);
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {}
//
//            public void onProviderDisabled(String provider) {}
//          };
//
//        // Register the listener with the Location Manager to receive location updates
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_TIME, UPDATE_DISTANCE, mLocationListener);
//
//
//    }
//    
//    /** Determines whether one Location reading is better than the current Location fix
//	  * @param location  The new Location that you want to evaluate
//	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
//	  */
//	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
//	    if (currentBestLocation == null) {
//	        // A new location is always better than no location
//	        return true;
//	    }
//
//	    // Check whether the new location fix is newer or older
//	    long timeDelta = location.getTime() - currentBestLocation.getTime();
//	    boolean isSignificantlyNewer = timeDelta > UPDATE_INTERVAL;
//	    boolean isSignificantlyOlder = timeDelta < -UPDATE_INTERVAL;
//	    boolean isNewer = timeDelta > 0;
//
//	    // If it's been more than one minutes since the current location, use the new location
//	    // because the user has likely moved
//	    if (isSignificantlyNewer) {
//	        return true;
//	    // If the new location is more than one minutes older, it must be worse
//	    } else if (isSignificantlyOlder) {
//	        return false;
//	    }
//
//	    // Check whether the new location fix is more or less accurate
//	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
//	    boolean isLessAccurate = accuracyDelta > 0;
//	    boolean isMoreAccurate = accuracyDelta < 0;
//	    
//	    // Determine location quality using a combination of timeliness and accuracy
//	    if (isMoreAccurate) {
//	        return true;
//	    } else if (isNewer && !isLessAccurate) {
//	        return true;
//	    }
//	    return false;
//	}
//	
//	public void processNewLocation(Location loc)
//	{
//		if(isBetterLocation(loc, mCurrentBestLocation))
//		{
//			mCurrentBestLocation = loc; 
//			
//			TextView myEditText = (TextView) findViewById(R.id.TextView01); 
//			myEditText.setText(getCurrentBestLocation().toString()); 
//			myEditText.refreshDrawableState();
//		}
//	}
//	
//	public SahanaLocation getCurrentBestLocation()
//	{
//		if(mCurrentBestLocation != null)
//			return new SahanaLocation(mCurrentBestLocation.getLatitude(), mCurrentBestLocation.getLongitude()); 
//		else
//		{
//			// this is just so we can immediately get some location even if it isn't accurate
//			Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//			if(lastKnownLocation != null)
//				return new SahanaLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()); 
//			else
//				return new SahanaLocation(0,0); 
//		}
//	}
//
//    @Override
//    protected void onDestroy ()
//    {
//    	super.onDestroy(); 
//    	mLocationManager.removeUpdates(mLocationListener); 
//    }
//}
