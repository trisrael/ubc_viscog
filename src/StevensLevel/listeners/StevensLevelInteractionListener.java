/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;

import interaction.ExperimentInteractionListener;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 21, 2011
 */
public interface StevensLevelInteractionListener extends ExperimentInteractionListener{
    
    /** User has initiated an action which is expected to initiate a change in the graph **/
    
    
    public void correlationStepUp();
    
    public void correlationStepDown();
}
