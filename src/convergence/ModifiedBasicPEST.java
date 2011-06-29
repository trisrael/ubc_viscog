package convergence;

import util.Asymptote;

public class ModifiedBasicPEST extends AbstractConvergence{
	
	Asymptote data = new Asymptote();

    double MIN_CAP = 0.0;
    double MAX_CAP = 1.0;
	
	double currentStep = 0.0;
	double base;
	double difference;
	int numTrialsToAdjustStep = Integer.MAX_VALUE;
	int currentTrialNum = 1;
	boolean isConvergeFromAbove = false;
	
	double incorrectMultiplier = 3.0;
	
	/**
	 * Constructor.
	 * 
	 * @param base					the base value
	 * @param difference			the initial difference
	 * @param isConvergeFromAbove	is this convergence from above or below?
	 * @param stepSize				the step size
	 */
	public ModifiedBasicPEST(double base, double difference, boolean isConvergeFromAbove, double stepSize){
		this.base = base;
		this.difference = difference;
		this.currentStep = stepSize;
		this.isConvergeFromAbove = isConvergeFromAbove;
		
		data.addValue(difference);
	}
	
	public boolean setNumTrialsToAdjustStep(int trials){
		if (trials < 1) return false;
		
		return true;
	}
	
	public boolean isConverged(){
		return data.isConverged();
	}

	public double getTrialParam() {
		return isConvergeFromAbove?(base+difference):(base-difference);
	}
	
	public double getTrialCompare(){
		return base;
	}
	
	public double getBase(){
		return this.base;
	}
	
	public double getDifference(){
		return this.difference;
	}
	
	public int getCurrentTrialNum(){
		return currentTrialNum;
	}
	
	public double getWindowAverage(){
		return data.getConvergeWindowAverage();
	}
	
	public double getTrialsToConverge(){
		return data.getTrialsToConverge();
	}

	public void setTrialResponse(boolean isCorrect) {
		this.currentTrialNum++;
		adjustDifference(isCorrect);
		
		data.addValue(difference);
		
		// TODO: some test to change step size
	}
	
	protected void adjustDifference(boolean isCorrect){
		if(isCorrect){
			difference = Math.max(MIN_CAP, difference-currentStep);
		}else{
			if(isConvergeFromAbove){
				difference = Math.min(MAX_CAP-base, difference+currentStep*incorrectMultiplier);
			}else{
				//difference = difference+currentStep*incorrectMultiplier;
                difference = Math.min(base-MIN_CAP, difference+currentStep*incorrectMultiplier);
			}
		}
	}
	
	public String toString(){
		return data.toString();
	}
	
	public static void main(String args[]){
		Convergence myConv = new ModifiedBasicPEST(8.0, 2.0, true, 0.5);
		myConv.toString();
		
		boolean trials[] = {true, true, true, false, false, true, false, false, true, false, true};
		
		for(int i=0; i < trials.length; i++){
			myConv.setTrialResponse(trials[i]);
		}
		
		System.out.println(myConv.toString());
	}
	
}
