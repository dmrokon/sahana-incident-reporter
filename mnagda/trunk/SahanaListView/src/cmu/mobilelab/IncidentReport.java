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
	private SahanaLocation 		incidentLocation; 
	private Category 			incidentCategory; 
	private Reporter 			incidentReporter; 
	private String				incidentComments; 
	private Impact				incidentImpact; 
	private	ArrayList<String>	photoFileLocations; 

	public IncidentReport()
	{
 
	}
	
	public IncidentReport(Date IncidentDate, SahanaLocation IncidentLocation, Category IncidentCategory, 
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
	
	public void setIncidentLocation(SahanaLocation incidentLocation) {
		this.incidentLocation = incidentLocation;
	}

	public SahanaLocation getIncidentLocation() {
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
