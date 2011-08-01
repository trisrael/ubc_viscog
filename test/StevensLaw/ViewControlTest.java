/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.screens.TaskScreen;
import interaction.ExperimentInteractionEvent;
import interaction.Observable;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import screens.AbstractStrictScreen;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author tristangoffman
 */
public class ViewControlTest {
    private ViewControl cont;
    
    public ViewControlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        cont = new ViewControl();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void isObservable(){
        Observable.class.cast(new ViewControl());   
    }
    
    @Test 
    public void canAddScreens(){
        AbstractStrictScreen v = new TaskScreen();
        cont.addScreen(v);
        
        assertThat("Should have screen in ViewControl after addition", v, isIn(cont.getScreens()));        
    }
    
    @Test
    public void screenIsVisibleAfterSwitch(){        
        canAddScreens();        
        cont.setScreen(TaskScreen.class);
    }
            
}