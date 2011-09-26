package StevensLevel;

import configuration.ExperimentConfiguration;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author tristangoffman
 */
public class Experiment extends EventBusHelper implements experiment.Experiment{
    private static Experiment exp;
   
    
    
    
    /** Members **/
    private ExperimentControl eCon = null;
    
    /**
     * Can't set a new experiment configuration publicly, can only manipulate pre-existing configuration (ExperimentControl is built with one)
     * @return 
     */
    public ExperimentConfiguration getExperimentConfiguration() {
        return getExperimentControl().getConfiguration();
    }
    
    public Experiment(){
        EventBusHelper.setup();
        this.eCon = new ExperimentControl();
    }
    
    public ExperimentControl getExperimentControl(){
        return eCon;
    }

    @Override
    public void run() {
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
