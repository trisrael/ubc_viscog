package StevensLevel.parts;

import screens.ExperimentEndScreen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 10, 2011
 */
public class EndingPart extends ExperimentPart<ExperimentEndScreen> {

    @Override
    public Class<ExperimentEndScreen> getScreenClass() {
       return ExperimentEndScreen.class;
    }
    
}
