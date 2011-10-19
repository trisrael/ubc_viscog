/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

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

    @Test
    public void counterbalance() throws FileNotFoundException {
        Yaml yaml = new Yaml(new StevensLevelDesignConstructor());

        String currentDir = new File(".").getAbsolutePath();
        Object res = yaml.load(new FileReader(new File(currentDir.substring(0,currentDir.length() - 1) + "test/configuration/multi_counterbalance.conf")));
        StevensLevelDesign des = StevensLevelDesign.class.cast(res);

        ExperimentConfiguration conf =
                new ExperimentConfiguration();
        conf.setDesign(des);
        
        conf.counterbalance(new Subject(3, "TG", ExperimentType.JND));
        assertThat(conf.getRoundDesigns().size(), is(des.getSequential().size()) );
    }
}
