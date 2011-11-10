package configuration;

import java.util.List;
import common.condition.DotHueType;
import common.condition.DotStyleType;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
 

/**
 * @author tristangoffman
 */
public class RoundDesignTest {
    
    @Test
    public void changedBeansList(){
        RoundDesign rnd = new RoundDesign();
        rnd.setDotHue(DotHueType.IsoBlue);
        List<Method> map = rnd.difference();
        
        assertThat(map.size(), is(1));
    }
    
    @Test
    public void diffBet() throws NoSuchMethodException{
        RoundDesign rnd = new RoundDesign();
        RoundDesign rnd2 = new RoundDesign();
        rnd.setDotHue(DotHueType.IsoBlue);
        rnd2.setDotStyle(DotStyleType.Point);
        List<Method> map = rnd.differenceBetween(rnd2);
        
        assertThat(map.size(), is(1));
        assertThat(map.get(0), is(RoundDesign.class.getMethod("getDotStyle")));
    }
    
    @Test
    public void merge(){
           RoundDesign rnd = new RoundDesign();
        RoundDesign rnd2 = new RoundDesign();
        rnd.setDotHue(DotHueType.IsoBlue);
        rnd2.setDotStyle(DotStyleType.Point);
        rnd.merge(rnd2);
        assertThat(rnd.difference().size(), is(2));
        assertThat(rnd.getDotStyle(), is(DotStyleType.Point));
    }
}