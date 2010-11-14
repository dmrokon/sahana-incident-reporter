package cmu.mobilelab;

public class Reporter {
	
	private String	reporterName; 
	private String 	contactDetails;
	
	public Reporter() {
		this.reporterName = new String();
		this.contactDetails = new String();
	}
	
	public Reporter(String ReporterName, String ContactDetails)
	{
		reporterName = ReporterName; 
		contactDetails = ContactDetails; 
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
	
	@Override public boolean equals(Object that)
	{
		if ( this == that ) return true;
		else if (!( that instanceof Reporter) ) return false; 
		else
		{
			Reporter thatReporter = (Reporter) that; 
			if(this.reporterName.equals(thatReporter.reporterName) &&
				this.contactDetails.equals(thatReporter.contactDetails))
					return true; 
		}
		return false; 
	}
	
	private int hashCode = 0; 
	@Override public int hashCode () {
        if (hashCode == 0) {
        	int code = Utilities.getStartingHashCode();
            code = Utilities.getHashMultiplier() * code + this.reporterName.hashCode();
            code = Utilities.getHashMultiplier() * code + this.contactDetails.hashCode();
            hashCode = code;
        }
        return hashCode;
    }
	
	public String toString()
	{
		return reporterName + ", " + contactDetails; 
	}
}
