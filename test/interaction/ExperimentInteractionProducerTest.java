package interaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 10, 2011
 */
public abstract class ExperimentInteractionProducerTest<List, Prod> {
    
    private ExperimentInteractionListener lis;
    private ExperimentInteractionProducer prod;
    
    
    @Before
    public void setUp() throws InstantiationException, IllegalAccessException {
        prod = (ExperimentInteractionProducer) producerClass().newInstance();
        lis = (ExperimentInteractionListener) listenerClass().newInstance();
    }

    @After
    public void tearDown() {
        prod = null;
        lis = null;
    }
    
    public abstract Class<Prod> producerClass();
    
    public abstract Class<List> listenerClass();
    
    public ExperimentInteractionProducer prod(){
        return prod;
    }
    
    public ExperimentInteractionListener lis(){
        return lis;
    }
    
    
    //TESTS
    
    @Test
    public void addListeners() {
        prod().addListener(lis());
        assertThat("Should have listener added but was not found in listeners", lis(), isIn(prod().getListeners()));
    }

    @Test
    public void notifyListeners() {
        ExperimentInteractionListener obs = (ExperimentInteractionListener) mock(listenerClass());
        prod().addListener(obs);

        ExperimentInteractionEvent ev = new ExperimentInteractionEvent(BasicInteraction.TaskCompleted);
        prod().notifyListeners(ev);

        verify(obs).interactionOccured(ev);
    }

    @Test
    public void notifyListenersAfterContruct() {
        prod().notifyListeners(BasicInteraction.InvalidKeyPress);
    }

    @Test
    public void getListenersNotNullAfterConstruct() {
        assertThat("listeners should never be null, but were returned as such", prod().getListeners(), not(isNull()));
    }

}
