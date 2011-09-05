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
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author tristangoffman
 */
public class ExperimentConfigurationTest {

    /**
     * Test of ready method, of class ExperimentConfiguration.
     */
    @Test
    public void testReady() {
        System.out.println("ready");
        ExperimentConfiguration instance = new ExperimentConfiguration();
        assertThat(instance.getDesigns().length, is(0));
        
        instance.ready();
        assertThat(instance.getDesigns()[0], is(TaskDesign.class));
    
    }
}
