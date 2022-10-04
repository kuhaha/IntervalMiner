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
import java.util.List;

/**
 * Implementation of a sequence database, where each sequence is implemented
 * as a list of integers and should have a unique id.
*
* @see Sequence
 * @author Philipe-Fournier-Viger
 */
public class SequenceDatabase {

	/** a matrix to store the sequences in this database */

	//itemset sequence, 
	// an itemset is a set of items with a same start point
	protected List<int[]> sequences = new ArrayList<int[]>();
	
	/** the total number of item occurrences in this database
	 * (variable to be used for statistics) */
	protected long itemOccurrenceCount = 0;
	
	protected int minsup = 1; // minimal support in percentage
	
	/**
	 * Method to load a sequence database from a text file in SPMF format.
	 * @param path  the input file path.
	 * @throws IOException exception if error while reading the file.
	 */
	public void loadFile(String path) throws IOException {
		// initialize the variable to calculate the total number of item occurrence
		itemOccurrenceCount = 0;
		// initialize the list of arrays for storing sequences
		SequenceHandler.sti_sequences = new ArrayList<STI[]>();
		//sequences = new ArrayList<Integer[]>();

		String thisLine; // variable to read each line.
		BufferedReader myInput = null;
		try {
			FileInputStream fin = new FileInputStream(new File(path));
			myInput = new BufferedReader(new InputStreamReader(fin));
		
			// Read event sequences from file and transform each sequence to an itemset sequence in SPMF format.  
			// A item is identified by symbol, an itemset is the set of items with same start point
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
					String[] tokens = thisLine.split(Constants.EVENT_DELIMITER);

					// we will store the sequence as a list of STIs in memory
					STI[] sti_sequence = new STI[tokens.length];// current sti sequence	
					
					// as well as convert STI representation to SPMF format of itemset representation
					List<Integer> itemset_sequence = new ArrayList<Integer>();// current item sequence
					
					int itemset = 0;
					for(int j=0; j < tokens.length; j++){
						String[]sti = tokens[j].split(Constants.ITEM_DELIMITER);
						int st = Integer.parseInt(sti[0]); //interval start
						int fn = Integer.parseInt(sti[1]); //interval finish
						int sym = Integer.parseInt(sti[2]);//interval state symbol
						int sym_num = Constants.SYMBOL_FROM + sym;
						STI intv = new STI(st,fn,sym);
						Tiep tiep_st = new Tiep(st, sym_num,intv);
						Tiep tiep_fn = new Tiep(fn, -sym_num,intv);
						sti_sequence[j] = intv;
	
						if (itemset != st ) { // a new itemset
							if (itemset != 0) {
								itemset_sequence.add(Constants.ITEMSET_END);
							}
							itemset = st;	
						}
						itemset_sequence.add(sym);	
						itemOccurrenceCount++;
					
						// Inverted-index: symbol to its occurrences: sym -> seq -> pos
						SequenceHandler.addOccurrence(sym, j);
				
					}
					itemset_sequence.add(Constants.SEQUENCE_END); // end of the sequence
					// add the sequence to the list of sequences
					this.sequences.add(itemset_sequence.stream().mapToInt(i->i).toArray());
					
					SequenceHandler.addSequence(sti_sequence);
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
		System.out.println("============ SPMF SEQUENCE DATABASE =======");
		System.out.println(toString());
	}
	
	/**
	 * Print statistics about this database.
	 */
	public void printDatabaseStats() {
		System.out.println("============= STATS ==========");
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
			for(Integer token : sequence){
				buffer.append(token.toString() + " ");
			}
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
}
