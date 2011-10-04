package StevensLevel;

import StevensLevel.events.ScreenChange;
import StevensLevel.listeners.ScreenChangeListener;
import StevensLevel.listeners.ScreenNotificationListener;
import interaction.ExperimentInteraction;
import interaction.InteractionReactor;
import java.lang.Class;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static StevensLevel.EventBusHelper.*;

import screens.AbstractStrictScreen;
import screens.Screen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ViewControl extends WithInterationReactorImpl implements InteractionReactor, ScreenChangeListener {

    protected View view;
   

    public static enum UIInteraction implements ExperimentInteraction {
        Screen, Close
    }

    public View getView() {
        return view;
    }
    private Map<Class, AbstractStrictScreen> screens = new HashMap<Class, AbstractStrictScreen>();
    private AbstractStrictScreen currScreen = null; //The screen currently showing to the testee/participant

    public ViewControl() {
        this.view = new View(); //Single Jframe where viewable portions of experiment will be rendered     
        listen(this, ScreenChangeListener.class);
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
    
    
    @Override
    public void changeScreen(ScreenChange chg) {
        changeScreen(chg.getScreenClass());
    }
    
    public void changeScreen(Class<? extends Screen> clazz){
        setNewScreen(clazz);
       pb(this, ScreenNotificationListener.class).screenIsReady(clazz);
    }
    

    /**
     * Takes a payload attempts to cast to a class (AbstractStrictScreen class), creates a new instance if current screen is already of type supplied, and then updates the screen
     *Otherwise does nothing.
     * @param payload 
     */
    private void setNewScreen(Object payload) {
        Class<? extends AbstractStrictScreen> scrClazz = (Class<? extends AbstractStrictScreen>) payload;
                try {
                    
                    if (currScreen == null || !(scrClazz == currScreen.getClass())){
                         currScreen = scrClazz.newInstance();
                         view.setScreen(currScreen); 
                        view.update();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ViewControl.class.getName()).log(Level.SEVERE, null, ex);
                }
             
                
    }
    
    public void setup(){
          getView().addInteractionReactor(this);
    }
    
}
