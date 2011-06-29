package util;

import java.util.Calendar;

public class SimpleTimer {
	
	protected long start = Calendar.getInstance().getTimeInMillis();
	protected long end = 0;
	
	
	/**
	 * Starts the timer.
	 * <p>
	 * Returns milliseconds since epoch of current time.
	 * 
	 * @return
	 */
	public long tic(){
		start = Calendar.getInstance().getTimeInMillis();
		return start;
	}
	
	/**
	 * Polls the timer.
	 * <p>
	 * Returns the time elapsed since <code>tic()</code> was called, in
	 * milliseconds since epoch units.
	 * 
	 * @return
	 */
	public long toc(){
		end = Calendar.getInstance().getTimeInMillis();
		return end-start;
	}
}
