
package render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import static util.DrawUtil.*;
/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class WhiteWithBlackCenterPoint extends WithOuterAndInnerCircle{

    @Override
    public void drawPointWith(Shape outerCircle, Shape innerCircle) {
        Graphics2D g = dat().graphics();
        Color color = dat().pointColor();
        
        drawOutline(g, outerCircle, color, Color.white);
        drawShape(g, innerCircle, color, color);
    }
    
}
