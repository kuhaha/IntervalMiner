package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

/***
 * 
 * @author kuhaha
 * static constants for time interval event sequence database format
 */
public class Constants {
	private Constants(){ 
	// prevent instance generation  
	}
	
	public static final boolean DEBUG = true;
	
	// original database
	public static final String HEADING1 = "^startToncepts.*";// RE
	public static final String HEADING2 = "^numberOfEntities.*"; // RE
	public static final String ISENTITY = "[0-9]+,[0-9]+;"; //RE
	public static final String ITEM_DELIMITER = ",";// delimiter between start, finish and symbol
	public static final String EVENT_DELIMITER = ";";// delimiter between event intervals
	
	// transformed database
	public static final int SYMBOL_START = 10; // renumber symbols, reserving separator numbers, such as -1,-2,-3, -9 
	public static final int ITEMSET_END = -1; // separator indicating the end of an itemset 
	public static final int SEQUENCE_END = -2; //separator indicating the end of a sequence
	public static final int MEET_AT = -1;	// indicating an meet itemset
	
	//Allen's temporal relations
	public static final char ALLEN_BEFORE = '<';
	public static final char ALLEN_MEET = 'm';
	public static final char ALLEN_OVERLAP = 'o';
	public static final char ALLEN_FINISHBY = 'f';
	public static final char ALLEN_CONTAIN = 'c';
	public static final char ALLEN_EQUAL = '=';
	public static final char ALLEN_STARTS = 's';
	
}
