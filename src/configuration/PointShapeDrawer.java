/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.awt.Color;
import java.awt.Graphics2D;
import util.PointData;

/**
 *
 * @author tristangoffman
 */
public interface PointShapeDrawer {
    
    /** draws a point given coordinates and an object to draw on **/
    void drawPoint(PointData data);
}
