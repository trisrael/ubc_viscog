package StevensLaw;

import java.io.File;


/**
 *
 * @author tristangoffman
 */
public class Experiment implements experiment.Experiment{
    private static Experiment exp;
    
    /** Members **/
    private ExperimentControl eCon = null;
    
    
    public Experiment(){
       
        this.eCon = new ExperimentControl();     
    }
    
    protected ViewControl getViewControl(){
        return eCon.getViewControl();
    }
    
    public ExperimentControl getExperimentControl(){
        return eCon;
    }

    @Override
    public void run() {
        
    }

    @Override
    public void test() {
        throw new UnsupportedOperationException("Not supported yet.");
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
