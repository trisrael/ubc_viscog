/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.ExperimentInteractionListener;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.awt.event.KeyEvent;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static interaction.BasicInteraction.*;
import static StevensLaw.State.*;

/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class ExperimentControlTest extends TestBase {

    public static ExperimentControl ex;

    public ExperimentControlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public static void setUp() {
        ex = new ExperimentControl();
    }

    @After
    public void tearDown() {
    }

    public static class AfterConstruct {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
        }

        @Test
        public void isListener() {
            ExperimentInteractionListener.class.cast(new ExperimentControl());
        }

        @Test
        public void hasViewControl() {
            hasFieldWith(ViewControl.class);
        }

        @Test
        public void viewControlInstantiated() {
            assertThat(ex.getViewControl(), notNullValue());
        }

        @Test
        public void hasTaskRounds() {
            assertThat(ex.getTaskRounds(), notNullValue());
        }
    }

    public static class TaskRounds {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
        }

        @Test
        public void addTaskRound() {
            TaskRound rnd = new TaskRound(1.0, 0);
            ex.addTaskRound(rnd);
            assertThat(ex.getTaskRounds(), contains(rnd));
        }

        @Test
        public void addTaskRoundisLast() {
            TaskRound rnd = new TaskRound(1.0, 0);
            TaskRound rnd2 = new TaskRound(1.0, 0);
            ex.addTaskRound(rnd);
            ex.addTaskRound(rnd2);
            List<TaskRound> rnds = ex.getTaskRounds();
            assertThat(rnds.get(rnds.size() - 1), is(rnd2));
        }
    }

    public static class Configuration {
       
        @Test
        public void placeHolder(){
            
        } 
    }

    public static class SetupWithoutConfiguration {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
            ex.setup();
        }

        @Test
        public void hasSequence() {
            assertThat(ex.getSequence(), notNullValue());
        }

        @Test
        public void startScreenFirstInSequence() {
        }

        @Test
        public void endScreenLastInSequence() {
        }
    }

    public static class SetupWithConfiguration extends SetupWithoutConfiguration {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
        }
        
        
    }
}
