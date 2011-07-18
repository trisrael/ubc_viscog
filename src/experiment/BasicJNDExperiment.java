/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import convergence.Convergence;
import deploy.JND.ExperimentStartDialogue;
import deploy.JND.JNDExperimentConfFrame;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
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
import screens.TestTwoCorrScreen;
import util.Globals;
import util.LatinSquareGenerator;
import util.Util;

/**
 *
 * @author Will
 */
public class BasicJNDExperiment extends JFrame implements Experiment, KeyListener {

// Ben 4/8/11 to make this program handle various design conditions
//    final private String expCond = "ScalingCond";
//    final private String expCond = "PointNumCond";
//    final private String expCond = "PointSizeCond";
//    final private String expCond = "PointStyleCond";
//    final private String expCond = "PointHueCond";
//    final private String expCond = "PointColorCond";
//    final private String expCond = "PointRingCond";
//    final private String expCond = "PointTeeCond";
    final private String expCond = "PointShapeCond";

    public class TwoCorrImageThread extends Thread { // emily 06/2010

        public Screen screen;
        public Image image;
        //Override

        public void run() {
            screen = currentCondition.getNextScreen();
            image = screen.getImage();
        }
    }
    private int iPointsLeft = 100;
    final private String iPointsLeftLabel = "iPointsLeft";
    private int iPointsRight = 100;
    final private String iPointsRightLabel = "iPointsRight";
    private int iCapComplete = 30;
    final private String iCapCompleteLabel = "iCapComplete";
    private int iScreenResX = 500;
    final private String iScreenResXLabel = "iScreenResX";
    private int iScreenResY = 500;
    final private String iScreenResYLabel = "iScreenResY";
    private int iTestNumTrials = 30;
    final private String iTestNumTrialsLabel = "iTestNumTrials";
    private double dErrLeft = 0.0001;
    final private String dErrLeftLabel = "dErrLeft";
    private double dErrRight = 0.0001;
    final private String dErrRightLabel = "dErrRight";
    private double dStep = 0.01;
    final private String dStepLabel = "dStep";
    private boolean bIsAxisOn = false;
    final private String bIsAxisOnLabel = "bIsAxisOn";
    private boolean bIsLabelsOn = false;
    final private String bIsLabelsOnLabel = "bIsLabelsOn";
    private boolean bIsFilterKeys = false;
    final private String bIsFilterKeysLabel = "bIsFilterKeys";
    static final String FOLDER_CONF = "./conf/";
    static final String FOLDER_LOGS = "./logs/";
    static final String CONF_COND_NAME = "BasicJND.cond";
    static final String CONF_EXP_NAME = "BasicJND.conf";
    static final String CONF_WARNING = "# Generated file. Don't edit by hand.";
    static final String CONF_DELIMITER = " = ";
    private String sCondConfFile = FOLDER_CONF + CONF_COND_NAME;
    private String sExpConfFile = FOLDER_CONF + CONF_EXP_NAME;
    // Stuff beyond here doesn't need to be saved in the config file
    private boolean isDebug = true;
    private boolean isTest = true;
    private boolean isCorrect = false;
    private boolean bIsBlocked = true;          // assumed blocked for now
    private boolean bIsCounterbalanced = true;  // assumed counterbalanced
    // Conditions to run
    protected List conditionsQueue = new Vector();
    private int trialNum = 1;       // current trial number
    private int currentCondNum = 0; // current condition number
    // Configuration frame
    JNDExperimentConfFrame myConfFrame = new JNDExperimentConfFrame(this);
    // Experiment control
    ExperimentControl expCtrl = new ExperimentControl();
    // A vector of vectors of Integers which hold our latin square for
    // counterbalancing
    Vector latinSquare = null;
    // Screens
    Screen beginScreen = new TestBeginScreen();
    Screen correctScreen = new TestCorrectScreen();
    Screen incorrectScreen = new TestIncorrectScreen();
    Screen currentStimuliScreen = new TestRightScreen();
    Screen currentScreen;
    Image screenImage;
    // Data output file name
    private String dataFilename;
    private String summaryFilename;
    private String XMLFilename;
    private String dataFolder;
    // Conditions stuff
    private Condition currentCondition;
    private Vector conditions = new Vector();
    // Place-holder
    private Convergence myConverge;
    // width and height of display
    int w, h;
    // current subject number
    private int currentSubjectNumber = 1;
    // GetImageThread
    private TwoCorrImageThread imgThread; // emily 06/2010

    /**
     * Constructor.
     */
    public BasicJNDExperiment() {
        super();
        this.loadConfiguration();
        this.setupConfFrame();

        // remove window borders, etc.
        this.setUndecorated(true);


        // Handle a closing window
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                handleWindowClose();
            }
        });

        // set cursor to invisible
        Toolkit tk = Toolkit.getDefaultToolkit();
        Cursor invisibleCursor = tk.createCustomCursor(tk.createImage(""), new Point(), null);
        this.setCursor(invisibleCursor);

    }

    /**
     * Returns the string being used as a delimiter.
     * 
     * @return
     */
    public static String getConfDelimiter() {
        return CONF_DELIMITER;
    }

    /**
     * Sets up the configuration editor frame.
     */
    private void setupConfFrame() {
        myConfFrame.clearTable();
        myConfFrame.addConfRow("Number of points on left", new Integer(iPointsLeft), Integer.class, iPointsLeftLabel, new Integer(1), new Integer(5000));
        myConfFrame.addConfRow("Number of points on right", new Integer(iPointsRight), Integer.class, iPointsRightLabel, new Integer(1), new Integer(5000));
        myConfFrame.addConfRow("Max trials per condition", new Integer(iCapComplete), Integer.class, iCapCompleteLabel, new Integer(1), new Integer(1000));
        myConfFrame.addConfRow("Horizontal resolution", new Integer(iScreenResX), Integer.class, iScreenResXLabel, new Integer(1), new Integer(50000));
        myConfFrame.addConfRow("Vertical resolution", new Integer(iScreenResY), Integer.class, iScreenResYLabel, new Integer(1), new Integer(50000));
        myConfFrame.addConfRow("Number of test trials", new Integer(iTestNumTrials), Integer.class, iTestNumTrialsLabel, new Integer(1), new Integer(1000));
        myConfFrame.addConfRow("Left R error", new Double(dErrLeft), Double.class, dErrLeftLabel, new Double(0.0), new Double(1.0));
        myConfFrame.addConfRow("Right R error", new Double(dErrRight), Double.class, dErrRightLabel, new Double(0.0), new Double(1.0));
        myConfFrame.addConfRow("Step size", new Double(dStep), Double.class, dStepLabel, new Double(0.000001), new Double(1.0));
        myConfFrame.addConfRow("Axis on", new Boolean(bIsAxisOn), Boolean.class, bIsAxisOnLabel, new Boolean(false), new Boolean(true));
        myConfFrame.addConfRow("Labels on", new Boolean(bIsLabelsOn), Boolean.class, bIsLabelsOnLabel, new Boolean(false), new Boolean(true));
        myConfFrame.addConfRow("Filter disallowed keys", new Boolean(bIsFilterKeys), Boolean.class, bIsFilterKeysLabel, new Boolean(false), new Boolean(true));
    }

    /**
     * Returns the string for the configuration file for the current
     * experiment configuration.
     *
     * @return
     */
    public String getConfString() {
        String temp = "";
        temp = temp + CONF_WARNING + "\n";
        temp = appendConfLine(temp, bIsAxisOnLabel, new Boolean(bIsAxisOn).toString());
        temp = appendConfLine(temp, bIsLabelsOnLabel, new Boolean(bIsLabelsOn).toString());
        temp = appendConfLine(temp, bIsFilterKeysLabel, new Boolean(bIsFilterKeys).toString());
        temp = appendConfLine(temp, dErrLeftLabel, new Double(dErrLeft).toString());
        temp = appendConfLine(temp, dErrRightLabel, new Double(dErrRight).toString());
        temp = appendConfLine(temp, dStepLabel, new Double(dStep).toString());
        temp = appendConfLine(temp, iCapCompleteLabel, new Integer(iCapComplete).toString());
        temp = appendConfLine(temp, iPointsLeftLabel, new Integer(iPointsLeft).toString());
        temp = appendConfLine(temp, iPointsRightLabel, new Integer(iPointsRight).toString());
        temp = appendConfLine(temp, iScreenResXLabel, new Integer(iScreenResX).toString());
        temp = appendConfLine(temp, iScreenResYLabel, new Integer(iScreenResY).toString());
        temp = appendConfLine(temp, iTestNumTrialsLabel, new Integer(iTestNumTrials).toString());
        return temp;
    }

    /**
     * Parses a value from a given string read from an experiment's
     * configuration file.
     *
     * @param var
     * @param value
     */
    private void parseValue(String var, String value) {
        try {
            if (var.equalsIgnoreCase(bIsAxisOnLabel)) {
                bIsAxisOn = Boolean.parseBoolean(value);
            } else if (var.equalsIgnoreCase(bIsLabelsOnLabel)) {
                bIsLabelsOn = Boolean.parseBoolean(value);
            } else if (var.equalsIgnoreCase(bIsFilterKeysLabel)) {
                bIsFilterKeys = Boolean.parseBoolean(value);
            } else if (var.equalsIgnoreCase(dErrLeftLabel)) {
                dErrLeft = Double.parseDouble(value);
            } else if (var.equalsIgnoreCase(dErrRightLabel)) {
                dErrRight = Double.parseDouble(value);
            } else if (var.equalsIgnoreCase(dStepLabel)) {
                dStep = Double.parseDouble(value);
            } else if (var.equalsIgnoreCase(iCapCompleteLabel)) {
                iCapComplete = Integer.parseInt(value);
            } else if (var.equalsIgnoreCase(iPointsLeftLabel)) {
                iPointsLeft = Integer.parseInt(value);
            } else if (var.equalsIgnoreCase(iPointsRightLabel)) {
                iPointsRight = Integer.parseInt(value);
            } else if (var.equalsIgnoreCase(iScreenResXLabel)) {
                iScreenResX = Integer.parseInt(value);
            } else if (var.equalsIgnoreCase(iScreenResYLabel)) {
                iScreenResY = Integer.parseInt(value);
            } else if (var.equalsIgnoreCase(iTestNumTrialsLabel)) {
                iTestNumTrials = Integer.parseInt(value);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Attempts to clear the majority of pointers from this experiment.
     * <p>
     * This is in hopes that the java garbage collection will free this memory
     * when needed.
     */
    public void clearExperiment() {
        this.conditions.clear();
        this.conditionsQueue.clear();
        this.myConverge = null;
        this.currentCondition = null;
    }

    /**
     * Sets up a brand new experiment.
     */
    public void setupExperiment() {
        this.isTest = false;
        this.clearExperiment();
        loadConfiguration();
        loadConditions();
        resetExperimentParameters();

        // Get a latin square nxn size, where n is the number of conditions
        // loaded. Note that this square goes from 1 to n (rather than 0 to n-1)
        //latinSquare = LatinSquareGenerator.getBasicBalancedLatinSquare(conditions.size());
    }

    public void setupTest() {
        this.isTest = true;
        this.clearExperiment();
        loadConfiguration();

        // **Change number of points for test trials here
        // order of values is: startL, startR, pointsL, pointsR, err, err, step-size, xx, xx, scaling, pointSize, pointStyle, pointHue
        BasicJNDCondition testCondition = new BasicJNDCondition(0.1, 0.8, true, 100, 100, 0.001, 0.001, 0.05, true, true, 0.9, 8, 15, 1);
        ((BasicJNDCondition) testCondition).setCompleteCap(this.iTestNumTrials);
        conditions.add(testCondition);

        resetExperimentParameters();
    }

    /**
     * Resets experiment parameters.
     */
    public void resetExperimentParameters() {
        this.trialNum = 1;
        this.currentCondNum = 0;
        this.expCtrl = new ExperimentControl();

    }

    /**
     * Helper to append a line to a configuration file string.
     * 
     * @param confString
     * @param var
     * @param value
     * @return
     */
    private String appendConfLine(String confString, String var, String value) {
        return confString + var + CONF_DELIMITER + value + "\n";
    }

    /**
     * Creates the log file and folder for this experiment.
     */
    private void createLogfileAndFolder() {

        // create log folder (to dump distributions, other data, etc.)
        try {
            File myFolder = new File(FOLDER_LOGS + dataFolder);
            myFolder.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // create logfile
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FOLDER_LOGS + dataFilename, false));
            out.write(LogfileRow.getTitle() + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // create a summary of a subjects data
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FOLDER_LOGS + summaryFilename, false));
            out.write("level	above	JND	trials  scalingVal dotSize numPoints dotStyle dotHue" + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    /**
     * Backs up the current configuration (in case we'd like to check that things were ok)
     */
    private void backupConfiguration() {
        Util.safeCopyFile(new File(this.sCondConfFile), new File(FOLDER_LOGS + dataFolder + "/" + CONF_COND_NAME));
        Util.safeCopyFile(new File(this.sExpConfFile), new File(FOLDER_LOGS + dataFolder + "/" + CONF_EXP_NAME));
    }

    /**
     * Runs an experiment given a subjectID string.
     *
     * @param subjectID
     */
    public void run(String subjectID) {
        this.loadConfiguration();
        createLogfileAndFolder();
        backupConfiguration();
        setVisibleAndRun();
    }

    /**
     * Places conditions into the conditionsQueue.
     * <p>
     * This function counterbalances if <code>bIsCounterbalanced</code> is true.
     *
     * @param subjectNumber
     */
    private void queueConditions(int subjectNumber) {
        if (isTest) {
            doSimpleQueueConditions();
        } else {
            doLatinSquaresQueueConditions(subjectNumber);
        }

        currentCondition = (Condition) conditionsQueue.get(0);

        currentScreen = beginScreen;
        screenImage = currentScreen.getImage();
    }

    /**
     * Loads the conditions in the order of the simple latin squares generator
     * in LatinSquareGenerator.
     *
     * @param subjectNumber
     */
    private void doLatinSquaresQueueConditions(int subjectNumber) {
        //latinSquare = LatinSquareGenerator.getBasicBalancedLatinSquare(conditions.size());

        //Vector conditionOrder = (Vector)this.latinSquare.get(subjectNumber%latinSquare.size());

        Vector conditionOrder = LatinSquareGenerator.getConditionOrder(subjectNumber);

        // replace nulls with stuff
        for (int i = 0; i < conditionOrder.size(); i++) {
            conditionsQueue.add(conditions.get(((Integer) conditionOrder.get(i)).intValue()));
        }

        // not sure the purpose here...substituted in "subjectNumber" here
        if (isDebug) {
            System.out.println("Used order (offset by " + subjectNumber + "): " + conditionOrder.toString());
        }

    }

    /**
     * Simply places all of the conditions from <code>conditions</code> into
     * <code>conditionsQueue</code> in the same order.
     * <p>
     * If blocked, all conditions are run in the order of <code>conditionsQueue
     * </code>.
     */
    private void doSimpleQueueConditions() {
        // add conditions to the queue
        for (int i = 0; i < conditions.size(); i++) {
            conditionsQueue.add(i, conditions.get(i));
        }
    }

    /**
     * Makes this frame visible and runs through the current experiment setup.
     */
    public void setVisibleAndRun() {

        queueConditions(currentSubjectNumber);
        this.addKeyListener(this);
        doFullscreen();
        this.update(this.getGraphics());
        this.setVisible(true);
    }

    /**
     * Shows a dialogue to get some user input, sets up some experiment
     * parameters and runs the experiment.
     */
    public void run() {

        // TODO: figure out how to start animated loading frame in its own
        //       thread
        //Globals.ANIMATED_LOADING_FRAME.setVisible(true);

        this.setupExperiment();

        //Globals.ANIMATED_LOADING_FRAME.setVisible(false);

        // dialogue to get subject number and initials/ID/etc.
        StringBuffer initials = new StringBuffer(ExperimentStartDialogue.MAX_CHARS);
        StringBuffer subjectNumber = new StringBuffer(ExperimentStartDialogue.MAX_DIGITS);

        boolean isStart = ExperimentStartDialogue.showDialogue(this, initials, subjectNumber);

        // don't start experiment if we hit 'cancel'
        if (isStart == false) {
            return;
        }

        subjectNumber.trimToSize();
        initials.trimToSize();

        dataFilename = Util.getNow("yyyy-MM-dd_HHmmss") + "_" + expCond + "_" + subjectNumber.toString() + "_" + initials.toString() + ".txt";
        summaryFilename = Util.getNow("yyyy-MM-dd_HHmmss") + "_" + "_" + expCond + "_" + subjectNumber.toString() + "_" + initials.toString() + "_summary" + ".txt";
        XMLFilename = Util.getNow("yyyy-MM-dd_HHmmss") + "_" + "_" + expCond + "_" + subjectNumber.toString() + "_" + initials.toString() + "_XML" + ".xml";
        dataFolder = Util.getNow("yyyy-MM-dd_HHmmss") + "_" + expCond + "_" + subjectNumber.toString() + "_" + initials.toString();
        if (isDebug) {
            System.out.println(dataFilename);
        }

        this.currentSubjectNumber = Integer.parseInt(subjectNumber.toString());
        run(subjectNumber.toString());
    }

    /**
     * What to do when the experiment window is closed.
     */
    private void handleWindowClose() {
        this.setVisible(false);
        this.dispose();
        this.removeKeyListener(this);
        this.clearExperiment();
    }

    /**
     * Makes the experiment window fullscreen.
     */
    private void doFullscreen() {
        // set background color
        this.setBackground(Color.BLACK);

        // switch to fullscreen mode
        GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().setFullScreenWindow(this);

        w = this.getWidth();
        h = this.getHeight();

    }

    /**
     * Draws the current experiment image as the current frame.
     * 
     * @param g
     */
    public void paint(Graphics g) {
        if (screenImage != null) {
            g.drawImage(screenImage,
                    w / 2 - screenImage.getWidth(this) / 2,
                    h / 2 - screenImage.getHeight(this) / 2,
                    this);
        }
    }

    /**
     * Tests an experiment.
     */
    public void test() {
        this.setupTest();
        setVisibleAndRun();
    }

    /**
     * Shows the JFrame meant to configure this experiment.
     * 
     * @param isShowFrame
     */
    public void showConfigureFrame(boolean isShowFrame) {
        if (isShowFrame) {
            setupConfFrame();
            myConfFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            myConfFrame.setVisible(true);
        } else {
            myConfFrame.setVisible(false);
        }
    }

    /**
     * Loads an experiment configuration from a file.
     * 
     * @param configFile
     */
    public void loadConfigurationFromFile(File configFile) {
        try {
            FileInputStream fstream = new FileInputStream(configFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                if (isDebug) {
                    System.out.println("Debug: " + strLine);
                }

                String tokens[] = strLine.split(CONF_DELIMITER);
                if (tokens.length == 2) {
                    parseValue(tokens[0].trim(), tokens[1].trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks a string if it can be parsed properly.
     * <p>
     * TODO: complete this function if necessary
     *
     * @param conf
     * @return
     */
    public boolean checkConfigurationString(String conf) {
        String[] lines = conf.split("$");
        System.out.println(lines.length);

        for (int i = 0; i < lines.length; i++) {
            String tokens[] = lines[i].split(CONF_DELIMITER);
            if (tokens.length == 2) {
                try {
                    // TODO: place appropriate check code here
                    //parseValue(tokens[0].trim(), tokens[1].trim());
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loads configuration options from a string.
     * 
     * @param str
     */
    public void loadConfigurationFromString(String str) {
        String[] lines = str.split("\\n");

        for (int i = 0; i < lines.length; i++) {
            String tokens[] = lines[i].split(CONF_DELIMITER);
            if (tokens.length == 2) {
                try {
                    if (isDebug) {
                        System.out.println(tokens[0].trim() + "--" + tokens[1].trim());
                    }
                    parseValue(tokens[0].trim(), tokens[1].trim());
                } catch (Exception e) {
                    System.err.println("String parse error.");
                }
            }
        }
    }

    /**
     * Saves the current experiment configuration to a file.
     * 
     * @param configFile
     */
    public void saveConfigurationToFile(File configFile) {
        BufferedWriter out;

        try {
            out = new BufferedWriter(new FileWriter(configFile, false));
            out.write(this.getConfString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current experiment configuration to a predefined file.
     */
    public void saveConfiguration() {
        saveConfigurationToFile(new File(this.sExpConfFile));
    }

    /**
     * Loads the experiment configuration from a predefined file.
     */
    public void loadConfiguration() {
        loadConfigurationFromFile(new File(this.sExpConfFile));
    }

    /**
     * Loads conditions from a file.
     * <p>
     * All conditions are parsed from a conditions file, and using the values
     * from the current experiment's configuration new conditions are created
     * and placed into the Vector conditions. The pointers are then ordered
     * according to shuffling, counterbalancing, etc., and then inserted into
     * the List conditionsQueue, where they are run in the order of conditionsQueue.
     *
     * Note: this function should be called *after* the experiment configuration
     * is set as it relies on values in the configuration.
     * 
     * @param condConfFile
     */
    public void loadConditionsFromFile(File condConfFile) {
        try {

            FileInputStream fstream = new FileInputStream(condConfFile);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                System.out.println(strLine);


// Edited by Ben 09242010 to work for the scaling experiment.
// Reads in a scaling condition here, passes it to BasicJNDCond, which sends it to the
// the testtwocorr screen and eventually to the distribution2D file, where it is used to
// set the scaling factor to draw the scatterplot.
// Edited again 11/2/10; set the experimental condition at the top of the file as expCond
// now parses scaling, numPoints, and dotSize from the condition file for ALL conditions
// then passes these as necessary to the BasicJNDCond

                // split via whitespace
                String tokens[] = strLine.split("\\s");
                if (tokens.length >= 4) {
                    if (tokens[0].equals(expCond)) {

                        try {
                            double base = Double.parseDouble(tokens[1]);
                            double diff = Double.parseDouble(tokens[2]);
                            boolean isDescending = Boolean.parseBoolean(tokens[3]);
                            double scalingVal = Double.parseDouble(tokens[4]);
                            double dotSize = Double.parseDouble(tokens[5]);
                            int numPoints = Integer.parseInt(tokens[6]);
                            int dotStyle = Integer.parseInt(tokens[7]);
                            int dotHue = Integer.parseInt(tokens[8]);

                            if (isDebug) {
                                System.out.println("Parsed: " + base + " " + diff + " " + isDescending + " " + scalingVal);
                            }
                            BasicJNDCondition tempCond = new BasicJNDCondition(base, diff, isDescending, numPoints, numPoints,
                                    dErrLeft, dErrRight, dStep, bIsAxisOn, bIsLabelsOn, scalingVal, dotSize,
                                    dotStyle, dotHue);
                            tempCond.setCompleteCap(this.iCapComplete);
                            conditions.add(tempCond);
                        } catch (Exception e) {
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
    }

    /**
     * Loads conditions from the preset conditions file.
     */
    public void loadConditions() {
        loadConditionsFromFile(new File(this.sCondConfFile));
    }

    /**
     * Is the current key ok to press?
     * 
     * @param e
     * @param expCtrl
     * @return
     */
    public boolean isKeyAllowed(KeyEvent e, ExperimentControl expCtrl) {
        boolean result = false;
        int currentState = expCtrl.getCurrentState();

        switch (currentState) {
            case (ExperimentControl.EXP_DISPLAY):
                if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_M) {
                    result = true;
                }
                break;
            default:
                result = true;
        }
        return result;
    }

    // part of KeyListener
    public void keyTyped(KeyEvent e) {
    }

    // part of KeyListener
    public void keyReleased(KeyEvent e) {
    }

    // part of KeyListener
    public void keyPressed(KeyEvent e) {
        //int correctKey = currentScreen.getCorrectKey();
        int currentKey = e.getKeyCode();
        int correctKey = currentScreen.getCorrectKey();
        isCorrect = (currentKey == correctKey);

        // always close this window when ESC is pressed
        if (currentKey == KeyEvent.VK_ESCAPE) {
            this.getToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        // don't do anything if the current key pressed is not allowed
        if (this.bIsFilterKeys && !isKeyAllowed(e, expCtrl)) {
            return;
        }

        // update the experiment state
        expCtrl.processInput(currentKey, currentScreen);

        switch (expCtrl.getCurrentState()) {
            case ExperimentControl.EXP_BEGIN:
                currentScreen = this.beginScreen;
                screenImage = currentScreen.getImage();
                this.update(this.getGraphics());
                break;
            case ExperimentControl.EXP_FEEDBACK:
                // TODO: append data to log file if appropriate
                if (!isTest) {
                    appendLogfileRow(Globals.GLOBAL_TIMER.toc(), correctKey, currentKey);
                    if (currentScreen.getClass().equals(TestTwoCorrScreen.class)) {
                        writeScreenToFile(((TestTwoCorrScreen) currentScreen).getXMLString());
                    }
                }

                // set feedback and increment
                if (currentCondition.setTrialResponse(currentKey)) {
                    currentScreen = this.correctScreen;
                } else {
                    currentScreen = this.incorrectScreen;
                }
                this.trialNum++;

                // handle completed conditions
                if (currentCondition.isCompleted()) {
                    appendSummaryRow();
                    currentCondNum++;
                    if (currentCondNum >= conditionsQueue.size()) {
                        expCtrl.setExpEnd();
                        currentScreen = new ExperimentEndScreen();
                    } else {
                        currentCondition = (BasicJNDCondition) (conditionsQueue.get(currentCondNum));
                    }
                }
                screenImage = currentScreen.getImage();
                this.update(this.getGraphics());

                // creates the next 2Corr image while waiting for subject to press space bar (emily 06/2010)
                try {
                    imgThread = new TwoCorrImageThread();
                    imgThread.start();
                    imgThread.join();
                } catch (InterruptedException ex) {
                    System.out.println("Interupted Exception");
                }
                break;
            case ExperimentControl.EXP_END:
                doExperimentEnd();
                screenImage = currentScreen.getImage();
                break;
            case ExperimentControl.EXP_DISPLAY:
                long t = System.currentTimeMillis(); //tag
                if (trialNum <= 1) {
                    currentScreen = currentCondition.getNextScreen();
                    screenImage = currentScreen.getImage();
                } else {
                    currentScreen = imgThread.screen;
                    screenImage = imgThread.image;
                }
                Globals.GLOBAL_TIMER.tic();
                this.update(this.getGraphics());
                System.out.println(System.currentTimeMillis() - t); //tag
                break;
        }
    }

    /**
     * Appends XML code (that represents a screen) to a file.
     * <p>
     * Appends the current trial number and initials to the filename.
     *
     * @param xmlString
     */
    private void writeScreenToFile(String xmlString) {
        String prefix = "<trial>\n";
        prefix = prefix + "\t<trial_info>\n";
        prefix = prefix + "\t\t<trial_num>" + trialNum + "</trial_num>" + "\n";
        prefix = prefix + "\t\t<r_base>" + myConverge.getTrialParam() + "</r_base>" + "\n";
        prefix = prefix + "\t\t<r_compare>" + myConverge.getTrialCompare() + "</r_compare>" + "\n";
        prefix = prefix + "\t\t<is_correct>" + isCorrect + "</is_correct>\n";
        prefix = prefix + "\t\t<is_descending>" + ((BasicJNDCondition) currentCondition).getIsDescending() + "</is_descending>\n";
        prefix = prefix + "\t\t<base_on_left>" + ((BasicJNDCondition) currentCondition).getIsBaseOnLeft() + "</base_on_left>\n";
        prefix = prefix + "\t\t<scaling_val>" + ((BasicJNDCondition) currentCondition).getScalingVal() + "</scaling_val>\n";
        prefix = prefix + "\t\t<dot_size>" + ((BasicJNDCondition) currentCondition).getDotSize() + "</dot_size>\n";
        prefix = prefix + "\t\t<num_points>" + ((BasicJNDCondition) currentCondition).getNumPoints() + "</num_points>\n";
        prefix = prefix + "\t\t<dot_style>" + ((BasicJNDCondition) currentCondition).getDotStyle() + "</dot_style>\n";
        prefix = prefix + "\t\t<dot_hue>" + ((BasicJNDCondition) currentCondition).getDotHue() + "</dot_hue>\n";
        prefix = prefix + "\t</trial_info>\n";


        xmlString = prefix + xmlString + "</trial>\n";

        String distFile = FOLDER_LOGS + this.XMLFilename;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(distFile, true));
            out.write(xmlString);
            out.newLine();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a row of data to the log file.
     * 
     * @param trial_time_in_ms
     * @param correctKey
     * @param currentKey
     */
    private void appendLogfileRow(long trial_time_in_ms, int correctKey, int currentKey) {

        // very very dumb way to implement log file
        // LogfileRow(int trialNum, double trialTime, double base, double compare, double difference, boolean isCorrect, boolean isAscending, String side)
        // TODO: make the condition themselves print to the log file
        myConverge = currentCondition.getConvergence();
        boolean isDescending = ((BasicJNDCondition) currentCondition).getIsDescending();
        boolean isBaseOnLeft = ((BasicJNDCondition) currentCondition).getIsBaseOnLeft();
        double scalingVal = ((BasicJNDCondition) currentCondition).getScalingVal();
        int numPoint = ((BasicJNDCondition) currentCondition).getNumPoints();
        double dotSize = ((BasicJNDCondition) currentCondition).getDotSize();
        int dotStyle = ((BasicJNDCondition) currentCondition).getDotStyle();
        int dotHue = ((BasicJNDCondition) currentCondition).getDotHue();

        LogfileRow lr = new LogfileRow(trialNum, trial_time_in_ms, myConverge.getTrialParam(), myConverge.getTrialCompare(), Math.abs(myConverge.getTrialCompare() - myConverge.getTrialParam()), correctKey == currentKey, !isDescending, isBaseOnLeft ? LogfileRow.RIGHT : LogfileRow.LEFT, scalingVal, dotSize, numPoint, dotStyle, dotHue);
        System.out.print(lr.toString() + "\n");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FOLDER_LOGS + dataFilename, true));
            out.write(lr.toString() + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Appends a row of data to the summary file to represent each completed level.
     * 
     */
    private void appendSummaryRow() {
        myConverge = currentCondition.getConvergence();
        boolean isDescending = ((BasicJNDCondition) currentCondition).getIsDescending();
        double scalingVal = ((BasicJNDCondition) currentCondition).getScalingVal();
        double dotSize = ((BasicJNDCondition) currentCondition).getDotSize();
        int numPoints = ((BasicJNDCondition) currentCondition).getNumPoints();
        int dotStyle = ((BasicJNDCondition) currentCondition).getDotStyle();
        int dotHue = ((BasicJNDCondition) currentCondition).getDotHue();

        String summary = myConverge.getTrialCompare() + "    " + isDescending + "	" + myConverge.getWindowAverage() + "	" + myConverge.getTrialsToConverge() + "    " + scalingVal + "  " + dotSize + " " + numPoints + " " + dotStyle + " " + dotHue + "\n";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FOLDER_LOGS + summaryFilename, true));
            out.write(summary);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void doExperimentEnd() {
    }

    public static void main(String[] args) {
        BasicJNDExperiment myExperiment = new BasicJNDExperiment();

        System.out.println(myExperiment.getConfString());
        myExperiment.saveConfiguration();
        myExperiment.loadConfiguration();
        System.out.println("Configuration loaded");
        myExperiment.setupConfFrame();
        myExperiment.showConfigureFrame(true);
        System.out.println(myExperiment.getConfString());
        myExperiment.run();
        //System.exit(0);
    }
}
