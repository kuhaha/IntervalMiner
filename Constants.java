package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

/***
 * 
 * @author young
 * static finalants for time interval event sequence database format
 */
public class Constants {
	  private Constants(){ }// forbid instance generation
	  public static final String HEADING1 = "^startToncepts.*";
	  public static final String HEADING2 = "^numberOfEntities.*";
	  public static final String ISENTITY = "[0-9]+,[0-9]+;";
	  public static final String VDELIMITER = ",";// delimiter between start, finish, symbol
	  public static final String EDELIMITER = ";";
	  
	  
      //Allen's tmporal relations
      public static final char ALLEN_BEFORE = '<';
      public static final char ALLEN_MEET = 'm';
      public static final char ALLEN_OVERLAP = 'o';
      public static final char ALLEN_FINISHBY = 'f';
      public static final char ALLEN_CONTAIN = 'c';
      public static final char ALLEN_EQUAL = '=';
      public static final char ALLEN_STARTS = 's';

}
