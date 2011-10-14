/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.condition;

import configuration.PointShapeDrawer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import render.DrawOrientation;
import render.RegularFilledPoint;
import render.RegularUnfilledWhitePoint;
import render.RingPoint;
import render.WhiteWithBlackCenterPoint;
import render.WithOuterAndInnerCircle;
import render.WithOuterCircle;
import static common.condition.DotHueType.*;
import static common.condition.DotStyleType.*;
import java.awt.geom.Line2D;
import render.TPoint;
import render.WithSizes;
import static util.DrawUtil.*;
import util.PointData;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class ConditionHelper {

    private static Map<DotHueType, Integer> dotHueNumMap;
    private static Map<Integer, Color> numToColorMap;
    private static Map<DotStyleType, Integer> dotStyleNumMap;
    private static Map<DotStyleType, PointShapeDrawer> dotStyleDrawer;

    
    //Instantiation
    static {
        
        //This method of tracking conditions is not as efficient, but much easier to read (a better way might in fact be to use classes and then only build
        //classes when necessary, then caching the objects as they are produced (but hopefully this will do for now)

        //Num to DotHue mapping
        dotHueNumMap = new EnumMap<DotHueType, Integer>(DotHueType.class);
        dotHueNumMap.put(Black, 1);
        dotHueNumMap.put(DarkGray, 2);
        dotHueNumMap.put(MidGray, 3);
        dotHueNumMap.put(LightGray, 4);
        dotHueNumMap.put(Mixed, 5);
        dotHueNumMap.put(IsoGray, 6);
        dotHueNumMap.put(IsoRed, 7);
        dotHueNumMap.put(IsoGreen, 8);
        dotHueNumMap.put(IsoBlue, 9);

        //Num to color

        numToColorMap = new HashMap<Integer, Color>();
        numToColorMap.put(1, Color.black);
        numToColorMap.put(2, new Color(83, 83, 83));
        numToColorMap.put(3, new Color(145, 145, 145));
        numToColorMap.put(4, new Color(201, 201, 201));
        numToColorMap.put(6, new Color(122, 122, 122));
        numToColorMap.put(7, new Color(217, 13, 13));
        numToColorMap.put(8, new Color(0, 151, 0));
        numToColorMap.put(9, new Color(81, 81, 253));

        
        //DotStyle to Num
        dotStyleNumMap.put(Filled, 1);
        dotStyleNumMap.put(Unfilled, 2);
        dotStyleNumMap.put(WhiteOutBlackIn, 3);
        dotStyleNumMap.put(BlackOutWhiteIn, 4);
        dotStyleNumMap.put(ThinRing, 6);
        dotStyleNumMap.put(MedRing, 7);
        dotStyleNumMap.put(ThickRing, 8);
        dotStyleNumMap.put(Point, 9);
        //NOTE: space between nums..?
        
        dotStyleNumMap.put(Plusses, 11);
        dotStyleNumMap.put(UprightGeometricT, 12);
        dotStyleNumMap.put(RightGeometricT, 13);
        dotStyleNumMap.put(BottomGeometricT, 14);
        dotStyleNumMap.put(LeftGeometricT, 15);
        dotStyleNumMap.put(UprightCentroidT, 16);
        dotStyleNumMap.put(RightCentroidT, 17);
        dotStyleNumMap.put(BottomCentroidT, 18);
        dotStyleNumMap.put(LeftCentroidT, 19);
        
        //NOTE: space between nums..?
        dotStyleNumMap.put(Ring, 21);
        dotStyleNumMap.put(Triangle, 22);
        dotStyleNumMap.put(X, 23);
        dotStyleNumMap.put(Plus, 24);
        
        
        dotStyleDrawer.put(Filled, new RegularFilledPoint());
        dotStyleDrawer.put(Unfilled, new RegularUnfilledWhitePoint());
        dotStyleDrawer.put(WhiteOutBlackIn, new WhiteWithBlackCenterPoint());
        
        dotStyleDrawer.put(BlackOutWhiteIn, new WithOuterAndInnerCircle() {

            @Override
            public void drawPointWith(Shape outerCircle, Shape innerCircle) {
              drawShape(dat().graphics(), outerCircle, Color.BLACK, Color.BLACK);
              drawShape(dat().graphics(), innerCircle, Color.white, Color.white);
            }
        });
        

        
        
       dotStyleDrawer.put(ThinRing, new RingPoint() {

            @Override
            public int ringStrokeSize() {
                return 1;
            }
        });
       
       
       
        
        
       dotStyleDrawer.put(MedRing, new RingPoint() {

            @Override
            public int ringStrokeSize() {
                return 2;
            }
        });
       
       
       dotStyleDrawer.put(ThickRing, new RingPoint() {

            @Override
            public int ringStrokeSize() {
                return 3;
            }
        });
       
       //TODO: Is this exactly the same as first?
       dotStyleDrawer.put(Point, new RegularFilledPoint());
       
       dotStyleDrawer.put(Point, new WithSizes() {

            @Override
            public void drawPoint(PointData data) {
                Graphics2D g2 = data.graphics();
                double y_cent = data.yCenter();
                double x_cent = data.xCenter();
                
                Shape plusSignHoriz = new Line2D.Double(x_cent - halfSize(), y_cent, x_cent + halfSize(), y_cent);
                Shape plusSignVert = new Line2D.Double(x_cent, y_cent - halfSize(), x_cent, y_cent + halfSize());
                //g2.draw(outerCircle);
                g2.setPaint(data.pointColor());
                g2.draw(plusSignHoriz);
                g2.draw(plusSignVert);
            }
        });
       
       //Geometrics
       
       dotStyleDrawer.put(UprightGeometricT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Upright;
            }
        });
       
        
       dotStyleDrawer.put(RightGeometricT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Right;
            }
        });
       
        
       dotStyleDrawer.put(BottomGeometricT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Bottom;
            }
        });
       
        
       dotStyleDrawer.put(LeftGeometricT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Left;
            }
        });
       
        //Centroids
       dotStyleDrawer.put(UprightCentroidT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Upright;
            }
        });
       
        
       dotStyleDrawer.put(RightCentroidT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Right;
            }
        });
       
        
       dotStyleDrawer.put(BottomCentroidT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Bottom;
            }
        });
       
        
       dotStyleDrawer.put(LeftCentroidT, new TPoint() {

            @Override
            public DrawOrientation orient() {
             return DrawOrientation.Left;
            }
        });

       
       dotStyleDrawer.put(Ring, new RingPoint() {

            @Override
            public int ringStrokeSize() {
          return 1;
            }
        });
       
       dotStyleDrawer.put(Triangle, new PointShapeDrawer() {

            @Override
            public void drawPoint(PointData data) {
              drawTri(data.graphics(), data.xCenter(), data.yCenter(), data.size(), data.pointColor());
            }
        });
       
       dotStyleDrawer.put(Plus, new PointShapeDrawer() {

            @Override
            public void drawPoint(PointData data) {
              drawPlus(data.graphics(), data.xCenter(), data.xCenter(), data.size(), data.pointColor());      
            }
        }) ;
       
       dotStyleDrawer.put(X, new PointShapeDrawer() {

            @Override
            public void drawPoint(PointData data) {
                drawX(data.graphics(), data.xCenter(), data.yCenter(), data.size(), data.pointColor());
            }
        }) ;
    }
    
    
    //HELPER METHODS
    

    public static Color getDotColor(int fillCol) {
        synchronized (numToColorMap) {
            return numToColorMap.get(fillCol);
        }
    }

    public static PointShapeDrawer getPointShapeDrawer(int pointStyle) {
        DotStyleType type;
        DotStyleType val = null;
        
        //O(n) for no real reason
        synchronized(dotStyleNumMap){
            DotStyleType[] vals = DotStyleType.values();
            for (int i = 0; i < vals.length; i++) {
                val = vals[i];
                if (dotStyleNumMap.get(val) == pointStyle)
                    break;
                
            }
        }
        
        synchronized(dotStyleDrawer){
            return dotStyleDrawer.get(val);
        }
    }
}
