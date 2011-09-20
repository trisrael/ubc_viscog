/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import org.bushe.swing.event.EventService;
import org.bushe.swing.event.EventServiceExistsException;
import org.bushe.swing.event.EventServiceLocator;
import org.bushe.swing.event.annotation.EventTopicSubscriber;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.*;
import static StevensLaw.EventBusHelper.*;


/**
 *
 * @author tristangoffman
 */
public class EventBusHelperTest {
    
   final String TOPIC_NAME = "do";
    final private String EVENT_PAYLOAD = "occurred";
    
    public EventBusHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
         EventBusHelper.setup();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws EventServiceExistsException {
      
    }
    
    @After
    public void tearDown() {
        
    }
    
    
    @Test(expected= EventServiceExistsException.class)
    public void reset() throws EventServiceExistsException{
        EventBusHelper.setup();
    }
   

    /**
     * Test of eb method, of class EventBusHelper.
     */
    @Test
    public void testEb() {
        System.out.println("eb");
        
        EventService result = EventBusHelper.eb();
        assertThat(result, CoreMatchers.any(EventService.class));
    }

    /**
     * Test of eventbus method, of class EventBusHelper.
     */
    @Test
    public void testEventbus() {
        System.out.println("eventbus");
        
        EventService result = EventBusHelper.eventbus();
        assertThat(result, CoreMatchers.any(EventService.class));
    }
    
    @Test(expected = AbstractMethodError.class)
    public void receivesSubscriptions() throws InterruptedException{
        SpiedClass obj = new SpiedClass();
        
        testEb();
        Thread thr = new Thread(new Runnable() {

                   @Override
                   public void run() {
                       eb().publish(TOPIC_NAME, EVENT_PAYLOAD);
                   }
               });
        thr.start();
      
    }
    
    
    public class SpiedClass{
        private SpiedClass spy;
        public String lastEventPayload;

        public SpiedClass getSpy() {
            return spy;
        }

        
        public SpiedClass(){
            this.spy = spy(this);
        }
        
        @EventTopicSubscriber(topic=TOPIC_NAME, eventServiceName= EventBusHelper.EVENT_SERVICE_NAME)
        public void subscribedMethod(String topic, Object event){
           lastEventPayload = (String) event;
            spy.methodCaught(); //mechanism to use spy to verify this was called
        }

        private void methodCaught() {
          throw new AbstractMethodError();
        }
        
        
        
    }
}
