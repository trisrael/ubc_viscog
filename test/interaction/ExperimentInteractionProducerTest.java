/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interaction;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author tristangoffman
 */
public class ExperimentInteractionProducerTest {

    private Observer lis;
    private Prod prod;

    class Prod extends ExperimentInteractionProducer {
    }

    class Observer implements ExperimentInteractionListener {

        @Override
        public void interactionOccured(ExperimentInteractionEvent ev) {
            //do nothing
        }
    }

    public ExperimentInteractionProducerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        prod = new Prod();
        lis = new Observer();
    }

    @After
    public void tearDown() {
        prod = null;
        lis = null;
    }

    @Test
    public void addListeners() {
        prod.addListener(lis);
        assertThat("Should have listener added but was not found in listeners", lis, isIn(prod.getListeners()));
    }

    @Test
    public void notifyListeners() {
        Observer obs = mock(Observer.class);
        prod.addListener(obs);

        ExperimentInteractionEvent ev = new ExperimentInteractionEvent(BasicInteraction.TaskCompleted);
        prod.notifyListeners(ev);

        verify(obs).interactionOccured(ev);
    }
    
    @Test
    public void notifyListenersAfterContruct() {
        prod.notifyListeners(BasicInteraction.InvalidKeyPress);
    }

    @Test
    public void getListenersNotNullAfterConstruct() {
        assertThat("listeners should never be null, but were returned as such", prod.getListeners(), not(isNull()));
    }
}
