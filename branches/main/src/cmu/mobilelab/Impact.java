package cmu.mobilelab;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	
	public Impact() {
		this.incidentImpact = new HashMap<ImpactType, Integer>();
	}
	
	public Impact(ImpactType impactType, int impactValue) {
		this.incidentImpact = new HashMap<ImpactType, Integer>();
		this.incidentImpact.put(impactType, impactValue); 
	}
	
	public Map<ImpactType, Integer> getImpact(){
		Map<ImpactType, Integer> map = new HashMap<ImpactType, Integer>(); 
		map.putAll(incidentImpact); 
		return map; 
	}
	
	public void setImpact(ImpactType impactType, int impactValue) {
		this.incidentImpact.put(impactType, impactValue); 
	}
	
	@Override public boolean equals(Object that)
	{
		if ( this == that ) return true;
		else if (!( that instanceof Impact) ) return false; 
		else
		{
			Impact thatImpact = (Impact) that; 
			if (this.incidentImpact.size() != thatImpact.incidentImpact.size())
				return false; 
			
			for(ImpactType it : this.incidentImpact.keySet())
			{
				// if not equal
				if(!thatImpact.incidentImpact.containsKey(it) || 
						(this.incidentImpact.get(it) != thatImpact.incidentImpact.get(it)))
					return false; 
				// else assume (ANDing) true
			}
			
			return true; 
		}
	}
	
	private int hashCode = 0; 
	@Override public int hashCode () 
	{
        if (hashCode == 0) {
        	int code = Utilities.getStartingHashCode();
        	for(ImpactType it : this.incidentImpact.keySet())
			{
        		code = Utilities.getHashMultiplier() * code + it.hashCode();
        		code = Utilities.getHashMultiplier() * code + this.incidentImpact.get(it);
			}
        	hashCode = code;
        }
        return hashCode;
	}
	
	public String toString()
	{
		Set<ImpactType> incidentSet = incidentImpact.keySet();   
		String impactString = ""; 
		
		for(ImpactType it : incidentSet)
		{
			impactString += it.toString() + ": " + incidentImpact.get(it).toString() + "\n"; 
		}
		
		return impactString; 
	}
}
