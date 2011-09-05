/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.ExperimentInteractionEvent;
import interaction.ExperimentInteractionListener;
import interaction.ExperimentInteractionProducer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import screens.AbstractStrictScreen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ViewControl extends ExperimentInteractionProducer implements ExperimentInteractionListener {

    private final View view;
    private Map<Class, AbstractStrictScreen> screens = new HashMap<Class, AbstractStrictScreen>();
    private AbstractStrictScreen currScreen = null; //The screen currently showing to the testee/participant

    public ViewControl() {
        this.view = new View(); //Single Jframe where viewable portions of experiment will be rendered        
    }

    @Override
    public void interactionOccured(ExperimentInteractionEvent ev) {
        //Experiment Interactions are all enum types... if it is not then an Exception will be thrown
    }

    boolean addScreen(AbstractStrictScreen scr) {
        //NOTE: Only allowing for one screen for each class to be added (singleon screens as you would) -- forcing update of pre-existing screen instead
        //of addition of new screen
        if (!screens.containsValue(scr)) {
            screens.put(scr.getClass(), scr);
        }

        return false;
    }

    Collection<AbstractStrictScreen> getScreens() {
        return screens.values();
    }
    
    void setScreen(Class<? extends AbstractStrictScreen> aClass) {
        AbstractStrictScreen scr = screens.get(aClass);
        if(scr == null){ throw new RuntimeException("Tried to switch to non-existing screen in ViewControl"); }                    
        this.view.setScreen(scr);
    }
    
    boolean isVisible(){
        return getScreen() != null;
    }
    
    protected AbstractStrictScreen getScreen(){
        return currScreen;
    }
}

