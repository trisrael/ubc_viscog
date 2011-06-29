package convergence;

import util.Asymptote;
import util.Globals;

/**
 *  From Ron:
 * 
	0. Two reference patches (RH and RL), located top and bottom.
		(different instances each trial)
	    Two test patches (left and right) in area between:
	      - contents are either high (H) vs centre (C) correlations,
			or centre (C) vs low (L) correlations
	      - each trial alternates between H vs C and C vs L
	      - initial value of H is RH, L is RL, C = (1/2)*(H+L)
	1a. When H vs C shown:
	      - if C is chosen, decrease H by one step
	      - if H is chosen, increase H by three units (equil = 25%)
		- if H is 1.0, keep at 1.0
	1b. When C vs L shown:
	      - if C is chosen, increase L by one step
	      - if H is chosen, decrease L by three units (equil = 25%)
		- if L is 0.0, keep at 0.0
	2. After both 1a and 1b completed, set new value of C = (1/2)*(H+L)
	3. If not yet converged, goto 0.
	 
	Convergence can be defined as both the H vs C and C vs L series
	converging.  (This can probably be done using the same method as
	for the jnd's.)
	
	Notes:
	Summary for 1: if the base is chosen, move the variant towards it
	 			   by 1 step, otherwise move away by 3. Cap at [0,1]
 * 
 **/

// TODO: consider implementing this class to extend ModifiedBasicPEST

public class ModifiedBasicSteve extends AbstractConvergence{
	
	protected double RH;	// reference high
	protected double RL;	// reference low
	protected double H;
	protected double L;
	protected double C;
	
	protected int currentTrialType;
	protected double stepSize;
	protected Asymptote A_HC = new Asymptote();	// keep track of H
	protected Asymptote A_CL = new Asymptote();	// keep track of L
	
	// TODO: Think of a smarter way to balance HC/CL trials
	// Going to use in the "stupid way" of balancing HC/CL trials
	// i.e. run all trials in pairs, pick a random first trial
	//      type, then pick the opposite for the second. Repeat
	//      this every 2 trials (or every N trials).
	protected boolean isFirstTrial;
	protected boolean isTypeHC;
	protected int firstTrialType;
	

	/**
	 * Constructor
	 * 
	 * @param RH		High reference value
	 * @param RL		Low reference value
	 * @param stepSize	Initial step size of convergence algorithm
	 */
	public ModifiedBasicSteve(double RH, double RL, double stepSize) {
		this.RH = RH;
		this.RL = RL;
		this.H = RH;
		this.L = RL;
		this.C = 0.5*(H+L);
		this.stepSize = stepSize;
		this.isTypeHC = Globals.GLOBAL_RANDOM.nextBoolean();
		this.isFirstTrial = true;
	}
	
	/**
	 * Responds to the response of a trail.
	 */
	public void setTrialResponse(boolean isCorrect) {
		this.isFirstTrial = !this.isFirstTrial; // just keep switching
		adjustParams(isCorrect);
		pushValueToAsymptote();
		if(!isFirstTrial)adjustBias();
		setNextTrialType();
		this.isConverged = A_HC.isConverged() && A_CL.isConverged();
	}
	
	/**
	 * Returns the high reference value.
	 * 
	 * @return	high reference
	 */
	public double getRH(){
		return this.RH;
	}
	
	/**
	 * Returns the low reference value.
	 * 
	 * @return	low reference
	 */
	public double getRL(){
		return this.RL;
	}
	
	/**
	 * Helper function to add trial parameters to asymptotes.
	 */
	protected void pushValueToAsymptote(){
		if(isTypeHC){
			A_HC.addValue(H);
		}else{
			A_CL.addValue(L);
		}
	}
	
	/**
	 * Helper function to adjust H and L.
	 * 
	 * @param isCorrect		was the current response correct?
	 */
	protected void adjustParams(boolean isCorrect){
		if(isTypeHC){
			if(isCorrect) H = Math.max(H-stepSize, C);
			else	H = Math.min(H+3*stepSize, 1.0);
		}else{
			if(isCorrect) L = Math.min(L+stepSize,C);
			else	L = Math.max(L-3*stepSize, 0.0);
		}
	}
	
	/**
	 * Sets the next trial type.
	 * <p>
	 * HC means: high compared to centre value
	 */
	protected void setNextTrialType(){
		if(isFirstTrial){
			isTypeHC = !isTypeHC;
		}else{
			isTypeHC = Globals.GLOBAL_RANDOM.nextBoolean();
		}
	}
	
	/**
	 * Returns the 'correct' answer for the current trial.
	 * <p>
	 * For these trials, it's assumed that C is closer to the centre
	 * than H or L. That being said, C will always be the correct answer.
	 * Note that the assumption of a shifted bias of centre point
	 * may not be correct, so that's what we're trying to determine
	 * with these experiments.
	 */
	public double getTrialParam() {
		return this.C;	// in this case, the adjusting centre is always correct
	}
	
	/**
	 * Returns the 'incorrect' answer for the current trial.
	 * <p>
	 * This value is useful when setting up the appropriate Screen for
	 * this trial.
	 */
	public double getTrialCompare() {
		return (this.isTypeHC)?this.H:this.L;
	}

	/**
	 * Adjusts C.
	 * <p>
	 * This adjusts the bias parameter in the Steven's Law
	 * experiment.
	 */
	protected void adjustBias(){
		this.C = 0.5*(this.H + this.L);
	}

	public double getWindowAverage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTrialsToConverge() {
		// TODO Auto-generated method stub
		return 0;
	}
}
