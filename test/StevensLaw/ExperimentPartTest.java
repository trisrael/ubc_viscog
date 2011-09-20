/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.parts.ExperimentPart;
import screens.BeginScreen;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author tristangoffman
 */
public class ExperimentPartTest {

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
