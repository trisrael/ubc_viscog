package experiment;

import java.io.File;

public interface Experiment {
    /**
     * Run the experiment
     */
    public void run();

    /**
     * Test the experiment
     */
    public void test();

    /**
     * Show or hide the experiment configuration frame (if it exists).
     *
     * @param isShowFrame
     */
    public void showConfigureFrame(boolean isShowFrame);

    /**
     * Loads the experiment configuration from a default location.
     */
    public void loadConfiguration();

    /**
     * Loads the experiment configuration from <code>configFile</code>
     * 
     * @param configFile
     */
    public void loadConfigurationFromFile(File configFile);

    /**
     * Loads the (possibly partial) experiment configuration from a String.
     *
     * @param str
     */
    public void loadConfigurationFromString(String str);

    /**
     * Save the current experiment configuration to a predefined file.
     */
    public void saveConfiguration();

    /**
     * Save the current experiment configuration to <code>configFile</code>
     *
     * @param configFile
     */
    public void saveConfigurationToFile(File configFile);

    /**
     * Check the configuration string <code>conf</code> for errors.
     * 
     * @param conf
     * @return
     */
    public boolean checkConfigurationString(String conf);
}
