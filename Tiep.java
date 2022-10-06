package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

/**
 * 
 * @author kuhaha
 * TIEP: time interval end point
 */
public class Tiep implements Comparable<Tiep>{
	protected int time; // start or finish time of an interval
	protected int symbol; // +n, start time, -n, finish time
	protected STI intv;
	
	Tiep(int t, int s, STI v){
		time = t;
		symbol = s;
		intv = v;
		
	}
	public boolean isStart() {
		return symbol > Constants.SYMBOL_FROM;
	}
	public boolean isFinish() {
		return symbol < - Constants.SYMBOL_FROM;
	}
	
	public int symbol() {
		return Math.abs(symbol);
	}
	
	@Override
    public int compareTo(Tiep t){
        int cmp = Integer.compare(time, t.time);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(symbol, t.symbol); 
        if (cmp != 0) return cmp;
        return 0;
    }
	
	public String toString() {
		return time + "<" + symbol + ">";
	}

}
