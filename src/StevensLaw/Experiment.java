package StevensLaw;


/**
 *
 * @author tristangoffman
 */
public class Experiment{
    
    /** Members **/
    private ExperimentControl eCon = null;
    private ViewControl vCon = null;
    
    public Experiment(){
        this.vCon = new ViewControl();
        this.eCon = new ExperimentControl();  
        vCon.addListener(eCon);
    }  
    
    public ViewControl getViewControl(){
        return vCon;
    }
    
    public ExperimentControl getExperimentControl(){
        return eCon;
    }

}
