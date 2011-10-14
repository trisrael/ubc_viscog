/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.awt.Shape;
import static util.DrawUtil.*;
/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class RegularFilledPoint extends WithOuterCircle{

    @Override
    public void drawPointWith(Shape outerCircle) {
      drawShape(dat().graphics(), outerCircle, dat().pointColor(), dat().pointColor());
    }
    
    
}
