package StevensLaw.parts;

import StevensLaw.ExperimentPart;
import StevensLaw.State;
import StevensLaw.StevensLevelTask;
import StevensLaw.Trial;
import StevensLaw.UIEvent;
import StevensLaw.UIEventListener;
import StevensLaw.screens.TaskScreen;
import configuration.TaskDesign;
import java.util.ArrayList;
import java.util.List;
import screens.AbstractStrictScreen;

/**
 * Round explains a number of Trials where each contains the same level of high and low correlation, simply the 'output' is different as in the randomization pattern
 * of the point on the distributions in question.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Round extends ExperimentPart implements UIEventListener{

    private final double lowCorr;
    private final double highCorr;
    private Integer numTrials = null;
    private final double startCorr;
    private List<Trial> trials;
    private Trial current; //Current task in progress (or null if out
    private final TaskDesign des;
    
    
    private int trialsPerformed = 0;

    public Round(TaskDesign des) {
            this.des  = des;
            this.lowCorr = (double) des.prop("lowCorr");
            this.highCorr = (double) des.prop("highCorr");
            this.numTrials = (Integer) des.prop("numTrials");
            Object adjCorr = des.prop("startCorr");
            
            
            if(adjCorr != null)
                this.startCorr = (double) adjCorr;
            else
                this.startCorr = this.highCorr; //default to using highCorr when no specific low corr given
            
            
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
        this.current = getTrial(trialsPerformed);
        runTrial();
    }

    @Override
    public Class getScreenClass() {
        return StevensLaw.screens.TaskScreen.class;
    }

    void setup() {
        if(getNumTrials() == null){throw new RuntimeException("Missing Trial Numbers");}
        
        for(int i = 0; i < getNumTrials(); i++){
            pushTrial(new Trial(highCorr, lowCorr, startCorr));
        }
    }

    public Integer getNumTrials() {
        return numTrials;
    }
    
    protected void setNumTrials(Integer num){
        numTrials = num;
    }

    private Trial getTrial(int ind) {
        return getTrials().get(ind);
    }
    
    private void nextTrial(){
        trialsPerformed++; //increment trials performed
    }

    private void pushTrial(Trial trial) {
        trials.add(trial);
    }

    private void runTrial() {
       this.current.run();
    }

    @Override
    public void uiEventOccurred(UIEvent event, Object payload) {
        
    }
}
