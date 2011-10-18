package configuration;

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
          return  val = InferenceUtil.prop(this, memberName);
        } catch (Exception ex) {
            x1 = ex;
        }
        return null;
    }
    
    /**
     * Some versions of java complain when using implicit casting, using prop in this fashion makes use of class Cast options to get what one would like.
     * @param <E>
     * @param string
     * @param aClass
     * @return 
     */
     public <E> E prop(String string, Class<E> aClass) {
        if(aClass == Boolean.class)
            return aClass.cast(Boolean.parseBoolean(String.class.cast(prop(string))));
        return aClass.cast(prop(string));
    }
    
    
    /** Additional properties **/
    
    private double highCorr = DEFAULT_DOUBLE;
    private double lowCorr = DEFAULT_DOUBLE;
    private double stepLevel =  DEFAULT_DOUBLE; //Level that user will cause correlation to jump up/down
    
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