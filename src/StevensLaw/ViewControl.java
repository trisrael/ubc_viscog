/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.BasicInteraction;
import interaction.ExperimentInteractionEvent;
import interaction.ExperimentInteractionListener;
import interaction.ExperimentInteractionProducer;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.Class;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import screens.AbstractStrictScreen;
import sun.tools.tree.ContinueStatement;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ViewControl extends ExperimentInteractionProducer implements ExperimentInteractionListener, UIEventListener {

    private final View view;
    private Map<Class, AbstractStrictScreen> screens = new HashMap<Class, AbstractStrictScreen>();
    private AbstractStrictScreen currScreen = null; //The screen currently showing to the testee/participant
   

    public ViewControl() {
        this.view = new View(); //Single Jframe where viewable portions of experiment will be rendered     
        this.view.addListener(this);
        //this.view.start();
    }

    @Override
    public void interactionOccured(ExperimentInteractionEvent ev) {
        //Experiment Interactions are all enum types... if it is not then an Exception will be thrown
        for (ExperimentInteractionListener lis : this.getListeners()) {
            lis.interactionOccured(ev);
        }
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
        if (scr == null) {
            throw new RuntimeException("Tried to switch to non-existing screen in ViewControl");
        }
        this.view.setScreen(scr);

    }

    boolean isVisible() {
        return getScreen() != null;
    }

    protected AbstractStrictScreen getScreen() {
        return currScreen;
    }

    /**
     * ViewControl deals with two types of possible uiEvents, a screen switch and all other events get passed directly to the screen to be dealt with.
     * @param event
     * @param payload 
     */
    public void uiEventOccurred(UIEvent event, Object payload) {
        switch (event) {
            case SCREEN_CHANGE:
                Class<? extends AbstractStrictScreen> scrClazz = (Class<? extends AbstractStrictScreen>) payload;
                try {
                    currScreen = scrClazz.newInstance();
                    view.setScreen(currScreen);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ViewControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ViewControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
                break;
            default:
                currScreen.uiEventOccurred(event, payload);
        }
        
        view.update();
    }
}
