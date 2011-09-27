/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;

import StevensLevel.screens.TaskScreen.StevensLevelUpdateViewEvent;
import java.util.EventListener;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 21, 2011
 */
public interface StevensLevelViewListener extends EventListener{
    

    public void update(StevensLevelUpdateViewEvent buildAdjustedPayload);
}
