
package render;

import java.awt.Color;
import java.awt.Shape;
import static util.DrawUtil.*;
/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class RegularUnfilledWhitePoint extends WithOuterCircle{

    @Override
    public void drawPointWith(Shape outerCircle) {
        drawOutline(dat().graphics(), outerCircle, dat().pointColor(), Color.white);
    }
    
}
