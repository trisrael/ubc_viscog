package StevensLaw.parts;

import StevensLaw.ExperimentPart;
import StevensLaw.State;
import StevensLaw.StevensLevelTask;
import StevensLaw.Trial;
import StevensLaw.screens.TaskScreen;
import java.util.ArrayList;
import java.util.List;
import screens.AbstractStrictScreen;

/**
 * Round explains a number of Trials where each contains the same level of high and low correlation, simply the 'output' is different as in the randomization pattern
 * of the point on the distributions in question.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Round<TaskScreen> extends ExperimentPart {

    private final double lowCorr;
    private final double highCorr;
    private Integer numTrials = null;
    private List<Trial> tasks;
    private Trial current; //Current task in progress (or null if out

    public Round(double highCorr, double lowCorr) {
        this.highCorr = highCorr;
        this.lowCorr = lowCorr;
    }

    public List<Trial> getTrials() {

        if (tasks == null) {
            tasks = new ArrayList<Trial>();
        }
        
        return tasks;
    }

    @Override
    public void run() {
        super.run();
        this.current = getTrials().remove(0);
    }

    @Override
    public Class getScreenClass() {
        return StevensLaw.screens.TaskScreen.class;
    }

    void setup() {
        
        if(getNumTrials() == null){throw new RuntimeException("Missing Trial Numbers");}
    }

    public Integer getNumTrials() {
        return numTrials;
    }
    
    public void setNumTrials(Integer num){
        numTrials = num;
    }
}
