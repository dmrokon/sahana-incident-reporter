package cmu.mobilelab;

import java.util.ArrayList;

public interface IDataAccessConnector 
{
	void open(); 
	void close(); 
	void 						insertReport(IncidentReport report); 
	ArrayList<IncidentReport> 	getReports(int numReports); 	
//	IncidentReport 				getReport(); 
}
