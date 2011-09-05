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
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author tristangoffman
 */
public class TaskDesignTest {
    
   

    /**
     * Test of prop method, of class TaskDesign.
     */
    @Test
    public void testProp() {
        String memberName = "";
        TaskDesign instance = new TaskDesign();
        instance.setBaseDesign(new BaseDesign());
        assertThat((int) instance.prop("numTrials"), is(instance.getBaseDesign().getNumTrials()));
    }
}
