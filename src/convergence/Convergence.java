package convergence;

public interface Convergence {
	
	/**
	 * Set the response from the current trial.
	 * 
	 * @param isCorrect		was the current trail correct or incorrect?
	 */
	public void setTrialResponse(boolean isCorrect);
	
	/**
	 * Get the current trial's parameter.
	 * <p>
	 * The parameter is the current value of the thing we're trying to
	 * measure the JND/Steven's Law/Contrast of.
	 * 
	 * @return	parameter for the current trial
	 */
	public double getTrialParam();
	
	/**
	 * Get the current trial's comparison value.
	 * <p>
	 * For the most part, this will often be the parameter that tracks
	 * the incorrect answer for each trial.
	 * 
	 * @return comparison parameter for the current trial
	 */
	public double getTrialCompare();
	
	/**
	 * Has this particular measurement converged?
	 * 
	 * @return	<code>true</code> if converged, <code>false</code> otherwise
	 */
	public boolean isConverged();

	
	/**
	 * Get the current conditions average values
	 * 
	 * @return	the average value inside all windows of the convergence algorithm
	 */
	public double getWindowAverage();
	
	/**
	 * Get the number of trials before convergence
	 * 
	 * @return	the number of trials needed to converge
	 */
	public double getTrialsToConverge();
}
