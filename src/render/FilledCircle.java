
package render;

import configuration.PointShapeDrawer;
import java.awt.Graphics2D;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class FilledCircle implements PointShapeDrawer{

    @Override
    public void draw(Graphics2D g2, double size, double x, double y) {
        util.DrawUtil.drawPoint(g2, size, x, y, 2, 3, 4);
    }
    
    
    
}
