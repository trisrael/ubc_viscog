/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.parts.ScreenInteraction;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 5, 2011
 */
public interface UIEventListener {
    public void uiEventOccurred(ScreenInteraction event, Object payload);
}
