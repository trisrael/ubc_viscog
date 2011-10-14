/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import configuration.PointShapeDrawer;
import java.awt.Color;
import java.awt.Graphics2D;
import util.PointData;

/**
 * Top level class for doing base things if needed
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
abstract class AbstractPointShapeDrawer implements PointShapeDrawer {
    private PointData dat;
    @Override
    public void drawPoint(PointData data){
        dat = data;
    }
    
    public PointData dat(){return dat;};
   
}
