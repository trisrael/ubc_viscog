package StevensLaw;

import java.lang.reflect.TypeVariable;
import screens.AbstractScreen;
import screens.AbstractStrictScreen;

/**
 * Represents a part/fragment of an Experiment, an Experiment (like a presentation) is broken up into different groups of 'parts'. Each runnable as long as in correct state.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 4, 2011
 */
public abstract class ExperimentPart<ScreenClass extends AbstractScreen> extends WithStateImpl implements Runnable{
    
    private UIEventListener uiListener = null;
    
    public abstract Class<ScreenClass> getScreenClass();
    
    public void run(){
        if(getState() == State.COMPLETE){ throw new RuntimeException("Attempted to run ExperimentPart in complete stage");}  
        setState(State.IN_PROGRESS);
    }
}
