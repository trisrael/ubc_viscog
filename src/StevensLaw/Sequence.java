/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import java.util.ArrayList;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 4, 2011
 */
public class Sequence extends ArrayList<ExperimentPart> {

    public ExperimentPart first() {
        return getOrNull(0);
    }

    public ExperimentPart last() {
        return getOrNull(size() - 1);
    }

    //Wrappers
    private ExperimentPart getOrNull(int ind) {
        if (size() > 0) {
            return get(ind);
        }
        return null;
    }
}
