/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;

import java.util.EventListener;

/**
 *
 * @author tristangoffman
 */
public interface ScreenUpdateListener extends EventListener{
    /**
     * Action to be performed from a screen signalling that some sort of update
     * has occurred on its data or withing -> in order for a redrawing of the screen 
     * to be deemed necessary.
     */
    void screenUpdated();
}
