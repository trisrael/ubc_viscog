/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.ExperimentInteraction;
import interaction.ExperimentInteractionProducer;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import screens.AbstractStrictScreen;
import static java.awt.event.KeyEvent.*;
import static interaction.BasicInteraction.*;
import static StevensLaw.GraphInteraction.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 20, 2011
 */
public class View extends ExperimentInteractionProducer implements KeyListener {

    public enum States{
        FULL,
        MIN,
        MAX,
            
    }
    
    private AbstractStrictScreen currScreen = null;
    private Component screenComponent = null;
    protected JFrame container = new JFrame();
    
    public View(){
        container.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent ke){}

    @Override
    public void keyPressed(KeyEvent ke){}

    @Override
    public void keyReleased(KeyEvent ke) {
        ExperimentInteraction notifier;
        switch (ke.getKeyCode()) {
            case VK_Z:
                notifier = CorrelationDown;
                break;
            case VK_M:
                notifier = CorrelationUp;
                break;
            case VK_ENTER:
                notifier = TaskCompleted;
                break;
            default:
                notifier = InvalidKeyPress;
                break;
        }
        notifyListeners(notifier);
    }

    public void setScreen(AbstractStrictScreen scr) {
        container.remove(screenComponent);
        currScreen = scr;
    }

    public AbstractStrictScreen getScreen() {
        return currScreen;
    }
}
