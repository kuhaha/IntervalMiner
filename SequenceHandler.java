package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.pfv.spmf.patterns.itemset_list_integers_without_support.Itemset;

/**
 * 
 * @author kuhaha
 * handle sequence database of symbolic interval events 
 *  together with the occurrences of each symbol in sequence 
 */
public class SequenceHandler {
	//raw sequence database: list of interval sequence <start,finish, symbol>
	protected static List<STI[]> sti_sequences = new ArrayList<STI[]>();
	
	//mapping each symbol to a list of occurrences, 
	// an occurrence is a map from seq_id to the position in sti_sequence
	protected static Map<Integer,Map<Integer, Integer>> occurrences = new HashMap<>();
	
	// add an occurrence of symbol `sym` at position `pos` in current sti sequence 
	public static void addOccurrence(int sym, int pos) {
		int seq = sti_sequences.size();
		if (!occurrences.containsKey(sym)) {
			occurrences.put(sym, new HashMap<Integer, Integer>());
		}
		Map<Integer,Integer> occ = occurrences.get(sym);
		if (! occ.containsValue(pos)) {
			occ.put(seq, pos);
		}
	}
	
	// add a STI sequence 
	public static void addSequence(STI[] sti_sequence) {
		sti_sequences.add(sti_sequence);
	}
	
	// get the STI with symbol `sym` occurring in a specofoc sequence `seq`    
	public static STI getSTI(int sym, int seq) {
		if (occurrences.containsKey(sym)
			&& occurrences.get(sym).containsKey(seq)) 
		{
			int pos = occurrences.get(sym).get(seq);
			return sti_sequences.get(seq)[pos];
		}
		return null;
	}
	
	// get the STI with symbol `sym` occurring in a specofoc sequence `seq`    
	public static String getRelation(SequentialPattern pattern) {
		StringBuilder r = new StringBuilder("");
		List<STI> sti_sequence = new ArrayList<STI>();
		for(Itemset itemset : pattern.getItemsets()){
			// For each item in the current itemset
			for(Integer sym : itemset.getItems()){
				
			}
		}
		return r.toString();
	}

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
