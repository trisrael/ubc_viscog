package configuration;

import common.counterbalancing.CounterBalancedOrdering;
import org.junit.After;
import java.util.List;
import org.junit.Before;
import experiment.ExperimentType;
import experiment.Subject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import yaml.StevensLevelDesignConstructor;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 *@author tristangoffman
 */
public class ExperimentConfigurationTest {
    private ExperimentConfiguration conf;
    
    
    @Before
    public void setup() throws FileNotFoundException{
        fixtureDesign();

        conf = new ExperimentConfiguration();
        conf.setDesign(fixtureDesign());
    }

    @Test
    public void counterbalance() throws FileNotFoundException {
        conf.counterbalance(new Subject(1, "TG", ExperimentType.StevensLevel));
        List<RoundDesign> rnds = conf.getRoundDesigns();


        ExperimentConfiguration conf2 = new ExperimentConfiguration();
        conf2.setDesign(fixtureDesign());
        conf2.counterbalance(new Subject(2, "TG", ExperimentType.StevensLevel));
        
        assertThat(conf2.getRoundDesigns().get(0), not(equalTo(rnds.get(0))) );
    }
    
    @Test
    public void needsCounterBalance(){
        assertThat(conf.needsCounterBalance(), is(true));
    }
    
    @Test
    public void finalNotEqToSeq(){
        List<RoundDesign> li = conf.getDesign().getSequential();
        List<RoundDesign> others = conf.getDesign().getCounterbalanced();
        for(int i=0; i < li.size(); i++){
            li.get(0).merge(others.get(i));
        }
        
        conf.counterbalance(new Subject(1, "tg", ExperimentType.StevensLevel));
        assertThat(conf.getFinalRounds(), anyOf(not(equalTo(li))));
    }
    
    @After
    public void tearDown(){
        CounterBalancedOrdering.reset();
    }    
    
    /**
     * Helpers
     */
     private StevensLevelDesign fixtureDesign() throws FileNotFoundException {
        Yaml yaml = new Yaml(new StevensLevelDesignConstructor());

        String currentDir = new File(".").getAbsolutePath();
        Object res = yaml.load(new FileReader(new File(currentDir.substring(0,currentDir.length() - 1) + "test/configuration/multi_counterbalance.conf")));
        return StevensLevelDesign.class.cast(res);
    }
            
}
