package StevensLevel;

import experiment.Subject;
import StevensLevel.filesystem.FileSystemConstants;
import StevensLevel.logging.ExperimentLogging;
import configuration.ExperimentConfiguration;
import configuration.StevensLevelDesign;
import experiment.AbstractExperiment;
import experiment.ExperimentType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.yaml.snakeyaml.Yaml;
import java.util.logging.Level;
import java.util.logging.Logger;
import yaml.StevensLevelDesignConstructor;
import static StevensLevel.EventBusHelper.*;


/**
 *
 * @author tristangoffman
 */
public class Experiment extends AbstractExperiment{ 
    /** Members **/
    private static Experiment exp;
    private ExperimentControl eCon = null;
    private Subject subject;

    
    /**
     * Can't set a new experiment configuration publicly, can only manipulate pre-existing configuration (ExperimentControl is built with one)
     * @return 
     */
    public ExperimentConfiguration getExperimentConfiguration() {
        return getExperimentControl().getConfiguration();
    }
    
    public Experiment(){
        EventBusHelper.setupEventBus();
        this.eCon = new ExperimentControl();
    }
    
    public ExperimentControl getExperimentControl(){
        return eCon;
    }

    
    public void run(int subjectNum, String initials) {
        setSubject(new Subject(subjectNum, initials, ExperimentType.StevensLevel));
        
        loadAndRun(FileSystemConstants.CONF_FILENAME);
    }

    private void loadAndRun(String filename) {
        ExperimentConfiguration conf = new ExperimentConfiguration(); 
        
        
        BufferedReader stream = getConfFileStream(filename);
        
        Yaml yaml = new Yaml(new StevensLevelDesignConstructor());
        conf.setDesign(StevensLevelDesign.class.cast(yaml.load(stream)));
        
        conf.counterbalance(getSubject());
        
        getExperimentControl().setConfiguration(conf);
        
        getExperimentControl().run();
    }

    private BufferedReader getConfFileStream(String filename) {
        File confFile = configuration.ConfigurationHelper.retrieveConfFile(filename);
        BufferedReader stream = null;
        try {
            stream = new BufferedReader(new FileReader(confFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Experiment.class.getName()).log(Level.SEVERE, "No file: '" + filename + "' found in configuration directory. Needed to run StevensLevel experiment." , ex);
        }
        return stream;
    }

    @Override
    public void test() {
        ExperimentConfiguration conf = new ExperimentConfiguration();
        setSubject(new Subject(1, "testSubject", ExperimentType.StevensLevel));
        loadAndRun(FileSystemConstants.CONF_TEST_FILENAME);
    }

    @Override
    public void showConfigureFrame(boolean isShowFrame) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadConfiguration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadConfigurationFromFile(File configFile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadConfigurationFromString(String str) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveConfiguration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveConfigurationToFile(File configFile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkConfigurationString(String conf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setSubject(Subject subject) {
        this.subject = subject;
        ExperimentLogging.setSubject(getSubject()); //used by both run/test so make sure subject is set for both
        ExperimentLogging.setup();
    }
    
    
    public Subject getSubject() {
        return subject;
    }

}
