/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;

import StevensLevel.events.ScreenChange;
import java.util.EventListener;

/**
 *
 * @author tristangoffman
 */
public interface ScreenChangeListener extends EventListener{
    
    /**
     * When a new screen change listener is published, either change some appropriate view, or do something else that must be done at this point.
     * @param chg 
     */
    public void changeScreen(ScreenChange chg);
    
}
