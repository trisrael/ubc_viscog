package configuration;

import common.condition.DotHueType;
import common.condition.DotStyleType;

/**
 *Condition for a single round
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class RoundCondition extends TaskDesign{
    public TaskDesign parent;
   

    public TaskDesign getParent() {
        return parent;
    }

    public RoundCondition(){
        numTrials = -1;
    }

    /*
     * 
     * Parent is normally the base design conditions for a given experiment
     */
  

    public void setParent(TaskDesign parent) {
        this.parent = parent;
    }
    
    
    @Override
    public int getNumTrials(){
        if(numTrials == -1) //default val for roundCondition, normally this will be set at higher level
            return super.getNumTrials();
        
        return numTrials;
    }
    
}
