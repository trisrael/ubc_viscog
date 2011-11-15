/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel.listeners;

import StevensLevel.parts.Trial;
import java.util.EventListener;

/**
 *
 * @author tristangoffman
 */
public interface StevensLogListener extends EventListener{
    void logTrial(Trial trial);
}
