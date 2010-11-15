package cmu.mobilelab;

import java.util.ArrayList;
import java.util.Date;

public class IncidentReport {
	
	public enum Category { FLOOD, LANDSLIDE, ROADS_BROKEN, BRIDGES_BROKEN, 
		BUILDINGS_COLLAPSED, PEOPLE_TRAPPED, POWER_FAILURE}
	
	/**
	 * Date
Location
Category
Reporter
Comments
Impact A (TBD)
Impact A (TBD)
Impact A (TBD)
Photo(s)
	 **/
	
	private Date 				incidentDate; 
	private IncidentLocation 		incidentLocation; 
	private Category 			incidentCategory; 
	private Reporter 			incidentReporter; 
	private String				incidentComments; 
	private Impact				incidentImpact; 
	private	ArrayList<String>	photoFileLocations; 

	public IncidentReport()
	{
 
	}
	
	public IncidentReport(Date IncidentDate, IncidentLocation IncidentLocation, Category IncidentCategory, 
			Reporter IncidentReporter, String IncidentComments, Impact IncidentImpact, 
			ArrayList<String> PhotoFileLocations)
	{
		setIncidentDate(IncidentDate); 
		setIncidentLocation(IncidentLocation); 
		setIncidentCategory(IncidentCategory); 
		setIncidentReporter(IncidentReporter); 
		setIncidentComments(IncidentComments); 
		setIncidentImpact(IncidentImpact);
		setPhotoFileLocations(PhotoFileLocations); 
	}
	
	public void setIncidentDate(Date incidentDate) {
		this.incidentDate = incidentDate;
	}

	public Date getIncidentDate() {
		return incidentDate;
	}
	
	public void setIncidentLocation(IncidentLocation incidentLocation) {
		this.incidentLocation = incidentLocation;
	}

	public IncidentLocation getIncidentLocation() {
		return incidentLocation;
	}

	public void setIncidentReporter(Reporter incidentReporter) {
		this.incidentReporter = incidentReporter;
	}

	public Reporter getIncidentReporter() {
		return incidentReporter;
	}

	public void setIncidentCategory(Category incidentCategory) {
		this.incidentCategory = incidentCategory;
	}

	public Category getIncidentCategory() {
		return incidentCategory;
	}

	public void setIncidentComments(String incidentComments) {
		this.incidentComments = incidentComments;
	}

	public String getIncidentComments() {
		return incidentComments;
	}

	public void setIncidentImpact(Impact incidentImpact) {
		this.incidentImpact = incidentImpact;
	}

	public Impact getIncidentImpact() {
		return incidentImpact;
	}

	public void setPhotoFileLocations(ArrayList<String> photoFileLocations) {
		this.photoFileLocations = photoFileLocations;
	}

	public ArrayList<String> getPhotoFileLocations() {
		return photoFileLocations;
	}
	
	@Override public boolean equals(Object that)
	{
		if ( this == that ) return true;
		else if (!( that instanceof IncidentReport) ) return false; 
		else
		{
			IncidentReport thatReport = (IncidentReport) that; 
			if(this.incidentDate.equals(thatReport.incidentDate) &&
				this.incidentLocation.equals(thatReport.incidentLocation) &&
				this.incidentCategory.equals(thatReport.incidentCategory) &&
				this.incidentReporter.equals(thatReport.incidentReporter) &&
				this.incidentImpact.equals(thatReport.incidentImpact))
					return true; 
		}
		return false; 
	}
	
	private int hashCode = 0; 
	@Override public int hashCode () {
        if (hashCode == 0) {
        	int code = Utilities.getStartingHashCode();
            code = Utilities.getHashMultiplier() * code + this.incidentDate.hashCode();
            code = Utilities.getHashMultiplier() * code + this.incidentLocation.hashCode();
            code = Utilities.getHashMultiplier() * code + this.incidentCategory.hashCode();
            code = Utilities.getHashMultiplier() * code + this.incidentReporter.hashCode();
            code = Utilities.getHashMultiplier() * code + this.incidentImpact.hashCode();
            hashCode = code;
        }
        return hashCode;
    }
	
	public String toString()
	{
		String photoFileLocationStr = "{"; 
		for(String str : photoFileLocations)
			photoFileLocationStr += str + "|"; 
		photoFileLocationStr += "}"; 
		
		String irString = "Date = " + incidentDate.toString() + 
				", Location = " + incidentLocation.toString() + 
				", Category = " + incidentCategory.toString() + 
				", Reporter = " + incidentReporter.toString() + 
				", incidentComments = " + incidentComments + 
				", incidentImpact = " + incidentImpact.toString() + 
				", photoFileLocations = " + photoFileLocationStr; 
	
		return irString; 
	}
}
