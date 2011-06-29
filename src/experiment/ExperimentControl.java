package experiment;

import screens.Screen;

public class ExperimentControl {
	public static final int EXP_BEGIN = 0;				// Experiment begin
	public static final int EXP_DISPLAY = 1;			// Displaying stimuli
	public static final int EXP_FEEDBACK = 2;			// Displaying feedback
	public static final int EXP_FEEDBACK_WRONG_KEY = 6;	// Used so experiment feedback screens don't get recorded as data
	public static final int EXP_END = 3;				// End of experiment
	public static final int EXP_BREAK_START = 4;		// Start of break
	public static final int EXP_BREAK_END = 5;			// End of break
	
	protected int currentState = EXP_BEGIN;
	protected boolean isExpEnd = false;
	
	public int getCurrentState(){
		return currentState;
	}
	
	public void setExpEnd(){
		isExpEnd = true;
	}
	
	public int getNextState(){
		int nextState = -1;
		
		switch(currentState){
		case EXP_BEGIN:
			nextState = EXP_DISPLAY;
			break;
		case EXP_DISPLAY:
			nextState = EXP_FEEDBACK;
			break;
		case EXP_FEEDBACK:
			nextState = isExpEnd?EXP_END:EXP_DISPLAY;
			break;
		case EXP_FEEDBACK_WRONG_KEY:
			nextState = isExpEnd?EXP_END:EXP_DISPLAY;
			break;
		case EXP_END:
			nextState = EXP_END;
			break;
		}
		return nextState;
	}
	
	public void processInput(int keyPressed, Screen currentScreen){
		switch(currentState){
		case EXP_BEGIN:
		case EXP_END:
		case EXP_FEEDBACK_WRONG_KEY:
			// only transition state if key matches 'correct' key
			if(keyPressed == currentScreen.getCorrectKey()){
				currentState = getNextState();}
			break;
		case EXP_FEEDBACK:
			// TODO: put this logic in the right place (i.e. getNextState())
			if(keyPressed == currentScreen.getCorrectKey()){
				currentState = getNextState();
			}else{
				// i.e. don't go back to the EXP_FEEDBACK state again otherwise
				//		the 'correct' or 'incorrect' of the original feedback
				//		screen will now be registered as experiment feedback!
				// TODO: find better way to deal with this problem
				currentState = EXP_FEEDBACK_WRONG_KEY;
			}
			break;
		case EXP_DISPLAY:
			// always transition to the next state
			currentState = getNextState();
			break;
		}
	}
}
