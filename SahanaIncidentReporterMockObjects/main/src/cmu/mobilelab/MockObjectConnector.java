package cmu.mobilelab;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cmu.mobilelab.Impact.ImpactType;
import cmu.mobilelab.IncidentReport.Category;

public class MockObjectConnector implements IDataAccessConnector {

	ArrayList<IncidentReport> incidentReports = new ArrayList<IncidentReport>(); 
	
	@Override
	public void open() {
		
		Calendar currentCal = Calendar.getInstance(); 
		currentCal.set(2010, 10, 12); 
		Date incidentDate = currentCal.getTime(); 
		SahanaLocation incidentLocation = new SahanaLocation(25, 40); 
		Reporter incidentReporter = new Reporter("Reporter1", "555-555-5555"); 
		Impact incidentImpact = new Impact(ImpactType.COWS_LOST, 20); 
		ArrayList<String> photoFileLocations = new ArrayList<String>(); 
		
		IncidentReport currentReport = new IncidentReport(incidentDate, incidentLocation, 
				Category.BRIDGES_BROKEN, incidentReporter,"comments1", incidentImpact, 
				photoFileLocations); 
		
		incidentReports.add(currentReport); 
		

		currentCal.set(2010, 11, 12); 
		incidentDate = currentCal.getTime(); 
		incidentLocation = new SahanaLocation(145, 260); 
		incidentReporter = new Reporter("Reporter2", "111-111-1111"); 
		incidentImpact = new Impact(ImpactType.HOUSES_DAMAGED, 10); 
		photoFileLocations = new ArrayList<String>(); 
		
		currentReport = new IncidentReport(incidentDate, incidentLocation, 
				Category.LANDSLIDE, incidentReporter,"comments2", incidentImpact, 
				photoFileLocations); 
		
		incidentReports.add(currentReport); 
		
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<IncidentReport> getReports(int numReports) {
		return this.incidentReports; 
	}

	@Override
	public void insertReport(IncidentReport report) {
		this.incidentReports.add(report); 
	}
}
