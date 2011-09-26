/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;


import interaction.ExperimentInteraction;
import java.util.EventListener;

/**
 *
 * @author tristangoffman
 */
public interface PartInteractionListener extends EventListener {
    
    /**
     * ACtion to perform after a ExperimentPart has notified that it is complete.
     */
    public void partComplete();

    /**
     * A call to exit has been issued. Cleanup state and shutdown everything object has in its control.
     * 
     */
    public void exit();
}
