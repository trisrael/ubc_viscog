package StevensLaw;

import java.util.ArrayList;
import java.util.List;

/**
 * Round explains a number of Tasks where each contains the same level of high and low correlation, simply the 'output' is different as in the randomization pattern
 * of the point on the distributions in question.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class TaskRound extends HasState{

    private final double lowCorr;
    private final double highCorr;
    private List<Task> tasks;

    public TaskRound(double highCorr, double lowCorr) {
        this.highCorr = highCorr;
        this.lowCorr = lowCorr;
    }

    public List<Task> getTasks() {

        if (tasks == null) {
            tasks = new ArrayList<Task>();
        }
        
        return tasks;
    }
}
