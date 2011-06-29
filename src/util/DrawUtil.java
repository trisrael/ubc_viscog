package util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.geom.Point2D;
import java.lang.Math;

public class DrawUtil {


    // Question: do I want to have an if/then statement within the DrawPoint function
    // or have 5 different draw point functions?


	/**
	 * Draws a given shape.
	 *
	 * @param g2
	 * @param s
	 * @param line
	 * @param fill
	 */
	public static void drawShape(Graphics2D g2, Shape s, Color line, Color fill){
		g2.setStroke(new BasicStroke(1));
                g2.setPaint(line);
		g2.draw(s);
		g2.setPaint(fill);
		g2.fill(s);
	}

        public static void drawOutline(Graphics2D g2, Shape s, Color line, Color fill){
            g2.setPaint(fill);
            g2.fill(s);
            g2.setPaint(line);
            g2.draw(s);
        }

        public static void drawTri(Graphics2D g2, double centerX, double centerY, double d_size, Color line_col){

            Point2D.Double p1 = new Point2D.Double(centerX - ((d_size/2) * (Math.sqrt(3)/2)), centerY + (d_size/4));
            Point2D.Double p2 = new Point2D.Double(centerX, centerY - (d_size/2));
            Point2D.Double p3 = new Point2D.Double(centerX + ((d_size/2) * (Math.sqrt(3)/2)), centerY + (d_size/4));

            g2.setStroke(new BasicStroke(1));
            g2.setPaint(line_col);

            g2.draw(new Line2D.Double(p1, p2));
            g2.draw(new Line2D.Double(p2, p3));
            g2.draw(new Line2D.Double(p3, p1));
        }

        public static void drawPlus(Graphics2D g2, double centerX, double centerY, double d_size, Color line_col){
            g2.setPaint(line_col);
            g2.setStroke(new BasicStroke(1));

            g2.draw(new Line2D.Double(centerX - (d_size/2), centerY, centerX + (d_size/2), centerY));
            g2.draw(new Line2D.Double(centerX, centerY - (d_size/2), centerX, centerY + (d_size/2)));
            //g2.draw(outerCircle);


        }

        // draw a T
        // orient parameter defines orientation
        // 1: upright
        // 2: top on right
        // 3: top on bottom
        // 4: top on left
        public static void drawT(Graphics2D g2, double centerX, double centerY, double d_size, Color line_col, int orient){
            g2.setPaint(line_col);
            g2.setStroke(new BasicStroke(1));

            switch(orient){
                case 1:
                    g2.draw(new Line2D.Double(centerX - (d_size/2), centerY - (d_size/2), centerX + (d_size/2), centerY - (d_size/2)));
                    g2.draw(new Line2D.Double(centerX, centerY - (d_size/2), centerX, centerY + (d_size/2)));
                    
                    break;
                case 2:
                    
                    g2.draw(new Line2D.Double(centerX - (d_size/2), centerY, centerX + (d_size/2), centerY));
                    g2.draw(new Line2D.Double(centerX + (d_size/2), centerY - (d_size/2), centerX + (d_size/2), centerY + (d_size/2)));
                    break;
                case 3:
                    g2.draw(new Line2D.Double(centerX - (d_size/2), centerY + (d_size/2), centerX + (d_size/2), centerY + (d_size/2)));
                    g2.draw(new Line2D.Double(centerX, centerY - (d_size/2), centerX, centerY + (d_size/2)));
                    break;
                case 4:
                    g2.draw(new Line2D.Double(centerX - (d_size/2), centerY, centerX + (d_size/2), centerY));
                    g2.draw(new Line2D.Double(centerX - (d_size/2), centerY - (d_size/2), centerX - (d_size/2), centerY + (d_size/2)));
                    break;
            }

        }

        public static void drawX(Graphics2D g2, double centerX, double centerY, double d_size, Color line_col){
            g2.setPaint(line_col);
            g2.setStroke(new BasicStroke(1));
            double offset = (d_size/2) / (Math.sqrt(2));
            Point2D.Double p1 = new Point2D.Double(centerX - offset, centerY - offset);
            Point2D.Double p2 = new Point2D.Double(centerX + offset, centerY - offset);
            Point2D.Double p3 = new Point2D.Double(centerX + offset, centerY + offset);
            Point2D.Double p4 = new Point2D.Double(centerX - offset, centerY + offset);

            g2.draw(new Line2D.Double(p1, p3));
            g2.draw(new Line2D.Double(p2, p4));

        }

        public static void drawRing(Graphics2D g2, Shape s, Color line, int weight){
            g2.setPaint(line);
            g2.setStroke(new BasicStroke(weight));
            g2.draw(s);

        }

	/**
	 * Draws a circle.
	 * Now draws various styles of points (Ben, 4/10/11)
	 * @param g2
	 * @param size
	 * @param x
	 * @param y
	 * @param line
	 * @param fill
         * @param pointStyle
	 */
	public static void drawPoint(Graphics2D g2, double size, double x, double y, int lineCol, int fillCol, int pointStyle){

                // these are the actual centers (from distributions) but need to be adjusted depending
                // upon which style is used
                double x_cent = x + (size/2);
                double y_cent = y + (size/2);

                Shape outerCircle = new Ellipse2D.Double(x, y, size, size); // the outer portion of the dot
                Shape innerCircle = new Ellipse2D.Double(x + (size/4), y + (size/4), size/2, size/2); // the inner portion, if there is one

                Color empty = Color.white; // an extra color used to create the different styles
                Color currColor = Color.black;

                //Set the color according to condition
                switch(fillCol){
                    // 4 = light gray
                    // 3 = mid gray
                    // 2 = dark gray
                    // 1 = black
                    // 5 = mixed

                    case 4:
                        currColor = new Color(201,201,201);
                        break;
                    case 3:
                        currColor = new Color(145,145,145);
                        break;
                    case 2:
                        currColor = new Color(83,83,83);
                        break;
                    case 1:
                        currColor = new Color(0,0,0);
                        break;

                    // isoluminant colors for later
                    // colors
                    // 6: gray
                    // 7: red
                    // 8: green
                    // 9: blue
                    case 7:
                        currColor = new Color(217,13,13);
                        break;

                    case 8:
                        currColor = new Color(0,151,0);
                        break;

                    case 9:
                        currColor = new Color(81,81,253);
                        break;

                    case 6:
                        currColor = new Color(122,122,122);
                        break;

                    // alternative gray values, NOT USED
 //                   case 1:
   //                     currColor = new Color(191,191,191);
     //                   break;
       //             case 2:
         //               currColor = new Color(128,128,128);
           //             break;
//                    case 3:
  //                      currColor = new Color(63,63,63);
    //                    break;
      //              case 4:
        //                currColor = new Color(0,0,0);
          //              break;

                }

                // This switch statement draws each different type of point/dot/ring/etc.
                // 1: regular filled point
                // 2: regular unfilled (white) point
                // 3: white dot with black dot at center
                // 4: black dot with white dot at center
                switch(pointStyle){
                    case 1:
                        drawShape(g2, outerCircle, currColor, currColor);
                        break;

                    case 2:
                        drawOutline(g2, outerCircle, currColor, empty);
                        break;

                    case 3:
                        drawOutline(g2, outerCircle, currColor, empty);
                        drawShape(g2, innerCircle, currColor, currColor);
                        break;

                    case 4:
                        drawShape(g2, outerCircle, currColor, currColor);
                        drawShape(g2, innerCircle, empty, empty);
                        break;

//                  6: thin ring
//                  7: medium ring
//                  8: thick ring
//                  9: regular point

                    case 6:
                        Shape thinRing = new Ellipse2D.Double(x, y, size, size);
                        drawRing(g2, thinRing, currColor, 1);
                        break;

                    case 7:
                        Shape medRing = new Ellipse2D.Double(x, y, size, size);
                        drawRing(g2, medRing, currColor, 2);
                        break;

                    case 8:
                        Shape thickRing = new Ellipse2D.Double(x, y, size-2 , size-2 );
                        drawRing(g2, thickRing, currColor, 4 );
                        //drawRing(g2, outerCircle, currColor, 4);
                        break;

                    case 9:
                        drawShape(g2, outerCircle, currColor, currColor);
                        break;

//                  11: plusses
//                  12: upright T with geometric center
//                  13: right T with geometric center
//                  14: bottom T with geometric center
//                  15: left T with geometric center
//                  16: upright T with centroid
//                  17: right T with centroid
//                  18: bottom T with centroid
//                  19: left T with centroid

                    case 11:
                        Shape plusSignHoriz = new Line2D.Double(x_cent - (size/2), y_cent, x_cent + (size/2), y_cent);
                        Shape plusSignVert = new Line2D.Double(x_cent, y_cent - (size/2), x_cent, y_cent + (size/2));
                        //g2.draw(outerCircle);
                        g2.setPaint(currColor);
                        g2.draw(plusSignHoriz);
                        g2.draw(plusSignVert);
                        break;

                    case 12:

                        drawT(g2, x_cent, y_cent, size, currColor, 1);
                        break;

                    case 13:
                        drawT(g2, x_cent, y_cent, size, currColor, 2);
                        break;

                    case 14:
                        drawT(g2, x_cent, y_cent, size, currColor, 3);
                        break;

                    case 15:
                        drawT(g2, x_cent, y_cent, size, currColor, 4);
                        break;

                    case 16:
                        drawT(g2, x_cent, y_cent, size, currColor, 1);
                        break;

                    case 17:
                        drawT(g2, x_cent, y_cent, size, currColor, 2);
                        break;

                    case 18:
                        drawT(g2, x_cent, y_cent, size, currColor, 3);
                        break;

                    case 19:
                        drawT(g2, x_cent, y_cent, size, currColor, 4);
                        break;

//                    Symmetric geometric shapes
//                    21: ring
//                    22: triangle
//                    23: plus
//                    24: x
                    case 21:
                        Shape symmRing = new Ellipse2D.Double(x, y, size, size);
                        drawRing(g2, symmRing, currColor, 1);
                        break;

                    case 22:

                        drawTri(g2, x_cent, y_cent, size, currColor);
                        break;

                    case 23:
                        drawPlus(g2, x_cent, y_cent, size, currColor);
                        break;

                    case 24:
                        drawX(g2, x_cent, y_cent, size, currColor);
                        break;


                }

	}

        public static void drawPoint2(Graphics2D g2, double size, double x, double y, Color line, Color fill){
                Shape outerCircle = new Ellipse2D.Double(x, y, size, size);
                Shape innerCircle = new Ellipse2D.Double(x + size/4, y + size/4, size/2, size/2);

                drawShape(g2, outerCircle, line, fill);
              //  drawShape(g2, innerCircle, line, fill);

        }

	/**
	 * Draws an axis.
	 *
	 * @param g2
	 * @param startx
	 * @param endx
	 * @param starty
	 * @param endy
	 * @param line
	 */
	public static void drawAxis(Graphics2D g2, int startx, int endx, int starty, int endy, Color line){
		int ticsize = 3;
		int belowXaxis = starty - ticsize;
		int aboveXaxis = starty + ticsize;
		int leftYaxis = startx - ticsize;
		int rightYaxis = startx + ticsize;

		int ticX = 0;
		int xticRange = endx-startx;
		int xnumTics = 5;
		double xticDistance = xticRange/xnumTics;

		int ticY = 0;
		int yticRange = endy-starty;
		int ynumTics = 5;
		double yticDistance = yticRange/ynumTics;


		g2.setPaint(line);
		g2.drawLine(startx, starty, endx, starty);
		g2.drawLine(startx, starty, startx, endy);

//		//draw X axis tic marks
//		for(int tics = xnumTics;  tics > 0 ; tics--){
//			ticX = (int) xticDistance*tics;
//			g2.drawLine(ticX, belowXaxis, ticX, aboveXaxis);
//		}
//
//		//draw Y axis tic marks
//		for(int stics = ynumTics;  stics > 0 ; stics--){
//			ticY = (int) yticDistance*stics;
//			g2.drawLine(ticY, leftYaxis, ticY, rightYaxis);
//		}

	}

	/**
	 * Converts a local Y coordinate to a screen Y coordinate.
	 *
	 * @param res		the resolution of the screen's Y dimension
	 * @param orig		the origin of the local coordinate system (assuming global origin at bottom left corner of screen)
	 * @param coord		the coordinate of the object in local coordinates
	 * @return
	 */
	public static double locToScreenY(double orig, double coord, double res){
		return res-(orig+coord);
	}

	/**
	 * Converts a local X coordinate to a screen X coordinate.
	 *
	 * @param orig		the origin of the local coordinate system (assuming global origin at bottom left corner of screen)
	 * @param coord		the coordinate of the object in local coordinates
	 * @return
	 */
	public static double locToScreenX(double orig, double coord){
		return orig+coord;
	}

}