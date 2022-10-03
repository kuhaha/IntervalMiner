package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

/**
 * 
 * @author kuhaha
 * TIEP: time interval end point
 */
public class Tiep {
	protected int time; // start or finish time of an interval
	protected int symbol; // +n, start time, -n, finish time
	
	Tiep(int t, int s){
		time = t;
		symbol = s;
	}
	public boolean isStart() {
		return symbol > Constants.SYMBOL_FROM;
	}
	public boolean isFinish() {
		return symbol < - Constants.SYMBOL_FROM;
	}
	
	public int time() {
		return Math.abs(time);
	}
	

}
