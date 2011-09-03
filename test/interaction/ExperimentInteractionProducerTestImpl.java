/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interaction;

class Prod extends ExperimentInteractionProducer {
}

class Observer implements ExperimentInteractionListener {

    @Override
    public void interactionOccured(ExperimentInteractionEvent ev) {
        //do nothing
    }
}

/**
 *
 * @author tristangoffman
 */
public class ExperimentInteractionProducerTestImpl extends ExperimentInteractionProducerTest<Observer, Prod> {

    
    @Override
    public Class<Prod> producerClass() {
       return Prod.class;
    }

    @Override
    public Class<Observer> listenerClass() {
        return Observer.class;
    }
}
