package StevensLaw;

import java.io.File;


/**
 *
 * @author tristangoffman
 */
public class Experiment implements experiment.Experiment{
    
    /** Members **/
    private ExperimentControl eCon = null;
    private ViewControl vCon = null;
    
    public Experiment(){
        this.vCon = new ViewControl();
        this.eCon = new ExperimentControl();  
        vCon.addListener(eCon);
    }  
    
    public ViewControl getViewControl(){
        return vCon;
    }
    
    public ExperimentControl getExperimentControl(){
        return eCon;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
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
