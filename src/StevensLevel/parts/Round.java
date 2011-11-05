package StevensLevel.parts;

import StevensLevel.State;
import StevensLevel.listeners.ScreenNotificationListener;
import configuration.TaskDesign;
import java.util.ArrayList;
import java.util.List;
import static StevensLevel.EventBusHelper.*;
import StevensLevel.UserKeyInteractionListener;
import StevensLevel.events.ScreenChange;
import StevensLevel.listeners.ScreenChangeListener;
import common.condition.DotHueType;
import common.condition.DotStyleType;
import configuration.RoundDesign;
import render.GraphStyleSheet;
import screens.Screen;
import screens.TestCorrectScreen;
import screens.TestIncorrectScreen;

/**
 * Round explains a number of Trials where each contains the same level of high and low correlation, simply the 'output' is different as in the randomization pattern
 * of the point on the distributions in question.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Round extends ExperimentPart implements ScreenNotificationListener {

    private final double lowCorr;
    private final double highCorr;
    private Integer numTrials = null;
    private List<Trial> trials;
    private Trial current; //Current task in progress (or null if out
    private final TaskDesign des;
    private int trialsPerformed = 0;
    private final int numPoints; // Number of points to draw on each graph (to build each correlation graph with)
    private final double stepSize;
    private GraphStyleSheet stylesheet;

    public Round(RoundDesign des) {
        this.des = des;


        this.lowCorr = des.prop("lowCorr", Double.class);
        this.highCorr = des.prop("highCorr", Double.class);
        this.numTrials = des.prop("numTrials", Integer.class);
        this.numPoints = des.prop("numPoints", Integer.class);
        this.stepSize = des.prop("stepLevel", Double.class);
        this.stylesheet = new GraphStyleSheet(des);

    }

    public List<Trial> getTrials() {

        if (trials == null) {
            trials = new ArrayList<Trial>();
        }

        return trials;
    }

    @Override
    public void run() {
        //Listen for newScreenEvent of type TaskScreen
        listen(this, ScreenNotificationListener.class);

        super.run(); //Since this is an ExperimentPart, should cause 
        // a new screen to be put in place
    }

    /**
     * Setup the next trial as the current trial and start running it.
     */
    protected synchronized void nextTrial() {
        //log out here (after trial stuff)
        this.current = getTrial(trialsPerformed);

        runTrial();
        trialsPerformed++; //increment trials performed
    }

    @Override
    public void setup() {
        if (getNumTrials() == null) {
            throw new RuntimeException("Missing Trial Numbers");
        }

        for (int i = 0; i < getNumTrials(); i++) {
            pushTrial(new Trial(highCorr, lowCorr, numPoints, stepSize, stylesheet));
        }
    }

    public Integer getNumTrials() {
        return numTrials;
    }

    protected void setNumTrials(Integer num) {
        numTrials = num;
    }

    protected Trial getCurrentTrial() {
        return this.current;
    }

    private Trial getTrial(int ind) {
        return getTrials().get(ind);
    }

    private void pushTrial(Trial trial) {
        getTrials().add(trial);
    }

    private void runTrial() {
        this.current.run();
    }

    @Override
    public Class getScreenClass() {
        return StevensLevel.screens.TaskScreen.class;
    }

    private boolean hasNextTrial() {
        return numTrials > trialsPerformed;
    }

    @Override
    public <T extends Screen> void screenIsReady(Class<T> screenClazz) {
        if (getState() == StevensLevel.State.IN_PROGRESS && hasNextTrial()) { //Send off first updates if running
            nextTrial();
            eb().removeListener(ScreenNotificationListener.class, this);
        }

    }

    /**
     * Default continueOn action simply sends out another event explaining of a larger continueOn.
     */
    public void continueOn() {
        if (isWaiting()) { //should be waiting from a completeTask call -> will either want to move on to nextTrack or call 

            if (hasNextTrial()) { //Send off first updates if running
                eb().getPublisher(this, UserKeyInteractionListener.class).ignoreUserInteractions(); //new trial just about to start 
                run(); //start running again (will start next trial automaticall)
            } else {
                super.completeTask();
            }

        }

    }

    /**
     * Moves between trials, showing a screen in between which explains if user was correct or not in there belief 
     * of correlation (adjusted correlation within trial)
     */
    public void completeTask() {
        //TODO: Is it possible that current trial could be null?
        if (isRunning()) {
            getCurrentTrial().stop();
            setState(State.WAITING); //wait for continueOn to go off before allowing completeTasks again
            eb().getPublisher(this, UserKeyInteractionListener.class).ignoreUserInteractions(); //new screen coming in
            pb(this, ScreenChangeListener.class).changeScreen(new ScreenChange(getCurrentTrial().isCorrect() ? TestCorrectScreen.class : TestIncorrectScreen.class));

        }
    }
}