/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class ExperimentTest {

    private static Experiment exp;

    public static void setUp() {
        exp = new Experiment();
    }

    public static class AfterConstruct {

        @Before
        public void setUp() {
            ExperimentTest.setUp();
        }
        String instStr = " should be an instance";

        public String isInstance(String field) {
            return field + instStr;
        }

        @Test
        public void HasExperimentControl() {
            assertThat(isInstance("ExperimentControl"), exp.getExperimentControl(), is(ExperimentControl.class));
        }

        @Test
        public void ExperimentControlIsListenerOfViewControl() {
            HasExperimentControl();


            assertThat("ExperimentControl needs to listen to ViewControl but is not", exp.getExperimentControl(), isIn(exp.getExperimentControl().getViewControl().getListeners()));
        }
    }

    public static class TestExperiment {

        @Before
        public void setUp() {
            ExperimentTest.setUp();
        }
        
        
        @Test
        public void shouldSeeStartScreen(){
            exp.test();
            
            assertTrue(exp.getViewControl().isVisible());
        }
    }
}
