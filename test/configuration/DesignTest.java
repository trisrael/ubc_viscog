/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import common.condition.DotHueType;
import common.condition.DotStyleType;
import java.util.List;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Before;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.Constructor;
import static junit.framework.Assert.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class DesignTest {
    private Yaml yaml;
    
   @Before
   public void before(){
        TaskDesign des = new TaskDesign();
        Constructor cons = new Constructor(StevensLevelDesign.class);
       
       TypeDescription sequential = new TypeDescription(StevensLevelDesign.class);
       sequential.putListPropertyType("sequential", RoundDesign.class);
       
       TypeDescription counterbalanced = new TypeDescription(StevensLevelDesign.class);
       counterbalanced.putListPropertyType("counterbalanced", RoundDesign.class);
       
       
       cons.addTypeDescription(sequential);
       cons.addTypeDescription(counterbalanced);
       yaml = new Yaml(cons);
   }
    
    // , dotShape: Object, dotHue: black, dotWidth: 0.5, pointShape: 7
  

    @Test
    public void totalDots() throws ClassNotFoundException {
       
        StevensLevelDesign bean = (StevensLevelDesign) yaml.load("design:\n  points: 250");
        assertEquals(250, bean.getDesign().getPoints());
    }
    
    public <T extends Object> List<T> ls(T[] ts){
        return Arrays.asList(ts);
    }
    
    
      @Test
    public void sequential() throws ClassNotFoundException {
       
        StevensLevelDesign bean = (StevensLevelDesign) yaml.load("sequential:\n  - {trials: 3}");
        assertThat(bean.getSequential().size(), is(1) );
        assertThat(bean.getSequential().get(0).getTrials(), is(3));
    }
    
    @Test
    public void sequentialDots() throws ClassNotFoundException {
       
        StevensLevelDesign bean = (StevensLevelDesign) yaml.load("sequential:\n  - {dotStyle: Unfilled, dotHue: LightGray}");
        assertThat(bean.getSequential().size(), is(1) );
        assertThat(bean.getSequential().get(0).getDotHue(), is(DotHueType.LightGray));
        assertThat(bean.getSequential().get(0).getDotStyle(), is(DotStyleType.Unfilled));
    }
    
    
        @Test
    public void counterBalanced() throws ClassNotFoundException {
       
        StevensLevelDesign bean = (StevensLevelDesign) yaml.load("counterbalanced:\n  - {dotStyle: Unfilled, dotHue: LightGray, lowCorr: 0.1, highCorr: 0.8, axisOn: false, labelsOn: true}");
        final List<RoundDesign> list = bean.getCounterbalanced();
        assertThat(list.size(), is(1) );
        assertThat(list.get(0).getDotHue(), is(DotHueType.LightGray));
        assertThat(list.get(0).getDotStyle(), is(DotStyleType.Unfilled));
        assertThat(list.get(0).getLowCorr(), is(0.1));
        assertThat(list.get(0).getHighCorr(), is(0.8));
        assertThat(list.get(0).getAxisOn(), is("false"));
        assertThat(list.get(0).getLabelsOn(), is("true"));
    }
        
        
              @Test
    public void multi() throws ClassNotFoundException {
       
        StevensLevelDesign bean = (StevensLevelDesign) yaml.load("sequential:\n  - {dotStyle: Unfilled, dotHue: LightGray, lowCorr: 0.1, highCorr: 0.8, axisOn: false, labelsOn: true}\n  - {dotStyle: Unfilled, dotHue: LightGray, lowCorr: 0.1, highCorr: 0.8, axisOn: false, labelsOn: true}");
        final List<RoundDesign> list = bean.getSequential();
        assertThat(list.size(), is(2));
        
    }
 
   
}
