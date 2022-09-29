package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;
/* Copyright (c) 2008-2013 Philippe Fournier-Viger
* 
* This file is part of the SPMF DATA MINING SOFTWARE
* (http://www.philippe-fournier-viger.com/spmf).
* 
* SPMF is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with
* SPMF. If not, see <http://www.gnu.org/licenses/>.
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a sequence database, where each sequence is implemented
 * as a list of integers and should have a unique id.
*
* @see Sequence
 * @author Philipe-Fournier-Viger
 */
public class SequenceDatabase {

	/** a matrix to store the sequences in this database */
	//raw sti sequences <start,finish, symbol>
	protected List<STI[]> sti_sequences = new ArrayList<STI[]>();
	
	//itemset sequence, 
	// an itemset is a set of items with a same start point
	protected List<int[]> sequences = new ArrayList<int[]>();
	
	//mapping each symbol to a list of its occrrences, 
	// an occurrence is a map from seq_id to the position in sti_sequence
	protected Map<Integer,Map<Integer, Integer>> occurrences = new HashMap<>();
		
//	protected Map<Integer,Integer> freq = new HashMap<Integer,Integer>();	
	
	/** the total number of item occurrences in this database
	 * (variable to be used for statistics) */
	protected long itemOccurrenceCount = 0;
	
	protected int minsup = 1; // minimal suppport in percentage
	
	/**
	 * Method to load a sequence database from a text file in SPMF format.
	 * @param path  the input file path.
	 * @throws IOException exception if error while reading the file.
	 */
	public void loadFile(String path) throws IOException {
		// initialize the variable to calculate the total number of item occurrence
		itemOccurrenceCount = 0;
		// initalize the list of arrays for storing sequences
		sti_sequences = new ArrayList<STI[]>();
		//sequences = new ArrayList<Integer[]>();

		String thisLine; // variable to read each line.
		BufferedReader myInput = null;
		
		
		try {
			FileInputStream fin = new FileInputStream(new File(path));
			myInput = new BufferedReader(new InputStreamReader(fin));
		
			// Read raw event sequences and transform each sequence to an itemset sequence in SPMF format.  
			// A item is identified by its symbol, an itemset is the set of items with same start point
			// STI or sti : symbolic time interval
			while ((thisLine = myInput.readLine()) != null) {
				

				// if the line is not a comment, is not empty or is not other
				// kind of metadata				
				if (thisLine.isEmpty() == false &&
						thisLine.charAt(0) != '#' && thisLine.charAt(0) != '%'
						&& thisLine.charAt(0) != '@'
						&& thisLine.length() > 1 // 1 to get rid of new line code whose lenght IS 1 !!
						&& !thisLine.matches(Constants.HEADING1)
						&& !thisLine.matches(Constants.HEADING2)
						&& !thisLine.matches(Constants.ISENTITY)) {
					
					// split this line according to event delimiter and process the line
					String[] tokens = thisLine.split(Constants.EDELIMITER);

					// we will store the sequence as a list of STIs in memory
					STI[] sti_sequence = new STI[tokens.length];// current sti sequence	
					
					// as well as convert STI represention to SPMF format of itemset representation
					List<Integer> itemset_sequence = new ArrayList<Integer>();// current item sequence
					
					Set<Integer> occ_sym =  new HashSet<>(); // symbols aleady seen in the sequence

					int itemset = 0;

					// (1) an STI object, (2) an item in spmf fortmat, start-aligned items form an itemset 
					// add them the array representing the current sequence.

					for(int j=0; j < tokens.length; j++){
						String[]sti = tokens[j].split(Constants.VDELIMITER);
						int st = Integer.parseInt(sti[0]); //interval start
						int fn = Integer.parseInt(sti[1]); //interval finish
						int sym = Integer.parseInt(sti[2]);//interval state symbol
						sti_sequence[j] = new STI(st,fn,sym); 
	
						if (itemset != st ) { // a new itemset
							if (itemset != 0) {
								itemset_sequence.add(-1);
							}
							itemset = st;	
						}
						itemset_sequence.add(sym);
						
						itemOccurrenceCount++;
									
						// Inverted-index: symbol to its occurreces in the sti sequences: sym -> seq -> pos
						int seq = sti_sequences.size();
						if (!this.occurrences.containsKey(sym)) {
							this.occurrences.put(sym, new HashMap<Integer, Integer>());
						}
						Map<Integer,Integer> occ = this.occurrences.get(sym);
						if (! occ.containsValue(j)) {
							occ.put(seq, j);
						}
					}
					itemset_sequence.add(-2); // end of the sequence
					// add the sequence to the list of sequences
					this.sequences.add(itemset_sequence.stream().mapToInt(i->i).toArray());
					
					this.sti_sequences.add(sti_sequence);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myInput != null) {
				myInput.close();
			}
		}
	}
		
	/**
	 * Print this sequence database to System.out.
	 */
	public void print() {
		System.out.println("============  SEQUENCE DATABASE ==========");
		System.out.println(toString());
	}
	
	/**
	 * Print statistics about this database.
	 */
	public void printDatabaseStats() {
		System.out.println("============  STATS ==========");
		System.out.println("Number of sequences : " + sequences.size());
		
		// Calculate the average size of sequences in this database
		double meansize = ((float)itemOccurrenceCount) / ((float)sequences.size());
		System.out.println("mean size" + meansize);
	}

	/**
	 * Return a string representation of this sequence database.
	 */
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		// for each sequence
		for (int i=0; i < sequences.size(); i++) { 
			buffer.append(i + ":  ");
			
			// get that sequence
			int[] sequence = sequences.get(i);
			
			// for each token in that sequence (items, or separators between items)
			// we will print it in a human-readable way
			
			for(Integer token : sequence){
				buffer.append(token.toString() + " ");
			}
		
			// print each item print eac
			buffer.append(System.lineSeparator());
		}
		return buffer.toString();
	}
	
	/**
	 * Get the sequence count in this database.
	 * @return the sequence count.
	 */
	public int size() {
		return this.sequences.size();
	}
	
	/**
	 * Get the sequences from this sequence database.
	 * @return A list of sequences (int[]) in SPMF format.
	 */
	public List<int[]> getSequences() {
		return this.sequences;
	}
	

	/**
	 * Get the index occurrences from this sequence database.
	 * @return A list of sequences (int[]) in SPMF format.
	 */
	public Map<Integer, Map<Integer,Integer>> getOccurrences() {
		return this.occurrences;
	}
	
	public void printSTIs() {
		System.out.println("=========  STI SEQUENCE DATABASE ==========");
		
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
		
			// print each item print eac
			buffer.append(System.lineSeparator());
		}
		System.out.println(buffer);
	}

}
