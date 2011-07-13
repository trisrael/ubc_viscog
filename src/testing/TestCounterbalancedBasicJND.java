package testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import log.LogfileRow;
import screens.ExperimentEndScreen;
import screens.Screen;
import screens.TestBeginScreen;
import screens.TestCorrectScreen;
import screens.TestIncorrectScreen;
import screens.TestRightScreen;
import util.Globals;
import util.Util;
import convergence.Convergence;
import experiment.BasicJNDCondition;
import experiment.Condition;
import experiment.ExperimentControl;

public class TestCounterbalancedBasicJND extends JFrame implements KeyListener{
	
	// TODO: add more states if needed
	// TODO: generalise this approach
	public static final int ORDER_BLOCKED = 1;
	public static final int ORDER_RANDOMIZED = 2;

	// Store conditions here
	// Note: not sure what the best data type to use is
	//		 though I do know that I might want to use 
	//		 java.util.Collections.shuffle(List, Random)
	//		 at some point
	protected List myConditions = new Vector();
	
	// Key mappings
	final int KEY_SPACE = KeyEvent.VK_SPACE;
	
	// avoid serialisation warnings
	private static final long serialVersionUID = 1L;
	
	public static final String LOG_FOLDER = ".\\logs\\";
	
	private int trialNum = 1;			// current trial number
	private int currentCondNum = 0;		// current condition number
	private int capComplete = 30;		// number of trials to cap conditions
	private int w, h;					// Display height and width
	
	// Experiment state machine
	ExperimentControl expCtrl = new ExperimentControl();
	
	// Screens
	Screen beginScreen = new TestBeginScreen();
	Screen correctScreen = new TestCorrectScreen();
	Screen incorrectScreen = new TestIncorrectScreen();
	Screen currentStimuliScreen = new TestRightScreen();
	Screen currentScreen;
	
	Image screenImage;
	
	// Data output file name
	private String dataFilename;
	
	// Conditions stuff
	private Condition currentCondition;
	private Vector conditions = new Vector();
	
	// Place-holder
	private Convergence myConverge;
	
	// Constructor
	TestCounterbalancedBasicJND(Vector importedConditions){
		
		// TODO: fix shallow copy
		conditions = importedConditions;
		currentCondition = (BasicJNDCondition)conditions.get(currentCondNum);
		
		// Set all conditions to end at a maximum of completeCap trials
		for(int i = 0; i<conditions.size(); i++)((BasicJNDCondition)conditions.get(i)).setTrialCompleteCap(capComplete);
		
		// Filename to output data to
		dataFilename = Util.getNow("yyyy-MM-dd_HHmmss");
		dataFilename = dataFilename+".txt";
		
		
		System.out.println(LogfileRow.getTitle());
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FOLDER+dataFilename, false));
			out.write(LogfileRow.getTitle() + "\n");
			out.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
		
		currentScreen = beginScreen;
		screenImage = currentScreen.getImage();
		
		setupListeners();
		setupFullscreenWindow();
		
		this.update(this.getGraphics());
		
	}
	
	private void setupListeners(){
		// Exit program on window close
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		addKeyListener(this);
		
	}
	
	private void setupFullscreenWindow(){
		// set background color
		this.setBackground(Color.BLACK);
		
		// remove window frame
		this.setUndecorated(true);
		
		// window should be visible
		this.setVisible(true);
		
		// switch to fullscreen mode
		GraphicsEnvironment.getLocalGraphicsEnvironment().
		getDefaultScreenDevice().setFullScreenWindow(this);
		
		w = this.getWidth();
		h = this.getHeight();
		
		if(Globals.isDebug){
			System.out.println("Display: " + String.valueOf(w) + "x" + String.valueOf(h));
		}
	}
	
	public void paint(Graphics g){
		if (screenImage != null)
			g.drawImage(screenImage,
					w/2-screenImage.getWidth(this)/2,
					h/2-screenImage.getHeight(this)/2,
					this);
	}

	// From KeyListener
	public void keyPressed(KeyEvent arg0) {
				
		int correctKey = currentScreen.getCorrectKey();
		int currentKey = arg0.getKeyCode();
		
		// Exit program if ESC is pressed
		if(currentKey == KeyEvent.VK_ESCAPE) System.exit(0);
		
		if(Globals.isDebug){
			System.out.println("Current: " + currentKey);
			System.out.println("Correct: " + correctKey);
		}
		
		expCtrl.processInput(currentKey, currentScreen);
		
		switch(expCtrl.getCurrentState()){
		case ExperimentControl.EXP_BEGIN:
			currentScreen = this.beginScreen;
			break;
		case ExperimentControl.EXP_FEEDBACK:
			
			// Append data to log file
			appendLogfileRow(Globals.GLOBAL_TIMER.toc(), correctKey, currentKey);			
			
			// Set feedback screen and increment
			if(currentCondition.setTrialResponse(currentKey)){
				currentScreen = this.correctScreen;
			}else{
				currentScreen = this.incorrectScreen;
			}
			this.trialNum ++;
			
			// Handle completed conditions
			if(currentCondition.isCompleted()){
				currentCondNum ++;
				if(currentCondNum >= conditions.size()){
					expCtrl.setExpEnd();
					currentScreen = new ExperimentEndScreen();
					//doExperimentEnd();  // stupid hack, TODO: something smarter
				}else{
					currentCondition = (BasicJNDCondition)(conditions.get(currentCondNum));
				}
			}
			break;
		case ExperimentControl.EXP_END:
			doExperimentEnd();
			break;
		case ExperimentControl.EXP_DISPLAY:

			currentScreen = currentCondition.getNextScreen();
			Globals.GLOBAL_TIMER.tic();
			break;
		}
		
		screenImage = currentScreen.getImage();
		this.update(this.getGraphics());
							
	}
	
	private void doExperimentEnd(){
		System.out.println("Experiment Complete!");
		System.exit(0);
	}
	
	private void appendLogfileRow(long trial_time_in_ms, int correctKey, int currentKey){
		
		// very very dumb way to implement log file
		// LogfileRow(int trialNum, double trialTime, double base, double compare, double difference, boolean isCorrect, boolean isAscending, String side)
		// TODO: make the condition themselves print to the log file
		myConverge = currentCondition.getConvergence();
		boolean isDescending = ((BasicJNDCondition)currentCondition).getIsDescending();
		boolean isBaseOnLeft = ((BasicJNDCondition)currentCondition).getIsBaseOnLeft();
                double scalingVal = ((BasicJNDCondition)currentCondition).getScalingVal();
                int numPoint = ((BasicJNDCondition)currentCondition).getNumPoints();
                double dotSize = ((BasicJNDCondition)currentCondition).getDotSize();
                int dotStyle = ((BasicJNDCondition)currentCondition).getDotStyle();

		LogfileRow lr = new LogfileRow(trialNum, trial_time_in_ms, myConverge.getTrialParam(), myConverge.getTrialCompare(), Math.abs(myConverge.getTrialCompare()-myConverge.getTrialParam()), correctKey==currentKey, !isDescending, isBaseOnLeft?LogfileRow.RIGHT:LogfileRow.LEFT, scalingVal, dotSize, numPoint, dotStyle, 1);
		System.out.print(lr.toString() + "\n");
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FOLDER+dataFilename, true));
			out.write(lr.toString() + "\n");
			out.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	// End KeyListener stuff
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String condConfFile = ".\\conf\\TestBasicJND.cond";
		
		Vector conditions = new Vector();		
		// public BasicJNDCondition(double base, double diff, boolean isConvAbove, int pointsLeft, int pointsRight, double errLeft, double errRight, double step)
		
		int pointsLeft = 500;
		int pointsRight = 500;
		double errLeft = 0.0001;
		double errRight = 0.0001;
		double step = 0.01;
		
		try {
			
			FileInputStream fstream = new FileInputStream(condConfFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				
				// split via whitespace
				String tokens[] = strLine.split("\\s");
				if(tokens.length >= 4){
					if(tokens[0].equals("BasicJND")){
						
						try{
						double base = Double.parseDouble(tokens[1]);
						double diff = Double.parseDouble(tokens[2]);
						boolean isDescending = Boolean.parseBoolean(tokens[3]);
                                                double scalingVal = 1;
						System.out.println("Parsed: " + base + " " + diff + " " + isDescending);
						conditions.add(new BasicJNDCondition(base, diff, isDescending, pointsLeft, pointsRight, errLeft, errRight, step, scalingVal, 1));
						}catch(Exception e){
							System.out.println("Parse error.");
						}
					}
				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		new TestCounterbalancedBasicJND(conditions);
	}

}
