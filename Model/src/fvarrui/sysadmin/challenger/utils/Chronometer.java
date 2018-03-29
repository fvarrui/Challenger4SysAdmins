package fvarrui.sysadmin.challenger.utils;

public class Chronometer {
	
	private long init, stop;
	
	public Chronometer() {
		init = 0;
	}
	
	public void init() {
		init = System.currentTimeMillis();
		stop = -1;
	}

	public void stop() {
		stop = System.currentTimeMillis();
	}

	public long getDiff() {
		if (stop == -1) return System.currentTimeMillis() - init;
		return stop - init;
	}
	
	@Override
	public String toString() {
		return "(" + init + "-" + stop + ")";
	}

}
