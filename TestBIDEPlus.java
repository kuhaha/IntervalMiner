package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Example of how to use the BIDE+ algorithm, from the source code.
 * 
 * @author Philippe Fournier-Viger
 */
public class TestBIDEPlus {

	public static void main(String [] arg) throws IOException{    
		// Load a sequence database
		String inputfile = fileToPath("datasets/toy.csv");
		// Create an instance of the algorithm
		AlgoBIDEPlus algo  = new AlgoBIDEPlus();
		
		// if you set the following parameter to true, the sequence ids of the sequences where
		// each pattern appears will be shown in the result
		boolean showSequenceIdentifiers = true;
		
		double minsupp = 50; // in percentage
		// execute the algorithm
		SequentialPatterns patterns = algo.runAlgorithm(inputfile, minsupp, null);  
			
		patterns.printFrequentPatterns(algo.sequenceCount, showSequenceIdentifiers);  
		System.out.println();
		algo.printStatistics();
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = TestBIDEPlus.class.getResource(filename);
		return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}