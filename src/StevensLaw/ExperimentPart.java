package StevensLaw;

import StevensLaw.parts.ScreenInteraction;
import com.sun.tools.hat.internal.model.ReachableExcludes;
import interaction.BasicInteraction;
import interaction.InteractionReactorReflection;
import interaction.PartInteraction;
import java.lang.reflect.TypeVariable;
import screens.AbstractScreen;
import screens.AbstractStrictScreen;
import interaction.ReactTo;

/**
 * Represents a part/fragment of an Experiment, an Experiment (like a presentation) is broken up into different groups of 'parts'. Each runnable as long as in correct state.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 4, 2011
 */
public abstract class ExperimentPart<ScreenClass extends AbstractScreen> extends WithStateWithInteractionReactorImpl implements Runnable, interaction.InteractionReactor  {
 
    public abstract Class<ScreenClass> getScreenClass();
    
    public void run(){
        if(getState() == State.COMPLETE){ throw new RuntimeException("Attempted to run ExperimentPart in complete stage");}  
        setState(State.IN_PROGRESS);
        
        sendReaction(ScreenInteraction.Update, getScreenClass());
    }

    void stop() {
        setState(State.COMPLETE);
    }
    
   
 
}
