/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.parts.Round;
import configuration.TaskDesign;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.core.IsNull.*;
//import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class TaskRoundTest {

    private static Round rnd;

    public TaskRoundTest() {
    }

    public static void setUp() {
        rnd = new Round(new TaskDesign());
    }

    @After
    public void tearDown() {
    }

    public static class AfterConstruct {

        @Before
        public void setUp() {
            TaskRoundTest.setUp();
        }

        @Test
        public void hasManyTasks() {
            
            assertThat(rnd.getTrials(), notNullValue());
        }
        
        @Test
        public void getScreenClassIsTaskScreen() throws InstantiationException, IllegalAccessException{
            throw new RuntimeException(rnd.getScreenClass().getName());
        }
        
        
    }
}
