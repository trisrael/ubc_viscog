package StevensLevel.parts;

import StevensLevel.listeners.ScreenChangeListener;
import StevensLevel.State;
import StevensLevel.WithStateImpl;
import StevensLevel.events.ScreenChange;
import StevensLevel.listeners.PartInteractionListener;
import interaction.ExperimentInteractionListener;
import screens.AbstractScreen;
import static StevensLevel.EventBusHelper.*;

/**
 * Represents a part/fragment of an Experiment, an Experiment (like a presentation) is broken up into different groups of 'parts'. Each runnable as long as in correct state.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 4, 2011
 */
public abstract class ExperimentPart<ScreenClass extends AbstractScreen> extends ExperimentModel{
 
    public abstract Class<ScreenClass> getScreenClass();
    
    /**
     * When run is called notify listeners that a new screen needs to be displayed
     */
    @Override
    public void run(){
       super.run();
       pb(this, ScreenChangeListener.class).changeScreen(new ScreenChange(getScreenClass()));
       
     }

    /**
     * Items to setup before an ExperimentPart is run. setup gets called by ExperimentControl
     * before run
     */
    public void setup() {
       
    }
   
 
}
