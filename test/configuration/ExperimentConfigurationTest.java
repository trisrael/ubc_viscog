/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import com.sun.tools.internal.ws.processor.util.DirectoryUtil;
import common.counterbalancing.CounterBalancedOrdering;
import org.junit.After;
import java.util.List;
import org.junit.Before;
import common.filesystem.FileSystem;
import experiment.ExperimentType;
import experiment.Subject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import yaml.StevensLevelDesignConstructor;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 *
 * @author tristangoffman
 */
public class ExperimentConfigurationTest {
    private ExperimentConfiguration conf;
    private StevensLevelDesign des;
    
    @Before
    public void setup() throws FileNotFoundException{
        Yaml yaml = new Yaml(new StevensLevelDesignConstructor());

        String currentDir = new File(".").getAbsolutePath();
        Object res = yaml.load(new FileReader(new File(currentDir.substring(0,currentDir.length() - 1) + "test/configuration/multi_counterbalance.conf")));
        des = StevensLevelDesign.class.cast(res);

        conf = new ExperimentConfiguration();
        conf.setDesign(des);
    }

    @Test
    public void counterbalance() throws FileNotFoundException {
        conf.counterbalance(new Subject(1, "TG", ExperimentType.StevensLevel));
        List<RoundDesign> rnds = conf.getRoundDesigns();
        
        
        Yaml yaml = new Yaml(new StevensLevelDesignConstructor());

        String currentDir = new File(".").getAbsolutePath();
        Object res = yaml.load(new FileReader(new File(currentDir.substring(0,currentDir.length() - 1) + "test/configuration/multi_counterbalance.conf")));
        StevensLevelDesign des2 = StevensLevelDesign.class.cast(res);
        ExperimentConfiguration conf2 = new ExperimentConfiguration();
        conf2.setDesign(des2);
        conf2.counterbalance(new Subject(2, "TG", ExperimentType.StevensLevel));
        
        assertThat(conf2.getRoundDesigns().get(0), not(equalTo(rnds.get(0))) );
    }
    
    @Test
    public void needsCounterBalance(){
        assertThat(conf.needsCounterBalance(), is(true));
    }
    
    @After
    public void tearDown(){
        CounterBalancedOrdering.reset();
    }        
            
}
