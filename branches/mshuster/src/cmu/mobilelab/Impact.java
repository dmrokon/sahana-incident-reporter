package cmu.mobilelab;

import java.util.HashMap;
import java.util.Map;

public class Impact {
	
	public enum ImpactType { PEOPLE_AFFECTED, PEOPLE_INJURED, PEOPLE_DECEASED, HOUSES_DESTROYED, 
		HOUSES_DAMAGED, COWS_LOST}
	
	/* 
	 	Single field to allow customisable types of impacts to be defined:
		Number of People Affected
		Number of People Injured
		Number of People Deceased
		Number of Houses Destroyed
		Number of Houses Damaged
		Number of Cows Lost
		Restricted to Admin
		Defined in the Situation (Sit) Module
		Also used in Assessment
	*/ 
	
	private Map<ImpactType, Integer> incidentImpact; 
	
	public Impact()
	{}
	
	public Map<ImpactType, Integer> getImpact(){
		Map<ImpactType, Integer> map = new HashMap<ImpactType, Integer>(); 
		map.putAll(incidentImpact); 
		return map; 
	}
	
	public void setImpact(ImpactType impactType, int impactValue) {
		this.incidentImpact.put(impactType, impactValue); 
	}
}
