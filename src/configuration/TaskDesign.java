package configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.InferenceUtil;

/**
 * The design for a single Round/TaskRound (all these things), wanna change the numTrials for a particular Round do it, need to change what 
 * point drawing is being performed? Do it.  If props are asked for that are not directly set on task design, then the BaseDesign check BaseDesign for default..
 * BaseDesign can also be overwritten by YAML.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class TaskDesign extends Design {
    public TaskDesign(){
        setBaseDesign(new Design());
    }

    public Design getBaseDesign() {
        return baseDesign;
    }

    public void setBaseDesign(Design baseDesign) {
        this.baseDesign = baseDesign;
    }
    
    private Design baseDesign;
    
    /**
     * Get properties from TaskDesign (Notice that there is no method to set them, this is to deter later setting.  All setting of properties should have been completed
     * from transition form yaml.
     * @param memberName
     */
    public Object prop(String memberName) {
        Object val = null;
        Exception x1 = null;
        Exception x2 = null;
        try {
            val = InferenceUtil.prop(this, memberName);
        } catch (Exception ex) {
            x1 = ex;
        }

        if (val == null) {
            try {
                val = InferenceUtil.prop(getBaseDesign(), memberName);

            } catch (Exception ex) {
               x2 = ex;
            }
        }
        
        if(x1 != null && x2 != null){
            Logger.getLogger(TaskDesign.class.getName()).log(Level.WARNING, null, x1);
        }

        return val;
    }
    
    
    /** Additional properties **/
    
    private double highCorr = 1.0;
    private double lowCorr = 0.0;
    private double stepLevel = 0.03; //Level that user will cause correlation to jump up/down
    
    public double getStepLevel() {
        return stepLevel;
    }

    public void setStepLevel(double stepLevel) {
        this.stepLevel = stepLevel;
    }

    public double getHighCorr() {
        return highCorr;
    }

    public void setHighCorr(double highCorr) {
        this.highCorr = highCorr;
    }

    public double getLowCorr() {
        return lowCorr;
    }

    public void setLowCorr(double lowCorr) {
        this.lowCorr = lowCorr;
    }
    
}