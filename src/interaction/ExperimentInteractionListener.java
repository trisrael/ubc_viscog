/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interaction;

import java.util.EventListener;

/**
 *
 * @author tristangoffman
 */
public interface ExperimentInteractionListener extends EventListener {
    
    public void continueOn();
    
    /**
     * User wishes to finish
     */
    public void exitExperiment();
    
    /**
     * Experiment Tast/Part is 'completed' as the user has deemed.
     */
    public void completeTask();
    
    
    /** Interaction which is invalid or not associated with anything else has been pressed **/
    public void invalidInteraction();
}
