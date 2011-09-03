/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.screens.BeginScreen;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author tristangoffman
 */
public class ExperimentPartTest {
    
    public ExperimentPartTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getScreenClass method, of class ExperimentPart.
     */
    @Test
    public void testGetScreenClass() {
        System.out.println("getScreenClass");
        ExperimentPart instance = new ExperimentPartImpl();
        Class expResult = BeginScreen.class;
        Class result = instance.getScreenClass();
        assertEquals(expResult, result);
    }
    

    public class ExperimentPartImpl extends ExperimentPart {

        @Override
        public Class<BeginScreen> getScreenClass() {
           return BeginScreen.class;
        }
    }
}
