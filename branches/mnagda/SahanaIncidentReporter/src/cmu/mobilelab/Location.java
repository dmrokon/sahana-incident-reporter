package cmu.mobilelab;

public class Location 
{
	private double latitude; 
	private double longitude;
	
	public Location(double Latitude, double Longitude)
	{
		latitude = Latitude; 
		longitude = Longitude; 
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLongitude() {
		return longitude;
	} 
}