package cmu.mobilelab;

public class Utilities {
	
	// HASH Utilities
	// Reference: http://bytes.com/topic/java/insights/723476-overriding-equals-hashcode-methods

	/**
	 * Starting Code so that hashcode doesn't start with 0
	 */
	public static int getStartingHashCode()
	{
		return 133;
	}
	
	/**
	 * Hashing Multiplier: must be Prime
	 * @return
	 */
	public static int getHashMultiplier()
	{
		return 23;
	}
	
	/**
	 * If the field is a double, use Double.doubleToLongBits(value), 
	 * and then hash the resulting long using the method above for long type
	 * @param value
	 * @return
	 */
	public static int getHashFromDouble(double value)
	{
		return getHashFromLong(Double.doubleToLongBits(value)); 
	}
	
	/**
	 * A long is bigger than an int. You can use (int)value^(value >>> 32) . 
	 * This is the method used by the java.lang.Long class.
	 * @param value
	 * @return
	 */
	public static int getHashFromLong(long value)
	{
		return (int)(value^(value >>> 32)); 
	}
	
}
