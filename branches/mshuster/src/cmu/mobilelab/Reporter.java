package cmu.mobilelab;

public class Reporter {
	
	private String	reporterName; 
	private String 	contactDetails;
	
	public Reporter(String ReporterName, String ContactDetails)
	{
		reporterName = ReporterName; 
		contactDetails = ContactDetails; 
	}
	
	public Reporter() {
		this.reporterName = new String();
		this.contactDetails = new String();
	}
	
	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}
	public String getReporterName() {
		return reporterName;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getContactDetails() {
		return contactDetails;
	} 
}
