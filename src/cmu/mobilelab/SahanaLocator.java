//package cmu.mobilelab;
//
//import android.app.Activity;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.widget.TextView;
//
///**
// * TODO: Write function to get current location with some sort of accuracy
// * @author mahvish
// *
// */
//
//public class SahanaLocator extends Activity {
//	
//	SahanaLocatorService mLocatorService;
//	boolean mIsBound = false; 
//	
//	private ServiceConnection mConnection = new ServiceConnection() {
//	    public void onServiceConnected(ComponentName className, IBinder service) {
//	        // This is called when the connection with the service has been
//	        // established, giving us the service object we can use to
//	        // interact with the service.  Because we have bound to a explicit
//	        // service that we know is running in our own process, we can
//	        // cast its IBinder to a concrete class and directly access it.
//	    	mLocatorService = ((SahanaLocatorService.SahanaLocatorBinder)service).getService();
//	    }
//
//	    public void onServiceDisconnected(ComponentName className) {
//	        // This is called when the connection with the service has been
//	        // unexpectedly disconnected -- that is, its process crashed.
//	        // Because it is running in our same process, we should never
//	        // see this happen.
//	    	mLocatorService = null;
//	    }
//	};
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//   
//        Intent serviceIntent = new Intent(); 
//        serviceIntent.setClassName("cmu.mobilelab", "SahanaLocatorService");
//        startService(serviceIntent); 
//   
//        doBindService(); 
//        
//        try
//        {
//        	Thread.sleep(60000); 
//        }
//        catch(Exception ex)
//        {}
//        
////        for(int i=0; i < 2000000; i++)
////        {
////        	SahanaLocation currLocation = mLocatorService.getCurrentBestLocation();
////        	TextView myEditText = (TextView) findViewById(R.id.TextView01); 
////        	myEditText.setText(currLocation.toString()); 
////        	myEditText.refreshDrawableState(); 
////        	try
////        	{
////        		Thread.sleep(200); 
////        	}
////        	catch(Exception ex){}
////        }
//    }
//    
//    void doBindService() {
//        // Establish a connection with the service.  We use an explicit
//        // class name because we want a specific service implementation that
//        // we know will be running in our own process (and thus won't be
//        // supporting component replacement by other applications).
//    	Intent serviceIntent = new Intent();
//    	serviceIntent.setClassName("cmu.mobilelab", "SahanaLocatorService"); 
//        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
//        mIsBound = true;
//    }
//
//    void doUnbindService() {
//        if (mIsBound) {
//            // Detach our existing connection.
//            unbindService(mConnection);
//            mIsBound = false;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        doUnbindService();
//        stopService(new Intent(SahanaLocator.this,
//                SahanaLocatorService.class));
//    }
//}