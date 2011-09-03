/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw.parts;

import StevensLaw.ExperimentPart;
import StevensLaw.screens.BeginScreen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 10, 2011
 */
public class BeginningPart extends ExperimentPart<BeginScreen> {

    @Override
    public Class<BeginScreen> getScreenClass() {
        return BeginScreen.class;
    }
    
}
