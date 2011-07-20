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
    /** 
     * Abstract out the underlying mechanisms that caused the interaction: we only care about the semantic
     * meaning behind the interaction performed in order to be able to perform an appropriate action. This allows
     * for the type of keypress to change, or to allow for multiple.. etc. All that is needed is for the appropriate
     * interaction to be added to UserInteractions
     **/
    public abstract void interactionOccured(ExperimentInteractionEvent ev);
}
