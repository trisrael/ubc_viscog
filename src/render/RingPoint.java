/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.awt.Shape;
import static util.DrawUtil.drawRing;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 14, 2011
 */
public abstract class RingPoint extends WithOuterCircle{

    @Override
    public void drawPointWith(Shape outerCircle) {
     drawRing(dat().graphics(), outerCircle, dat().pointColor(), ringStrokeSize());
    }
    
    /**
     * For anonymous class building, requires implementers to supply a integer value that the ring to be rendered should be drawn with (eg. 1, 2, 3 probably in pixels)
     * @return 
     */
    public abstract int ringStrokeSize();
    
}
