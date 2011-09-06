/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw.screens;

import StevensLaw.UIEvent;
import java.awt.Image;
import screens.AbstractStrictScreen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 25, 2011
 */
public class BeginScreen  extends AbstractStrictScreen{

    @Override
    protected Image generateImage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void uiEventOccurred(UIEvent uIEvent, Object event) {
       //doesn't get updated
    }
    
}
