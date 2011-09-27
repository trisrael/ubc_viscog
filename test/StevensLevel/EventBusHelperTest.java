/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

import org.junit.Before;
import com.northconcepts.eventbus.EventBus;
import java.util.EventListener;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.*;
import static StevensLevel.EventBusHelper.*;


/**
 *
 * @author tristangoffman
 */
public class EventBusHelperTest {

  
    
   final String TOPIC_NAME = "do";
    final private String EVENT_PAYLOAD = "occurred";
    
    public EventBusHelperTest() {
    }

    @Before
    public void setUp() throws Exception {
         setupEventBus();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
   
    
    @After
    public void tearDown() {
        
    }
    
    
   
    public void reset(){
        EventBus eb = eb();
        setupEventBus();
        assertThat(eb, is(eb()));
    }
   

    /**
     * Test of eb method, of class EventBusHelper.
     */
    @Test
    public void testEb() {
        System.out.println("eb");
        
        EventBus result = eb();
        assertThat(result, CoreMatchers.any(EventBus.class));
    }

    /**
     * Test of eventbus method, of class EventBusHelper.
     */
    @Test
    public void testEventbus() {
        System.out.println("eventbus");
        
        EventBus result = eventbus();
        assertThat(result, CoreMatchers.any(EventBus.class));
    }
    
    @Test
    public void receivesSubscriptions(){
        SpiedClass obj = new SpiedClass();
        listen(obj.getSpy(), TestEventListener.class);
        pb(this, TestEventListener.class).test();
   
        verify(obj.getSpy(), atMost(1)).test();
    }
    
    
    private interface TestEventListener extends EventListener{
            void test();
    }
    
    
    public class SpiedClass implements TestEventListener{
        private SpiedClass spy;
        public String lastEventPayload;

        public SpiedClass getSpy() {
            return spy;
        }

        
        public SpiedClass(){
            this.spy = spy(this);
        }
        
      

        @Override
        public void test() {
         
        }
        
        
        
    }
}
