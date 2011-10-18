package StevensLevel;

import StevensLevel.filesystem.FileSystemConstants;
import configuration.ExperimentConfiguration;
import configuration.StevensLevelDesign;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;
import yaml.StevensLevelDesignConstructor;


/**
 *
 * @author tristangoffman
 */
public class Experiment extends EventBusHelper implements experiment.Experiment{
    /** Members **/
    private static Experiment exp;
    private ExperimentControl eCon = null;
    
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

    @Override
    public void run() {
        ExperimentConfiguration conf = new ExperimentConfiguration(); 
        
        File confFile = configuration.ConfigurationHelper.retrieveConfFile(FileSystemConstants.CONF_FILENAME);
        BufferedReader stream = null;
        
        try {
            stream = new BufferedReader(new FileReader(confFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Experiment.class.getName()).log(Level.SEVERE, "No file: '" + FileSystemConstants.CONF_FILENAME + "' found in configuration directory. Needed to run StevensLevel experiment." , ex);
        }
        
        Yaml yaml = new Yaml(new StevensLevelDesignConstructor());
        conf.setDesign(StevensLevelDesign.class.cast(yaml.load(stream)));
        
        getExperimentControl().setConfiguration(conf);
        
        getExperimentControl().run();
    }

    @Override
    public void test() {
        getExperimentControl().setConfiguration(new ExperimentConfiguration()); //Use default options found within ExperimentConfiguration
        //getExperimentControl().getConfiguration().getBaseDesign().p"startTitle", "StevensLevel Test" );
        getExperimentControl().run();
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

}
