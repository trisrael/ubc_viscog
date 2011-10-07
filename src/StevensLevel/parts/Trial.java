package StevensLevel.parts;

import StevensLevel.EventBusHelper;
import StevensLevel.State;
import StevensLevel.CorrelationEnsurer;
import StevensLevel.events.StevensLevelInteraction;

import StevensLevel.listeners.ScreenUpdateListener;
import StevensLevel.listeners.StevensLevelInteractionListener;
import StevensLevel.listeners.StevensLevelViewListener;
import StevensLevel.screens.TaskScreen.Graphs;
import StevensLevel.screens.TaskScreen.StevensLevelUpdateViewEvent;
import java.util.Arrays;
import java.util.List;
import static StevensLevel.EventBusHelper.*;
import screens.Screen;

/**
 *A single task for a participant in which they must choose between lowering or raising a single distribution until they have deemed that it
 * has a correlation that is between that of two other distributions.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Trial extends ExperimentModel implements StevensLevelInteractionListener {

    //Member variables
    private final double highCorr;
    private final double lowCorr;
    private double adjustedCorr;
    private int numPoints;

    public double getStepSize() {
        return stepSize;
    }

    private void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }
    private double stepSize;

    public int getNumPoints() {
        return numPoints;
    }

    private void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    /**
     * Builds a Trials consisting of the correlation for several graphs (High, User defined and Low) though this is
     * just naming, it is entirely possible for user to place highCorr at lower level than lowCorr. NOTE: Refactoring naming to use left/right 
     * might be a better model.
     * 
     * By default the user defined graph will take the value of highCorr. If a specific value is wished for adjustedCorr (user defined) graph than 
     * the other constructor method should be used.
     * @param highCorr
     * @param lowCorr
     * @param numpts 
     */
    public Trial(double highCorr, double lowCorr, int numpts, double stepsize) {
        this.highCorr = highCorr;
        this.lowCorr = lowCorr;
        this.adjustedCorr = highCorr;
        setNumPoints(numpts);
        setStepSize(stepsize);
        listen(this, StevensLevelInteractionListener.class);
    }

    /**
     * Trials at the lowest form are what react to forms of StevensLevel user interaction. Correlation Up and Correlation down
     * @param highCorr
     * @param lowCorr
     * @param startCorr
     * @param numpts
     * @param stepsize 
     */
    public void step(StevensLevelInteraction in) {
        if (isRunning()) {


            double toAdd = 0;
            switch (in) {

                case CorrelationUp:
                    toAdd += getStepSize();
                    break;
                case CorrelationDown:
                    toAdd -= getStepSize();
                    break;
            }

            double prevCorr = getAdjustedCorr();
            setAdjustedCorr(CorrelationEnsurer.ensureCorr(prevCorr + toAdd));

            if (prevCorr != getAdjustedCorr()) { //Only send an update to the screen if value has changed to avoid user from getting confused
                
                pb(this, StevensLevelViewListener.class).update(buildAdjustedPayload());
                pb(this, ScreenUpdateListener.class).screenUpdated();
            }
        }
    }

    public Trial(double highCorr, double lowCorr, double startCorr, int numpts, double stepsize) {
        this(highCorr, lowCorr, numpts, stepsize);
        this.adjustedCorr = startCorr;

    }

    public void run() {
        setState(State.IN_PROGRESS);

        List<StevensLevelUpdateViewEvent> li = Arrays.asList(
                this.buildPayload(lowCorr, Graphs.LOW),
                buildAdjustedPayload(),
                this.buildPayload(highCorr, Graphs.HIGH));


        //Publish updates to be picked up by view for graphs and their points etc..
        for (StevensLevelUpdateViewEvent payload : li) {
            pb(this, StevensLevelViewListener.class).update(payload);
        }
        pb(this, ScreenUpdateListener.class).screenUpdated();

    }

    private StevensLevelUpdateViewEvent buildAdjustedPayload() {
        return this.buildPayload(adjustedCorr, Graphs.USER_DEFINED);
    }

    private StevensLevelUpdateViewEvent buildPayload(double corr, Graphs graph) {
        return new StevensLevelUpdateViewEvent(corr, getNumPoints(), graph);
    }

    public double getAdjustedCorr() {
        return adjustedCorr;
    }

    private void setAdjustedCorr(double adjustedCorr) {
        this.adjustedCorr = adjustedCorr;
    }

    //Events
    @Override
    public void correlationStepUp() {
        step(StevensLevelInteraction.CorrelationUp);
    }

    @Override
    public void correlationStepDown() {
        step(StevensLevelInteraction.CorrelationDown);
    }

    @Override
    public void stop() {
        super.stop();
        stoplistening(this, StevensLevelInteractionListener.class);
    }

    Boolean isCorrect() {
        return isFuzzyEqual(getAdjustedCorr(), (highCorr - lowCorr)/ 2);
    }

    /*
     * Checks whether two doubles are roughly equal using epsilon
     */
    private Boolean isFuzzyEqual(double val, double d) {
        return Math.abs(val - d) <= 0.00001;
    }
}
