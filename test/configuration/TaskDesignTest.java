/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Spy;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Spy.*;

import static org.mockito.Mockito.*;

/**
 *
 * @author tristangoffman
 */
public class TaskDesignTest {
    private TaskDesign inst;
    
   
@Before
public void setup(){
   this.inst = new TaskDesign(); 
}
    
    
    /**
     * Test of prop method, of class TaskDesign.
     */
    @Test
    public void testDotSizeSetting() {
        String memberName = "";
        TaskDesign instance = new TaskDesign();
        instance.setBaseDesign(new Design());
        instance.setDotSize(4);
        assertThat(instance.intprop("dotSize"), is(new Integer(4)));
    }
    
    @Test
    public void afterInitializeDotSizeNonNull(){
        assertThat(inst.getDotSize(), is(notNullValue()));   
    }
    
    /**
     * Test of prop method, of class TaskDesign.
     */
    @Test
    public void testDefDotSize() {
        String memberName = "";
        TaskDesign instance = new TaskDesign();
        
        
        assertThat(instance.intprop("dotSize"), is(nullValue()));
    }
    
    
    @Test 
     public void ensureGetDotSize(){
        TaskDesign spy = spy(inst);
        spy.prop("dotSize");
        verify(spy, times(1)).getDotSize();
        
    }
}
