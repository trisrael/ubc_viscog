package render;

import configuration.PointShapeDrawer;
import util.DrawUtil;
import util.PointData;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 14, 2011
 */
public abstract class TPoint implements PointShapeDrawer{
    public abstract DrawOrientation orient();
    
    @Override
    public void drawPoint(PointData d){
        DrawUtil.drawT(d.graphics(), d.xCenter(), d.yCenter(), d.size(), d.pointColor(), orient());
    }
}
