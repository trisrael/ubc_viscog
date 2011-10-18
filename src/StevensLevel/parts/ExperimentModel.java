package StevensLevel.parts;

import StevensLevel.listeners.PartInteractionListener;
import StevensLevel.State;
import StevensLevel.WithStateImpl;
import interaction.ExperimentInteractionListener;
import static StevensLevel.EventBusHelper.*;

/**
 * Object which defines an ExperimentModel, superclass to ExperimentPart but usable by 'headless' Experiment models those which are not visible such as
 * a Trial which is a component found within the ExperimentPart Round
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 21, 2011
 */
public class ExperimentModel extends WithStateImpl implements Runnable, interaction.InteractionReactor, ExperimentInteractionListener  {
    
    public void run(){
        if(getState() == State.COMPLETE){ throw new RuntimeException("Attempted to run ExperimentPart in complete stage");}  
        setState(State.IN_PROGRESS);
        
        listen(this, ExperimentInteractionListener.class);
        
        
    }

    public void stop() {
        setState(State.COMPLETE);
        stoplistening(this, ExperimentInteractionListener.class);
    }
    
    /**
     * Default continueOn action simply sends out another event explaining of a larger continueOn.
     */
    @Override
    public void continueOn(){
        completeTask();
    }
    
    
    
    /**
     * User wishes to finish
     */
    @Override
    public void exitExperiment(){
        pb(this, PartInteractionListener.class).exit();
    }
    
    /**
     * Experiment Tast/Part is 'completed' as the user has deemed. So tell whoever might be listening
     * that this 'Part' is complete.
     */
    @Override
    public void completeTask(){
        pb(this, PartInteractionListener.class).partComplete();
    }
    
    
    /** Interaction which is invalid or not associated with anything else has been pressed **/
    @Override
    public void invalidInteraction(){
        //does nothing
    }
    
}
