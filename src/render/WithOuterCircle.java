
package render;

import util.PointData;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public abstract class WithOuterCircle extends AbstractPointShapeDrawer{
    

    @Override
    public void drawPoint(PointData data) {
        super.drawPoint(data);
        drawPointWith(buildOuterCircle());    
    }
    
    /**
     * Build a regular circle shape using the size given.
     * @return 
     */
    public Shape buildOuterCircle(){
        PointData d = dat();
       return new Ellipse2D.Double(d.x(), d.y(), d.size(), d.size());
    }

    public abstract void drawPointWith(Shape outerCircle);
    
}
