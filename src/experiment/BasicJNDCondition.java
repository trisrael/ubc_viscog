package experiment;

import java.awt.event.KeyEvent;
import java.util.Random;

import convergence.Convergence;
import convergence.ModifiedBasicPEST;
import screens.Screen;
import screens.TestTwoCorrScreen;
import util.Globals;

/**
 * Runs a basic JND condition.
 * <p>
 * This will assume that all L and R trials are supposed to be randomised and not
 * dictated by any other means.
 * 
 * @author Will
 *
 */
public class BasicJNDCondition implements Condition {
	
	// Default Experiment Parameters
	protected double PEST_BASE = 0.7;
	protected double PEST_DIFF = 0.6;
	protected double PEST_STEP = 0.01;
	protected int NUM_POINTS_LEFT = 500;
	protected int NUM_POINTS_RIGHT = 500;
	protected double ERR_LEFT = 0.0001;
	protected double ERR_RIGHT = 0.0001;

        protected double DOT_SIZE = 1;
        protected double SCALING_VAL = 1;
        protected int DOT_STYLE = 1;
        protected int DOT_HUE = 1;

	
	protected int KEY_R = KeyEvent.VK_M;
	protected int KEY_L = KeyEvent.VK_Z;
	
	protected int trialNum = 1;
	
	// Condition is considered complete once trialNum > this number
	protected int trialNumCompleteCap = Integer.MAX_VALUE;
	
	protected boolean isDescending = false;
	protected boolean isBaseOnLeft = false;
	protected boolean isPickHigher = true;
    protected boolean isAxisOn = true;
    protected boolean isLabelsOn = true;
	
	Screen currentScreen = null;
	Convergence myConverge = null;
	
	// using the global random generator
	Random generator = Globals.GLOBAL_RANDOM;
	
	public BasicJNDCondition(double base, double diff, boolean isConvAbove,
			int pointsLeft, int pointsRight, double errLeft, double errRight, double step, double scaling, double dotSize){
		this.PEST_BASE = base;
		this.PEST_DIFF = diff;
		this.isDescending = isConvAbove;
		this.NUM_POINTS_LEFT = pointsLeft;
		this.NUM_POINTS_RIGHT = pointsRight;
		this.ERR_LEFT = errLeft;
		this.ERR_RIGHT = errRight;
		this.PEST_STEP = step;

                this.DOT_SIZE = dotSize;
                this.SCALING_VAL = scaling;
                //this.DOT_STYLE = dotStyle;

		myConverge = new ModifiedBasicPEST(PEST_BASE, PEST_DIFF, isDescending, PEST_STEP);
		setNextScreen();
	}

    	public BasicJNDCondition(double base, double diff, boolean isConvAbove,
			int pointsLeft, int pointsRight, double errLeft, double errRight, double step,
            boolean isAxisOn, boolean isLabelsOn, double scalingVal, double dotSize, int dotStyle, int dotHue){

                this.PEST_BASE = base;
		this.PEST_DIFF = diff;
		this.isDescending = isConvAbove;
		this.NUM_POINTS_LEFT = pointsLeft;
		this.NUM_POINTS_RIGHT = pointsRight;
		this.ERR_LEFT = errLeft;
		this.ERR_RIGHT = errRight;
		this.PEST_STEP = step;

                this.DOT_SIZE = dotSize;
                this.SCALING_VAL = scalingVal;
                this.DOT_STYLE = dotStyle;
                this.DOT_HUE = dotHue;

        myConverge = new ModifiedBasicPEST(PEST_BASE, PEST_DIFF, isDescending, PEST_STEP);
        this.isAxisOn = isAxisOn;
        this.isLabelsOn = isLabelsOn;

        setNextScreen();
            
	}

    public void setCompleteCap(int cap){
        if(cap < 1) return;
        this.trialNumCompleteCap = cap;
    }
	
	protected void setNextScreen(){
		isBaseOnLeft = generator.nextBoolean();
		
		if(isBaseOnLeft){
			// base on left
			currentScreen = new TestTwoCorrScreen(myConverge.getTrialCompare(), myConverge.getTrialParam(), NUM_POINTS_LEFT, ERR_LEFT, SCALING_VAL, DOT_SIZE, DOT_STYLE, DOT_HUE);
			
			if(isBaseHigher() && isPickHigher) currentScreen.setCorrectKey(KEY_L);
			else if(isBaseHigher() && !isPickHigher) currentScreen.setCorrectKey(KEY_R);
			else if(!isBaseHigher() && isPickHigher) currentScreen.setCorrectKey(KEY_R);
			else currentScreen.setCorrectKey(KEY_R);
		}else{
			// base on right
			currentScreen = new TestTwoCorrScreen( myConverge.getTrialParam(), myConverge.getTrialCompare(), NUM_POINTS_RIGHT, ERR_RIGHT, SCALING_VAL, DOT_SIZE, DOT_STYLE, DOT_HUE);
			
			if(isBaseHigher() && isPickHigher) currentScreen.setCorrectKey(KEY_R);
			else if(isBaseHigher() && !isPickHigher) currentScreen.setCorrectKey(KEY_L);
			else if(!isBaseHigher() && isPickHigher) currentScreen.setCorrectKey(KEY_L);
			else currentScreen.setCorrectKey(KEY_L);
		}

        // TODO: figure out a better way to do screen option passing!
        ((TestTwoCorrScreen)currentScreen).setDrawAxis(isAxisOn);
        ((TestTwoCorrScreen)currentScreen).setDrawLabels(isLabelsOn);
	}
	
	private boolean isBaseHigher(){
		return !isDescending;
	}

        public int getDotHue() {
            return DOT_HUE;
        }

        public int getDotStyle() {
            return DOT_STYLE;
        }

        public double getScalingVal() {
            return SCALING_VAL;
        }

        public double getDotSize() {
            return DOT_SIZE;
        }

        public int getNumPoints() {

            return NUM_POINTS_LEFT;
        }

	public int getCurrentTrialNumber() {
		return trialNum;
	}
	
	public double getWindowAverage(){
		return myConverge.getWindowAverage();
		
	}

	public Screen getNextScreen() {
		// TODO: Look into cloning instead... the current approach allows for future authors to
		//		 accidentally write over the Screen!
		
		// look into a better place to put this
		setNextScreen();
		
		return currentScreen;
	}

	public boolean isCompleted() {
		return myConverge.isConverged() || trialNum > this.trialNumCompleteCap;
	}

	public boolean setTrialResponse(int keyPressed) {
		this.trialNum ++;
		
		boolean isCorrect = currentScreen.getCorrectKey() == keyPressed;
		myConverge.setTrialResponse(isCorrect);
		
		// This really slows down the response
		// Placing into getNextScreen() where the delay will be in the feedback
		// setNextScreen();
		return isCorrect;
	}
	
	public void setTrialCompleteCap(int completeCap){
		this.trialNumCompleteCap = completeCap;
	}
	
	public Convergence getConvergence(){
		// TODO: return a clone, not the original!
		return myConverge;
	}
	
	public boolean getIsDescending(){return isDescending;}
	public boolean getIsBaseOnLeft(){return isBaseOnLeft;}
}
