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
	private Location 			incidentLocation; 
	private Category 			incidentCategory; 
	private Reporter 			incidentReporter; 
	private String				incidentComments; 
	private Impact				incidentImpact; 
	private	ArrayList<String>	photoFileLocations; 

	public IncidentReport(Date IncidentDate, Location IncidentLocation, Category IncidentCategory, 
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
	
	public void setIncidentLocation(Location incidentLocation) {
		this.incidentLocation = incidentLocation;
	}

	public Location getIncidentLocation() {
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
}
