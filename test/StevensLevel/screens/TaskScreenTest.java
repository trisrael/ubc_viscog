/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.screens;

import StevensLevel.listeners.ScreenUpdateListener;
import StevensLevel.listeners.StevensLevelViewListener;
import static StevensLevel.EventBusHelper.*;
import StevensLevel.parts.Trial;
import StevensLevel.screens.TaskScreen.StevensLevelUpdateViewEvent;
import harness.TestHarness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author tristangoffman
 */
public class TaskScreenTest extends TestHarness<TaskScreen> {
    
    
    @Before
    public void setUp() {
        setupEventBus();
        setInstance(new TaskScreen());
        eb().removeListener(getInstance());
        listen(spy(), StevensLevelViewListener.class);
        
        
    }
    
    @After
    public void tearDown() {
        removeEventBus();
    }

   @Test
   public void receiveUpdatesTest() throws InterruptedException{
        runSimpleTrialCausing3updatesOnScreen();
        verify(spy(), atMost(3)).update(any(StevensLevelUpdateViewEvent.class));
   }

    private void runSimpleTrialCausing3updatesOnScreen() throws InterruptedException {
        Trial tri = new Trial(1.0, 0, 200, 0.3);
        tri.run();
        Thread.sleep(1000);
    }
   
   @Test
   public void notifiesOfDirtyScreenOnce() throws InterruptedException{
     ScreenUpdateListener lis =  new ScreenUpdateListener() {

            @Override
            public void screenUpdated() {
              System.out.print("yoyo");
            }
        };
    ScreenUpdateListener lisSpy =  Mockito.spy(lis);
      listen(lisSpy, ScreenUpdateListener.class); 
      runSimpleTrialCausing3updatesOnScreen();
      
      verify(lisSpy, atMost(1)).screenUpdated();
   }
   
}
