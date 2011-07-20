package StevensLaw;


/**
 *
 * @author tristangoffman
 */
public class Experiment{
    
    /** Members **/
    private final ExperimentControl eCon;
    private final ViewControl vCon;
    
    public Experiment(){
        this.vCon = new ViewControl();
        this.eCon = new ExperimentControl();
    }
    

}
