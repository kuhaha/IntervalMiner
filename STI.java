package ca.pfv.spmf.algorithms.sequentialpatterns.IntervalMiner;

public class STI implements Comparable<STI> {
	protected int start; // interval start
	protected int finish; // interval finish
	protected int symbol; // state symbol

	public STI(int s, int f, int m) {
		start = s;
		finish = f;
		symbol = m;
	}

	public int start() {
		return start;
	}

	public int duration() {
		return finish - start;
	}

	public int gap(STI other) {
		int g1 = other.start - this.finish;
		int g2 = this.start - other.finish;
		return Math.min(g1, g2);
	}

	public char relation(STI other) {
		if (this.finish < other.start)
			return Constants.ALLEN_BEFORE;
		if (this.finish == other.start)
			return Constants.ALLEN_MEET;
		if (this.start == other.start && this.finish == other.finish)
			return Constants.ALLEN_EQUAL;
		if (this.start < other.start && this.finish > other.finish)
			return Constants.ALLEN_CONTAIN;
		if (this.start == other.start && this.finish < other.finish)
			return Constants.ALLEN_STARTS;
		if (this.start < other.start && this.finish == other.finish)
			return Constants.ALLEN_FINISHBY;
		return Constants.ALLEN_OVERLAP;
	}

	@Override
	public String toString() {
		return "<" + start + "," + finish + "," + symbol + ">";

	}

	@Override
	public int compareTo(STI other) {
		int cmp;
		cmp = Integer.compare(this.start, other.start);
		if (cmp != 0) {
			return cmp;
		}
		cmp = Integer.compare(this.finish, other.finish);
		if (cmp != 0) {
			return cmp;
		}
		return Integer.compare(this.symbol, other.symbol);
	}

}
