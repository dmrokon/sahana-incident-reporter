package cmu.mobilelab;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;


/**
 * Goal is to have a singleton so I can get the singleton, 
 * start it, use it and then stop it
 * 
 * That singleton starts the GPS updates and then gets stuff
 * Some way to set settings as well. 
 * 
 * Goal is to reduce using to 3 lines. 
 */

/**
 * To use : 
 * so we need some setting for batteries: currently i assume 
 * that we have battery problems so im putting in some random numbers
 * for time and distance interval
 * i will use the gps sevice because im guessing wifi and network 
 * will not be very good in those areas
 * on create starts location service which is updated
 * you get the best estimate as of yet
 * when you are done with the service, like form submit, pls kill the service
 * else it will keep using up power
 * @author mahvish
 *
 */
public class SahanaLocatorService extends Service {

	private static int UPDATE_TIME = 30000; 
	private static int UPDATE_DISTANCE = 500; 
	private static final int UPDATE_INTERVAL = 1000 * 60 * 1;
	private Location mCurrentBestLocation = null;
	private LocationManager mLocationManager = null; 
	private LocationListener mLocationListener = null; 
	
	/**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class SahanaLocatorBinder extends Binder {
    	SahanaLocatorService getService() {
            return SahanaLocatorService.this;
        }
    }
    
    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final SahanaLocatorBinder mBinder = new SahanaLocatorBinder();
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
//    	return null; 
    }
	
	@Override
    public void onCreate() {
    	
    	// Acquire a reference to the system Location Manager
        mLocationManager = 
        	(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
        // Define a listener that responds to location updates
        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              // Called when a new location is found by the network location provider.
              processNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
          };

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_TIME, UPDATE_DISTANCE, mLocationListener);
    }
	
	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
	    if (currentBestLocation == null) {
	        // A new location is always better than no location
	        return true;
	    }

	    // Check whether the new location fix is newer or older
	    long timeDelta = location.getTime() - currentBestLocation.getTime();
	    boolean isSignificantlyNewer = timeDelta > UPDATE_INTERVAL;
	    boolean isSignificantlyOlder = timeDelta < -UPDATE_INTERVAL;
	    boolean isNewer = timeDelta > 0;

	    // If it's been more than one minutes since the current location, use the new location
	    // because the user has likely moved
	    if (isSignificantlyNewer) {
	        return true;
	    // If the new location is more than one minutes older, it must be worse
	    } else if (isSignificantlyOlder) {
	        return false;
	    }

	    // Check whether the new location fix is more or less accurate
	    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    
	    // Determine location quality using a combination of timeliness and accuracy
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    }
	    return false;
	}
	
	public void processNewLocation(Location loc)
	{
		if(isBetterLocation(loc, mCurrentBestLocation))
		{
			mCurrentBestLocation = loc; 
		}
	}
	
	public SahanaLocation getCurrentBestLocation()
	{
		if(mCurrentBestLocation != null)
			return new SahanaLocation(mCurrentBestLocation.getLatitude(), mCurrentBestLocation.getLongitude()); 
		else
		{
			// this is just so we can immediately get some location even if it isn't accurate
			Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(lastKnownLocation != null)
				return new SahanaLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()); 
			else
				return new SahanaLocation(0,0); 
		}
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mLocationManager.removeUpdates(mLocationListener); 
	}
}
