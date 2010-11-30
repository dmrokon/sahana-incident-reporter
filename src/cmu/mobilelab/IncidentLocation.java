package cmu.mobilelab;

public class IncidentLocation 
{
	private double mLatitude = 0.0; 
	private double mLongitude = 0.0;
	private String mLocationName = "";
	
	public IncidentLocation(){};
	
	public IncidentLocation(String name, double Latitude, double Longitude)
	{
		mLocationName = name; 
		mLatitude = Latitude; 
		mLongitude = Longitude; 
	}
	
	public IncidentLocation(double Latitude, double Longitude)
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
	
	public void setLocationName(String locationName) {
		this.mLocationName = locationName;
	}
	
	public String getLocationName(){
		return mLocationName;
	}
	
	@Override public boolean equals(Object that)
	{
		if ( this == that ) return true;
		else if (!( that instanceof IncidentLocation) ) return false; 
		else
		{
			IncidentLocation thatLocation = (IncidentLocation) that; 
			if(this.mLatitude == thatLocation.mLatitude &&
					this.mLongitude == thatLocation.mLongitude)
				return true; 
		}
		return false; 
	}
	
	private int hashCode = 0; 
	@Override public int hashCode () {
        if (hashCode == 0) {
        	int code = Utilities.getStartingHashCode();
            code = Utilities.getHashMultiplier() * code + Utilities.getHashFromDouble(this.mLatitude);
            code = Utilities.getHashMultiplier() * code + Utilities.getHashFromDouble(this.mLongitude);
            hashCode = code;
        }
        return hashCode;
    }
	
	public String toString()
	{
		return this.mLocationName + "( " + this.mLatitude + ", " + this.mLongitude + " )"; 
	}
}