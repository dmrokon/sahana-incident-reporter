package cmu.mobilelab;

public class SahanaLocation 
{
	private double mLatitude; 
	private double mLongitude;
	
	public SahanaLocation(double Latitude, double Longitude)
	{
		mLatitude = Latitude; 
		mLongitude = Longitude; 
	}
	
	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}
	public double getLatitude() {
		return mLatitude;
	}
	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}
	public double getLongitude() {
		return mLongitude;
	} 
	
	public String toString()
	{
		return this.mLatitude + ", " + this.mLongitude; 
	}
}