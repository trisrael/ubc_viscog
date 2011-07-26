/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interaction;

/**
 *
 * @author tristangoffman
 */
public interface Observable {
    /*
     * Adds 
     */
    void addListener(ExperimentInteractionListener ob);
    
    /* Notify all listeners of interaciton */
    void notifyListeners(ExperimentInteraction in);    
    void notifyListeners(ExperimentInteractionEvent in);
}
