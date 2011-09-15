/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import org.mockito.Mockito;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.event.KeyEvent;
import StevensLaw.parts.EndingPart;
import StevensLaw.parts.BeginningPart;

import interaction.ExperimentInteractionListener;
import java.util.List;

import screens.AbstractStrictScreen;
import StevensLaw.parts.Round;
import configuration.TaskDesign;
import interaction.BasicInteraction;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import static org.junit.Assert.*;



import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 14, 2011
 */
public class InteractionReactorTest extends TestBase{
    @Test
    public void invokesMethod(){
        ExperimentControl xp = spy(new ExperimentControl());
        View view = xp.getViewControl().getView();
        xp.getViewControl().setInteractionReactor(xp);
      
         view.keyReleased(buildKeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_SPACE));
         verify(xp).basicInteractionOccurred(Mockito.any(BasicInteraction.class), Mockito.any(BasicInteraction.class));
         
    } 
    
     //HELPERS
    /**
     * @param id, type of KeyEvent
     * @param keyCode, key press/typed/released (Type keyCode should away be VK_UNDEFINED)
     * @return 
     */
    private static KeyEvent buildKeyEvent(int id, int keyCode) {
        return buildKeyEvent(id, keyCode, new JFrame());

    }

    private static KeyEvent buildKeyEvent(int id, int keyCode, Component src) {
        return new KeyEvent(src, id, 0, 0, keyCode);
    }
}
