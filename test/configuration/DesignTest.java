/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.util.List;
import configuration.Design;
import java.io.File;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Before;
import org.mockito.internal.util.ArrayUtils;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import render.FilledCircle;
import yaml.DesignConstructor;
import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class DesignTest {
    private Yaml yaml;
    
    @Before
    public void before(){
        Class<?> clazz = Design.class;
    
        yaml = new Yaml(new DesignConstructor( DesignTest.class.getClassLoader()));
    }
    
    // , dotShape: Object, dotHue: black, dotWidth: 0.5, pointShape: 7
  

    @Test
    public void totalDots() throws ClassNotFoundException {
          
        Design bean = (Design) yaml.load("{totalDots: 100}");
        assertEquals(100, bean.getTotalDots());
    }
    
    @Test
    public void hueString() throws ClassNotFoundException {
          
        Design bean = (Design) yaml.load("{dotHues: [black]}");
        
        assertThat(ls(bean.getDotHues()), hasItem("black"));
    }
    
    public <T extends Object> List<T> ls(T[] ts){
        return Arrays.asList(ts);
    }
    
    
   @Test //Doesn't deal well with hash tag for now leave as is.
    public void hueHex() throws ClassNotFoundException {
          
        Design bean = (Design) yaml.load("{dotHues: [453234]}");
        assertThat(ls(bean.getDotHues()), hasItem("453234"));
    }
   
   @Test
   public void dotShape(){
          Design bean = (Design) yaml.load("{dotShapes: [!shape render.FilledCircle] }");
          assertThat(ls(bean.getDotShapes()).get(0), instanceOf(FilledCircle.class));
   }   
   
    @Test
   public void defaultNamedotShape(){
          Design bean = (Design) yaml.load("{dotShapes: [!shape FilledCircle] }");
          assertThat(ls(bean.getDotShapes()).get(0),instanceOf(FilledCircle.class));
   } 
   
}
