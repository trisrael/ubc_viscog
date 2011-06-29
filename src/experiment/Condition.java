package experiment;

import convergence.Convergence;
import screens.Screen;

public interface Condition {
	public Screen getNextScreen();
	public boolean setTrialResponse(int keyPressed);
	public boolean isCompleted();
	public int getCurrentTrialNumber();
	public Convergence getConvergence();
}
