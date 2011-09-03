/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

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
public class TaskTest {
    
    public TaskTest() {
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
     * Test of adjustCorrelation method, of class Task.
     */
    @Test
    public void testAdjustCorrelation() throws Exception {
        System.out.println("adjustCorrelation");
        double val = 0.0;
        Trial instance = null;
        double expResult = 0.0;
        double result = instance.adjustCorrelation(val);
        assertEquals(expResult, result, 0.0);
   
    }
}
