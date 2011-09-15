package interaction;

import StevensLaw.StevensLevelInteraction;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Annotation;


/**
 * Marker interface for dealing with different reaction types ExperimentInteraction types => (BasicInteraction, GraphInteraction)
 * 
 * Classes/objects that would like to be compatible with this interface should mark themselves as a InteractionReactor as such and then for corresponding 
 * interactions that are to be dealt with a method should be annotated with the specific class of interactions and appropriately defining methods
 * with a parameter for some some Enum extending ExperimentInteraction
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 8, 2011
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ReactTo{
    Class value();
}

/**
 * Simple class showing how a class may work with different types of enum types
 * @author tristangoffman
 */
class ReactToExample{
    
    
    
    @ReactTo(StevensLevelInteraction.class)
    public void corrReactions(StevensLevelInteraction e){
        switch(e){
            case CorrelationUp:
                //doSomething
                break;
                
            case CorrelationDown:
                //doSomethingElse
                break;
        }
    }
}
