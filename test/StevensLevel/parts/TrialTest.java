/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.parts;

import StevensLevel.View;

import StevensLevel.listeners.ScreenUpdateListener;
import StevensLevel.screens.TaskScreen;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static StevensLevel.EventBusHelper.*;

/**
 *
 * @author tristangoffman
 */
public class TrialTest {
    private Trial obj;
    private Trial spy;
    
    
    @Before
    public void setUp() throws Exception {
        setupEventBus();
         obj = new Trial(1.0, 0.0, 1.0, 200, 0.3);
        spy = spy(obj);

    }
    
    @Test
    public void afterFirstRun(){
        View view = new View();
        
        View spyView = spy(view);
        eventbus().removeListener(view);
        listen(spyView, ScreenUpdateListener.class);
        
        TaskScreen tsk = new TaskScreen();        
        spyView.setScreen(tsk);
        obj.run();
        
        verify(spyView, atMost(1)).screenUpdated();
    }
    
}
