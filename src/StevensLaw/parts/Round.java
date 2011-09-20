package StevensLaw.parts;

import StevensLaw.Trial;
import configuration.TaskDesign;
import interaction.BasicInteraction;
import interaction.PartInteraction;
import interaction.ReactTo;
import java.util.ArrayList;
import java.util.List;

/**
 * Round explains a number of Trials where each contains the same level of high and low correlation, simply the 'output' is different as in the randomization pattern
 * of the point on the distributions in question.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Round extends ExperimentPart {

    private final double lowCorr;
    private final double highCorr;
    private Integer numTrials = null;
    private final double startCorr;
    private List<Trial> trials;
    private Trial current; //Current task in progress (or null if out
    private final TaskDesign des;
    private int trialsPerformed = 0;
    private final int numPoints; // Number of points to draw on each graph (to build each correlation graph with)
    private final double stepSize;

    public Round(TaskDesign des) {
        this.des = des;
        this.lowCorr = (double) des.prop("lowCorr");
        this.highCorr = (double) des.prop("highCorr");
        this.numTrials = (Integer) des.prop("numTrials");
        this.numPoints = (int) des.prop("numPoints");
        this.stepSize = (double) des.prop("stepLevel");
        Object adjCorr = des.prop("startCorr");


        if (adjCorr != null) {
            this.startCorr = (double) adjCorr;
        } else {
            this.startCorr = this.highCorr; //default to using highCorr when no specific low corr given
        }

    }

    public List<Trial> getTrials() {

        if (trials == null) {
            trials = new ArrayList<Trial>();
        }

        return trials;
    }

    @Override
    public void run() {
        super.run();
        nextTrial();
    }
    
     @ReactTo(BasicInteraction.class)
     public void basicInteractionOccurred(BasicInteraction in, Object payload){
        switch(in){
            case Complete:
                
                if(hasNextTrial())
                    nextTrial();
                else
                    afterTrial();
                    sendReaction(PartInteraction.Complete);
                
            default:
                break;
        }
    }

    /**
     * Setup the next trial as the current trial and start running it.
     */
    private void nextTrial() {
        afterTrial();
        
        this.current = getTrial(trialsPerformed);
        this.current.addInteractionReactor(this);
        runTrial();
        trialsPerformed++; //increment trials performed
    }

    private void afterTrial() {
        //Log out results
        
        if(this.current != null){
            this.current.addInteractionReactor(null);
        }
    }

    void setup() {
        if (getNumTrials() == null) {
            throw new RuntimeException("Missing Trial Numbers");
        }

        for (int i = 0; i < getNumTrials(); i++) {
            pushTrial(new Trial(highCorr, lowCorr, startCorr, numPoints, stepSize));
        }
    }

    public Integer getNumTrials() {
        return numTrials;
    }

    protected void setNumTrials(Integer num) {
        numTrials = num;
    }

    private Trial getTrial(int ind) {
        return getTrials().get(ind);
    }

    private void pushTrial(Trial trial) {
        trials.add(trial);
    }

    private void runTrial() {
        this.current.run();
    }
    
     @Override
    public Class getScreenClass() {
        return StevensLaw.screens.TaskScreen.class;
    }

    private boolean hasNextTrial() {
       return numTrials > trialsPerformed;
    }
}