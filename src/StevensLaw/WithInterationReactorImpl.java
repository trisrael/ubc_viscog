/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.ExperimentInteraction;
import interaction.InteractionReactor;
import static interaction.InteractionReactorReflection.*;
/**
 * Base class for those who have a single relation with an InteractionReactor
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 12, 2011
 */
public class WithInterationReactorImpl implements HasInteractionReactor{
    InteractionReactor interactionReactor;

    public InteractionReactor getInteractionReactor() {
        return interactionReactor;
    }

    public void setInteractionReactor(InteractionReactor interactionReactor) {
        this.interactionReactor = interactionReactor;
    }
    
    /**
     * If object has an available InteractionReactor set, send an event upwards
     */
    public void sendReaction(ExperimentInteraction e){
        if(hasInteractionReactor()){
            reactAll(interactionReactor, e);
        }
    }
    
    /**
     * If object has an available InteractionReactor set, send an event upwards
     */
    public void sendReaction(ExperimentInteraction e, Object pay){
        if(hasInteractionReactor()){
            reactAll(interactionReactor, e, pay);
        }
    }

    @Override
    public boolean hasInteractionReactor() {
        return interactionReactor != null;
    }
}
