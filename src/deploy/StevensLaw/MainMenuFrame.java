/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy.StevensLaw;

import deploy.common.AbstractMainMenuFrame;
import experiment.Experiment;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class MainMenuFrame extends AbstractMainMenuFrame{

    @Override
    protected Experiment constructExperiment() {
        return new StevensLaw.Experiment();
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]){
        new MainMenuFrame().execute();
    }
}
