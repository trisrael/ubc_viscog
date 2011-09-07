package testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import log.LogfileRow;

import convergence.Convergence;
import convergence.ModifiedBasicPEST;
import experiment.ExperimentControl;

import screens.Screen;
import screens.BeginScreen;
import screens.TestCorrectScreen;
import screens.TestIncorrectScreen;
import screens.TestLeftScreen;
import screens.TestOneCorrScreen;
import screens.TestRightScreen;
import screens.TestTwoCorrScreen;
import util.Globals;
import util.Util;

// TODO: clean up code
// TODO: add comments from code adapted from Flash version
public class TestScreens extends JFrame implements KeyListener {

    // Key mappings
    final int KEY_L = KeyEvent.VK_Z;
    final int KEY_R = KeyEvent.VK_M;
    final int KEY_SPACE = KeyEvent.VK_SPACE;
    // Experiment Parameters
    final double PEST_BASE = 0.7;
    final double PEST_DIFF = 0.6;
    final double PEST_STEP = 0.01;
    final int NUM_POINTS_LEFT = 500;
    final int NUM_POINTS_RIGHT = 500;
    final double ERR_LEFT = 0.0001;
    final double ERR_RIGHT = 0.0001;
    final double SCALE = 0.9;
    final double DOT_SIZE = 1;
    final int DOT_STYLE = 1;
    final int POINT_SIZE = 1;
    // avoid serialisation warnings
    private static final long serialVersionUID = 1L;
    public static final String LOG_FOLDER = ".\\logs\\";
    private Random generator = new Random();
    private boolean isBaseOnLeft;
    private boolean isDescending = false;
    private int trialNum = 1;
    Image screenImage, screenImage2;
    // Experiment state machine
    ExperimentControl expCtrl = new ExperimentControl();
    // Screens
    Screen beginScreen = new BeginScreen();
    Screen correctScreen = new TestCorrectScreen();
    Screen incorrectScreen = new TestIncorrectScreen();
    Screen currentStimuliScreen = new TestRightScreen();
    Screen currentScreen;
    // Convergence
    ModifiedBasicPEST myConverge;
    // Data output file name
    private String dataFilename;
    int w, h;  // Display height and width

    // Constructor
    TestScreens() {

        // Filename to output data to
        dataFilename = Util.getNow("yyyy-MM-dd_HHmmss");
        dataFilename = dataFilename + ".txt";

        System.out.println(LogfileRow.getTitle());
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FOLDER + dataFilename, false));
            out.write(LogfileRow.getTitle() + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // ModifiedBasicPEST(double base, double difference, boolean isConvergeFromAbove, double stepSize)
        myConverge = new ModifiedBasicPEST(PEST_BASE, PEST_DIFF, isDescending, PEST_STEP);

        currentScreen = beginScreen;
        screenImage = currentScreen.getImage();

        setupListeners();
        setupFullscreenWindow();

        this.update(this.getGraphics());

    }

    private void setupListeners() {
        // Exit program on window close
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Exit program on mouse click
        addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        addKeyListener(this);

    }

    private void setupFullscreenWindow() {
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

        if (Globals.isDebug) {
            System.out.println("Display: " + String.valueOf(w) + "x" + String.valueOf(h));
        }
    }

    public void paint(Graphics g) {
        if (screenImage != null) {
            g.drawImage(screenImage,
                    w / 2 - screenImage.getWidth(this) / 2,
                    h / 2 - screenImage.getHeight(this) / 2,
                    this);
        }
    }

    // From KeyListener
    public void keyPressed(KeyEvent arg0) {

        int correctKey = currentScreen.getCorrectKey();
        int currentKey = arg0.getKeyCode();

        if (Globals.isDebug) {
            System.out.println("Current: " + currentKey);
            System.out.println("Correct: " + correctKey);
        }

        expCtrl.processInput(currentKey, currentScreen);

        if (Globals.isDebug) {
            System.out.println(myConverge.toString());
            System.out.println(myConverge.getTrialParam());
        }

        switch (expCtrl.getCurrentState()) {
            case ExperimentControl.EXP_BEGIN:
                currentScreen = this.beginScreen;
                break;
            case ExperimentControl.EXP_FEEDBACK:
                // dumb way to implement log file
                // LogfileRow(int trialNum, double trialTime, double base, double compare, double difference, boolean isCorrect, boolean isAscending, String side)
                long trial_time_in_ms = Globals.GLOBAL_TIMER.toc();

                LogfileRow lr = new LogfileRow(trialNum, trial_time_in_ms, myConverge.getBase(), myConverge.getTrialParam(), myConverge.getDifference(), correctKey == currentKey, !isDescending, isBaseOnLeft ? LogfileRow.RIGHT : LogfileRow.LEFT, SCALE, DOT_SIZE, NUM_POINTS_LEFT, DOT_STYLE, 1);
                System.out.print(lr.toString() + "\n");
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FOLDER + dataFilename, true));
                    out.write(lr.toString() + "\n");
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                if (correctKey == currentKey) {
                    currentScreen = this.correctScreen;
                    myConverge.setTrialResponse(true);
                } else {
                    currentScreen = this.incorrectScreen;
                    myConverge.setTrialResponse(false);
                }
                this.trialNum++;
                if (myConverge.isConverged()) {
                    expCtrl.setExpEnd();
                }
                break;
            case ExperimentControl.EXP_END:
                System.exit(0);
                break;
            case ExperimentControl.EXP_DISPLAY:

                isBaseOnLeft = generator.nextBoolean();
                if (isBaseOnLeft) {
                    // base on left
                    currentScreen = new TestTwoCorrScreen(myConverge.getBase(), myConverge.getTrialParam(), NUM_POINTS_LEFT, ERR_LEFT, SCALE, DOT_SIZE, DOT_STYLE, 1);
                    currentScreen.setCorrectKey(KEY_R);
                } else {
                    // base on right
                    currentScreen = new TestTwoCorrScreen(myConverge.getTrialParam(), myConverge.getBase(), NUM_POINTS_RIGHT, ERR_RIGHT, SCALE, DOT_SIZE, DOT_STYLE, 1);
                    currentScreen.setCorrectKey(KEY_L);
                }
                Globals.GLOBAL_TIMER.tic();
                break;
        }

        screenImage = currentScreen.getImage();
        this.update(this.getGraphics());

    }

    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    // End KeyListener
    /**
     * @param args
     */
    public static void main(String[] args) {
        new TestScreens();
    }
}
