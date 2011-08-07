/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.screens.TaskScreen;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;
import StevensLaw.Task;
import java.lang.Iterable;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.core.IsNull.*;
import static org.hamcrest.core.Is.*;
//import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class TaskRoundTest {

    private static TaskRound rnd;

    public TaskRoundTest() {
    }

    public static void setUp() {
        rnd = new TaskRound(1.0, 0);
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
            
            assertThat(rnd.getTasks(), notNullValue());
        }
        
        @Test
        public void getScreenClassIsTaskScreen() throws InstantiationException, IllegalAccessException{
            throw new RuntimeException(rnd.getScreenClass().getName());
        }
        
        
    }
}
