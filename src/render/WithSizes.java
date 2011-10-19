/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import configuration.PointShapeDrawer;
import util.PointData;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 14, 2011
 */
public class WithSizes extends AbstractPointShapeDrawer{

    public double quarterSize(){
        return dat().size() /4;
    }
    
    public double halfSize(){
        return dat().size() / 2;
    }
}
