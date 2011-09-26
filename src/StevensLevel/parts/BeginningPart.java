package StevensLevel.parts;

import StevensLevel.State;
import screens.BeginScreen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 10, 2011
 */
public class BeginningPart extends ExperimentPart<BeginScreen> {

    @Override
    public Class<BeginScreen> getScreenClass() {
        return BeginScreen.class;
    }
    
    @Override
            public void run(){
        super.run();
        setState(State.WAITING); //Beginning screen will always be in waiting state
    }
    
}
