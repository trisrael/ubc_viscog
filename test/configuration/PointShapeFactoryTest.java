/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import configuration.PointShapeFactory;
import harness.TestHarness;
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
public class PointShapeFactoryTest extends TestHarness<PointShapeFactory> {
    

    
    @Before
    public void setUp() {
        setInstance(new PointShapeFactory());
    }
    
    @Test
    public void appendsDefaultClassExtension(){
        String name = "MyClassName";
        
        String full = PointShapeFactory.DEFAULT_CLASS_EXTENSION + name;
        getInstance().setClassName(name);
        assertThat(getInstance().getClassName(), is(full));
        
    }
  
}
