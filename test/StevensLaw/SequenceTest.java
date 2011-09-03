/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.parts.BeginningPart;
import harness.TestHarness;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tristangoffman
 */
public class SequenceTest extends TestHarness<Sequence>{

    
    @Before
    public void setUp() {
        obj(new Sequence());
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void firstEmpty(){
        assertNull(obj().first());
    }
    
    @Test
    public void firstNonEmpty(){
        BeginningPart added = new BeginningPart();
        
        obj().add(added);
        assertEquals(obj().first(), added);
    }
    
    @Test
    public void lastEmpty(){
        assertNull(obj().last());
    }
    
      
    @Test
    public void lastNonEmpty(){
        BeginningPart added = new BeginningPart();
        
        obj().add(added);
        assertEquals(obj().last(), added);
    }
   

}
