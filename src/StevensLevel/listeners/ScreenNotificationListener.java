/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;

import java.util.EventListener;
import screens.AbstractScreen;
import screens.Screen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 26, 2011
 */
public interface ScreenNotificationListener extends EventListener{
    /**
     * Notification to listening objects of screen ready to be notified about new events..etc
     * @param <T>
     * @param screenClazz, type of Screen currently in place 
     */
    public <T extends Screen> void screenIsReady(Class<T> screenClazz);
}
