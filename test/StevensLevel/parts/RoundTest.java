/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.parts;

import StevensLevel.View;
import StevensLevel.ViewControl;
import StevensLevel.events.ScreenChange;
import StevensLevel.listeners.ScreenChangeListener;
import StevensLevel.listeners.ScreenNotificationListener;
import StevensLevel.screens.TaskScreen;
import harness.ExtendableRoundTest;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;
import static StevensLevel.EventBusHelper.*;

import static org.hamcrest.Matchers.*;


/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class RoundTest {
    
    public static class AfterConstruct extends ExtendableRoundTest
    {
        @Test
        public void emptyTrialList(){
            assertEquals(obj().getTrials().size(), 0);
           
        }
        
    }
        
    public static class AfterSetup extends ExtendableRoundTest{
   
      
        
        
        @Test
        public void screenChangeNotification() throws InterruptedException{
            ViewControl vc = new ViewControl();
            vc.changeScreen(TaskScreen.class);
            
           
            
           Thread.sleep(1000);
            
           assertThat(getInstance().getCurrentTrial(), is(notNull()));

        }
               
    }
    
    public static class Trials extends AfterSetup {
        @Test
        public void switchTrials(){
            Round round = getInstance();
            round.setup();
            round.run();
            Trial nextTrial = round.getTrials().get(1);
            
            round.completeTask();
            round.spacebarPlaced();
            assertEquals(round.getCurrentTrial(), nextTrial);
        }
    }
    
}
