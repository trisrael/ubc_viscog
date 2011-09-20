/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bushe.swing.event.EventService;
import org.bushe.swing.event.EventServiceExistsException;
import org.bushe.swing.event.EventServiceLocator;
import org.bushe.swing.event.ThreadSafeEventService;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 20, 2011
 */
public class EventBusHelper {

    public final static String EVENT_SERVICE_NAME = "stevensLevelsEventService";
  
    public static EventService eb() {
        return eventbus();
    }

    public static EventService eventbus() {
        return EventServiceLocator.getEventService(EVENT_SERVICE_NAME);
    }

    static void setup() throws EventServiceExistsException {
        
            EventServiceLocator.setEventService(EVENT_SERVICE_NAME, new ThreadSafeEventService());
        
    }
    
}
