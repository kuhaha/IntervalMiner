package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class TestUnit {

	public static void main(String[] args) throws IOException{ 
		String inputfile = fileToPath("datasets/toy.csv");
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		sequenceDatabase.loadFile(inputfile);
		int sequenceCount = sequenceDatabase.size();
		
		int minsup = 50; // minsup in percentage
		int support = (int) (minsup * sequenceCount / 100);
		
		System.out.println("support= "+minsup+"%, supp= "+support);
		System.out.println();
		
		SequenceHandler.print();
		sequenceDatabase.print();
	}
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = TestBIDEPlus.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}

}
