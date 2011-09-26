package StevensLevel;

import StevensLevel.parts.ExperimentPart;
import java.util.ArrayList;

/**
 *A custom ArrayList containing ExperimentParts, with some extra helper methods.
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
