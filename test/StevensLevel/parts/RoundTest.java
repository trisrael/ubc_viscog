/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.parts;

import harness.ExtendableRoundTest;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;


/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class RoundTest {
    
    public static class AfterConstruct extends ExtendableRoundTest
    {
        @Test
        public void emptyTrialList(){
            assertEquals(obj().getTrials().size(), 0);
        }
        
    }
        
    public static class AfterSetup extends ExtendableRoundTest{
        @Test(expected=RuntimeException.class)
        public void blowsUpIfNoTrialNums(){
            obj().setup();
        }
        
        @Test
        public void trialsCreated(){
            spy().setNumTrials(4);
            spy().setup();
            
            verify(spy(), null);
            
        }
               
    }
    
}
