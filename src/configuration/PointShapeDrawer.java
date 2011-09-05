/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.awt.Graphics2D;

/**
 *
 * @author tristangoffman
 */
public interface PointShapeDrawer {
    
    /** draws a point given coordinates and an object to draw on **/
    void draw(Graphics2D g2, double size, double x, double y);
}
