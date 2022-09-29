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
	  public static final String HEADING1 = "^startToncepts.*";// RE
	  public static final String HEADING2 = "^numberOfEntities.*"; // RE
	  public static final String ISENTITY = "[0-9]+,[0-9]+;"; //RE
	  public static final String VDELIMITER = ",";// delimiter between start, finish and symbol
	  public static final String EDELIMITER = ";";// delimiter between event intervals
	  
      //Allen's temporal relations
      public static final char ALLEN_BEFORE = '<';
      public static final char ALLEN_MEET = 'm';
      public static final char ALLEN_OVERLAP = 'o';
      public static final char ALLEN_FINISHBY = 'f';
      public static final char ALLEN_CONTAIN = 'c';
      public static final char ALLEN_EQUAL = '=';
      public static final char ALLEN_STARTS = 's';

}
