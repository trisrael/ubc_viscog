/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy.JND;

import deploy.common.AbstractMainMenuFrame;
import experiment.BasicJNDExperiment;
import experiment.Experiment;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 3, 2011
 */
public class MainMenuFrame extends AbstractMainMenuFrame{

    @Override
    protected Experiment constructExperiment() {
        return new BasicJNDExperiment();
    }
    
}
