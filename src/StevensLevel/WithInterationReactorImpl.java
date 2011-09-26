/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import interaction.ExperimentInteraction;
import interaction.InteractionReactor;
import java.util.ArrayList;
import static interaction.InteractionReactorReflection.*;
/**
 * Base class for those who have a single relation with an InteractionReactor
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 12, 2011
 */
public class WithInterationReactorImpl implements HasInteractionReactor{
    List<InteractionReactor> reactors = new ArrayList<InteractionReactor>();

    public synchronized List<InteractionReactor> getInteractionReactors() {
        return reactors;
    }

    public synchronized void addInteractionReactor(InteractionReactor interactionReactor) {
        getInteractionReactors();
        this.reactors.add(interactionReactor);
    }
    
    public synchronized void removeInteractionReactor(InteractionReactor reactor){
        getInteractionReactors().remove(reactor);
    }
    
    /**
     * If object has an available InteractionReactor set, send an event upwards
     */
    public void sendReaction(ExperimentInteraction e){
        if(hasInteractionReactor()){
            reactAll(getBlacklist(), getInteractionReactors(), e);
        }
    }
    
    /**
     * If object has an available InteractionReactor set, send an event upwards
     */
    public void sendReaction(ExperimentInteraction e, Object pay){
        if(hasInteractionReactor()){
            
            reactAll(getBlacklist(), getInteractionReactors(), e, pay);
        }
    }
    
    /**
     * Get 'blacklist' of interaction reactors for recursive reflection call, avoiding infiniti looping
     * @return 
     */
    private Set<InteractionReactor> getBlacklist(){
        HashSet<InteractionReactor> hs = new HashSet<InteractionReactor>();
        
        if(this instanceof InteractionReactor)
        { 
            hs.add((InteractionReactor) this);
        }
        return hs;
    }

    @Override
    public boolean hasInteractionReactor() {
        return getInteractionReactors() != null && !getInteractionReactors().isEmpty();
    }
}
