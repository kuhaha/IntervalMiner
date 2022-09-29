package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author kuhaha
 * handle sequence database of symbolic interval events 
 *  together with the occurrences of each symbol in sequence 
 */
public class SequenceHandler {
	//raw sequence database: list of interval sequence <start,finish, symbol>
	protected static List<STI[]> sti_sequences = new ArrayList<STI[]>();
	
	//mapping each symbol to a list of its occurrences, 
	// an occurrence is a map from seq_id to the position in sti_sequence
	protected static Map<Integer,Map<Integer, Integer>> occurrences = new HashMap<>();
	
	public static void print() {
		System.out.println("=========  STI SEQUENCE DATABASE ==========");
		System.out.println(ToString());
	}
	private static String ToString(){
		StringBuilder buffer = new StringBuilder();
		// for each sequence
		for (int i=0; i < sti_sequences.size(); i++) { 
			buffer.append(i + ":  ");
			// get that sequence
			STI[] sti_sequence = sti_sequences.get(i);
			
			// for each token in that sequence (items, or separators between items)
			// we will print it in a human-readable way			
			for(STI token : sti_sequence){
				buffer.append(token.toString() + "; ");
			}
			buffer.append(System.lineSeparator());
		}
		return buffer.toString();
	}
}
