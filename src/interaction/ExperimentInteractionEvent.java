package interaction;

import java.util.EventObject;

/**
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 20, 2011
 */
public class ExperimentInteractionEvent extends EventObject {

    /** Members **/

    public ExperimentInteractionEvent(ExperimentInteraction exInt) {
        super(exInt); //Hack to allow for extension of EventObject
        this.source = exInt; //Might as well use space already allocated in object, versus wasting more memory
    }

    public <E extends ExperimentInteraction> E getType() {
        return (E) this.source; //Let it throw an error if somebody subclasses and allows for another object to be
        //set to source
    }
}
