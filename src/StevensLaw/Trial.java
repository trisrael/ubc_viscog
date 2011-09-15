package StevensLaw;

import StevensLaw.parts.ScreenInteraction;
import StevensLaw.Validator.InvalidCorrelation;
import StevensLaw.screens.TaskScreen.Graphs;
import StevensLaw.screens.TaskScreen.StevensLawPayload;
import interaction.PartInteraction;
import interaction.ReactTo;
import java.util.Arrays;
import java.util.List;

/**
 *A single task for a participant in which they must choose between lowering or raising a single distribution until they have deemed that it
 * has a correlation that is between that of two other distributions.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Trial extends WithStateWithInteractionReactorImpl {

    //Member variables
    private final double highCorr;
    private final double lowCorr;
    private double adjustedCorr;
    private int numPoints;

    public double getStepSize() {
        return stepSize;
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }
    private double stepSize;

    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
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
    }

    /**
     * Trials at the lowest form are what react to forms of StevensLevel user interaction. Correlation Up and Correlation down
     * @param highCorr
     * @param lowCorr
     * @param startCorr
     * @param numpts
     * @param stepsize 
     */
    @ReactTo(StevensLevelInteraction.class)
    public void step(StevensLevelInteraction in, Object payload) {
        double toAdd = 0;
        switch (in) {

            case CorrelationUp:
                toAdd += getStepSize();
                break;
            case CorrelationDown:
                toAdd -= getStepSize();
                break;
        }
        
        setAdjustedCorr(getAdjustedCorr() + toAdd);
        sendReaction(ScreenInteraction.Update, buildAdjustedPayload());
    }

    public Trial(double highCorr, double lowCorr, double startCorr, int numpts, double stepsize) {
        this(highCorr, lowCorr, numpts, stepsize);
        this.adjustedCorr = startCorr;

    }

    /**
     * Set adjusted correlation to new value, when an invalid correlation is supplied throw an exception (invalid correlation should never be supplied)
     * @return 
     */
    public double adjustCorrelation(double val) throws InvalidCorrelation {
        Validator.validateCorr(val);

        this.adjustedCorr = val;
        return this.adjustedCorr;
    }

    public void run() {
        setState(State.IN_PROGRESS);

        List<StevensLawPayload> li = Arrays.asList(
                this.buildPayload(lowCorr, Graphs.LOW),
                buildAdjustedPayload(),
                this.buildPayload(highCorr, Graphs.HIGH));
        for (StevensLawPayload stevensLawPayload : li) {
            sendReaction(ScreenInteraction.Update, stevensLawPayload);
        }

    }

    private StevensLawPayload buildAdjustedPayload() {
        return this.buildPayload(adjustedCorr, Graphs.USER_DEFINED);
    }

    private StevensLawPayload buildPayload(double corr, Graphs graph) {
        return new StevensLawPayload(corr, getNumPoints(), graph);
    }

    public double getAdjustedCorr() {
        return adjustedCorr;
    }

    public void setAdjustedCorr(double adjustedCorr) {
        this.adjustedCorr = adjustedCorr;
    }
}
