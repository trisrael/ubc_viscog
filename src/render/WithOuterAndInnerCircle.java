package render;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;


/**
 *Build a circle to be place inside the outerCircle
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public abstract class WithOuterAndInnerCircle extends WithOuterCircle{
    
    public Shape buildInner(){
        double s = dat().size();
        double quarterSize = s / 4;
        double halfSize = s = 2;
        return new Ellipse2D.Double(dat().x() + quarterSize, dat().y() + quarterSize, halfSize, halfSize);
    }

    @Override
    public void drawPointWith(Shape outerCircle) {
       drawPointWith(outerCircle, buildInner());
    }
    
    /**
     * Draw a point given both an outer and inner circle to work with
     * @param outerCircle
     * @param innerCircle 
     */
    public abstract void drawPointWith(Shape outerCircle, Shape innerCircle);
    
}
