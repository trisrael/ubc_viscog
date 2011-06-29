/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deploy.JND;

import javax.swing.UIManager;

/**
 *
 * @author Will
 */
public class GUIGlobals {

    static public final void setLookAndFeel(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.err.println("Could not set look and feel.");
        }
    }

}
