/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

import java.util.EventListener;
import com.northconcepts.eventbus.EventBus;
import com.northconcepts.eventbus.EventFilter;


/**

 * @author Tristan Goffman(tgoffman@gmail.com) Sep 20, 2011
 */
public class EventBusHelper {

    private static EventBus eb;

    
    static {
        setupEventBus();
    }
    
    public static EventBus eb() {
        return eventbus();
        
    }

    public static EventBus eventbus() {
        
        return eb;
    }
    
    /**
     * Publish an event 
     * @param eventSource
     * @param listenerInterface
     * @param event 
     */
    public static <T extends EventListener> T pb(Object eventSource, Class<T> listenerInterface){
       return eb().getPublisher(eventSource, listenerInterface);
    }
    
    /**
     * Add a listener for the type given to the EventBus.
     * @param <T>
     * @param clazz
     * @param listener 
     */
    public static <T extends EventListener> void listen(T listener, Class<T> clazz){
        eb().addListener( clazz, EventFilter.NULL, listener);
    }
    
    
    public static <T extends EventListener> void stoplistening(T listener, Class<T> clazz){
        eb().removeListener(clazz, listener);
    }

    public static void setupEventBus() {
        if(eb == null)
            eb = new EventBus();
    }

    public static void removeEventBus() { 
       eb = null;
    }
    
}
