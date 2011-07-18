package StevensLaw;

/**
 * Round explains a number of Tasks where each contains the same level of high and low correlation, simply the 'output' is different as in the randomization pattern
 * of the point on the distributions in question.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class TaskRound {
    private int numTasks;
    private final double lowCorr;
    private final double highCorr;
    
    public TaskRound(double highCorr, double lowCorr, int numTasks){
        this.numTasks = numTasks;
        this.highCorr = highCorr;
        this.lowCorr = lowCorr;
    }
       
}
