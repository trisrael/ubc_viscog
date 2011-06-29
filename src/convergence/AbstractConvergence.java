package convergence;

public abstract class AbstractConvergence implements Convergence{

	protected boolean isConverged = false;
	
	abstract public double getTrialParam();
	abstract public double getTrialCompare();
	abstract public void setTrialResponse(boolean isCorrect);
	
	public boolean isConverged() {
		return isConverged;
	}	
}
