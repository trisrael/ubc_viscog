package correlation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;
import util.DrawUtil;
import util.Globals;
import util.Util;
import xml.sax.XMLWriteable;

/**
 * A distribution of points.
 *
 * @author Will
 *
 */
public class Distribution2D implements XMLWriteable {

    protected LinkedList data;
    protected static final String objectVersion = "2009-04-29";
    // TODO: think about putting the label and axis drawing in a smarter place!
    protected boolean isDrawAxis = true;
    protected boolean isDrawLabels = true;

    public String getXMLString() {
        String temp = "\n";

        Iterator itr = data.iterator();
        while (itr.hasNext()) {
            Point2D.Double pt = (Point2D.Double) itr.next();
            if (pt != null) {
                temp = temp + "\t\t<point><x>" + pt.x + "</x><y>" + pt.y + "</y></point>\n";
            }
        }

        return temp;
    }

    public String getXMLSaveVersion() {
        return objectVersion;
    }

    public Distribution2D() {
        data = new LinkedList();
    }

    public Point2D.Double popLast() {
        if (data.size() == 0) {
            return null;
        }

        Point2D.Double temp = (Point2D.Double) data.getLast();
        data.removeLast();
        return temp;
    }

    public Point2D.Double popFirst() {
        if (data.size() == 0) {
            return null;
        }

        Point2D.Double temp = (Point2D.Double) data.getFirst();
        data.removeFirst();
        return temp;
    }

    public void pushFirst(Point2D.Double p) {
        data.addFirst(p);
    }

    public void pushLast(Point2D.Double p) {
        data.addLast(p);
    }

    /**
     * Returns the number of elements in this distribution.
     *
     * @return	number of elements
     */
    public int size() {
        return data.size();
    }

    /**
     * Returns the Pearson's correlation coefficient
     * of the current distribution.
     *
     * This method is O(size(distribution)) in time.
     *
     * @return	Pearson's R
     */
    public double getPearsonCorrelation() {
        double length = size();

        double result = 0;
        double sum_sq_x = 0;
        double sum_sq_y = 0;
        double sum_coproduct = 0;


        // TODO: re-write this waste of space using an iterator
        Object[] scores = data.toArray();

        double mean_x = ((Point2D.Double) scores[0]).x;
        double mean_y = ((Point2D.Double) scores[0]).y;
        for (int i = 2; i < length + 1; i += 1) {
            double sweep = ((double) i - 1.0) / (double) i;
            double delta_x = ((Point2D.Double) scores[i - 1]).x - mean_x;
            double delta_y = ((Point2D.Double) scores[i - 1]).y - mean_y;
            sum_sq_x += delta_x * delta_x * sweep;
            sum_sq_y += delta_y * delta_y * sweep;
            sum_coproduct += delta_x * delta_y * sweep;
            mean_x += delta_x / i;
            mean_y += delta_y / i;
        }
        double pop_sd_x = (double) Math.sqrt(sum_sq_x / length);
        double pop_sd_y = (double) Math.sqrt(sum_sq_y / length);
        double cov_x_y = sum_coproduct / length;
        result = cov_x_y / (pop_sd_x * pop_sd_y);
        return result;
    }

    // TODO: consider putting this functionality in a separate class
    public static void sort(Comparable[] input) {
        int gap = input.length;
        boolean swapped = true;
        while (gap > 1 || swapped) {
            if (gap > 1) {
                gap = (int) (gap / 1.3);
            }
            int i = 0;
            swapped = false;
            while (i + gap < input.length) {
                if (input[i].compareTo(input[i + gap]) > 0) {
                    Comparable t = input[i];
                    input[i] = input[i + gap];
                    input[i + gap] = t;
                    swapped = true;
                }
                i++;
            }
        }
    }

    public boolean nextReliableCorrelatedUniforms(double r, int size, double error) {
        if (size < 0 || error < 0) {
            return false;
        }
        data.clear();
        Vector yValues = new Vector();
        Vector xValues = new Vector();
        Random random = new Random();
        for (int e = 0; e < size; e++) { //create x values
            Double rand = new Double(Math.random());
            xValues.add(rand);
        }
        yValues = (Vector) xValues.clone();
        if (r != 1.0) {
            Collections.sort(xValues);
        }
        double trueR = Util.getPearsonCorrelation(vectorToDoubleArray(xValues), vectorToDoubleArray(yValues));
        while (Math.abs(trueR - r) > error) {
            if (trueR < r - error) {	// combsort11 for y
                int gap = yValues.size();
                boolean swapped = true;
                while (gap > 1 || swapped) {
                    if (gap > 1) {
                        gap = (int) (gap / 1.3);
                    }
                    int i = 0;
                    swapped = false;
                    while (i + gap < yValues.size() && trueR <= r - error) {
                        if (((Double) yValues.get(i)).compareTo((Double) yValues.get(i + gap)) > 0) {
                            Collections.swap(yValues, i, i + gap);
                            trueR = Util.getPearsonCorrelation(vectorToDoubleArray(xValues), vectorToDoubleArray(yValues));
                            swapped = true;
                        }
                        i++;
                    }
                }
            } else { // decorrelate
                while (trueR >= r + error) {
                    Collections.swap(yValues, random.nextInt(size - 1), random.nextInt(size - 1));
                    trueR = Util.getPearsonCorrelation(vectorToDoubleArray(xValues), vectorToDoubleArray(yValues));
                }
            }
        }
        for (int w = 0; w < yValues.size(); w++) {
            Point2D.Double temp = new Point2D.Double();
            temp.x = ((Double) xValues.get(w)).doubleValue();
            temp.y = ((Double) yValues.get(w)).doubleValue();
            this.pushFirst(temp);
        }
        return true;
    }

    public boolean turnIntoCorrelatedGaussian(double r, int size, double error) {
        if (size < 0 || error < 0) {
            return false;
        }
        data.clear();

        double meanTolerance = 2;
        double varLowTolerance = 0;
        double varHighTolerance = 2;
        double trueR = 10000;
        double meanX = 10000;
        double meanY = 10000;
        double varX = 10000;
        double varY = 10000;

        do {


            for (int n = 0; n < size; n++) {
                this.pushFirst(Util.nextCorrelatedGaussians(r));
            }

            do {
                this.popLast();
                this.pushFirst(Util.nextCorrelatedGaussians(r));
            } while (Math.abs(r - this.getPearsonCorrelation()) > error);


            meanX = getMeanX();
            meanY = getMeanY();
            varX = getVarianceX(meanX);
            varY = getVarianceY(meanY);

        } while (Math.abs(meanX) > meanTolerance
                || Math.abs(meanY) > meanTolerance
                || varX > varHighTolerance
                || varY > varHighTolerance);

        return true;
    }

    public boolean turnIntoTransformedCorrelatedGaussian(double r, int size, double error) {
        if (!turnIntoCorrelatedGaussian(r, size, error)) {
            return false;
        }

        linearTransform();

//~*~*~*~*~*~*~*~*  UNCOMMENT THIS LINE FOR THE DOUBLE Y CONDITION*~*~*~*~*~*~*~*~*
        //   scalePoints(0.5 , 1);
//~*~*~*~*~*~*~*~*  UNCOMMENT THIS LINE FOR THE DOUBLE Y CONDITION*~*~*~*~*~*~*~*~*
        this.TOTAL_POINTS = size;
        return true;
    }

    public Iterator iterator() {
        return data.iterator();
    }

    public String toString() {
        return data.toString();
    }

    public double[] vectorToDoubleArray(Vector v) {
        Iterator itr = v.iterator();
        double[] a = new double[v.size()];
        int count = 0;

        while (itr.hasNext()) {
            a[count] = ((Double) itr.next()).doubleValue();
            count++;
        }
        return a;
    }

    public double getMinX() {
        Iterator itr = data.iterator();
        double minVal = Double.POSITIVE_INFINITY;
        double currVal = Double.POSITIVE_INFINITY;

        while (itr.hasNext()) {
            currVal = ((Point2D.Double) itr.next()).x;
            if (currVal < minVal) {
                minVal = currVal;
            }
        }
        return minVal;
    }

    public double getMinY() {
        Iterator itr = data.iterator();
        double minVal = Double.POSITIVE_INFINITY;
        double currVal = Double.POSITIVE_INFINITY;

        while (itr.hasNext()) {
            currVal = ((Point2D.Double) itr.next()).y;
            if (currVal < minVal) {
                minVal = currVal;
            }
        }
        return minVal;
    }

    public double getMeanX() {
        Iterator itr = data.iterator();
        double total = 0;

        while (itr.hasNext()) {
            total += ((Point2D.Double) itr.next()).x;
        }
        return (total / size());
    }

    public double getMeanY() {
        Iterator itr = data.iterator();
        double total = 0;

        while (itr.hasNext()) {
            total += ((Point2D.Double) itr.next()).y;
        }
        return (total / size());
    }

    public double getVarianceX(double mean) {
        Iterator itr = data.iterator();
        double variance = 0;
        double n = 0;

        while (itr.hasNext()) {
            n = ((Point2D.Double) itr.next()).x;
            variance += (n - mean) * (n - mean);
        }
        return (variance / (size() - 1));
    }

    public double getVarianceY(double mean) {
        Iterator itr = data.iterator();
        double variance = 0;
        double n = 0;

        while (itr.hasNext()) {
            n = ((Point2D.Double) itr.next()).y;
            variance += (n - mean) * (n - mean);
        }
        return (variance / (size() - 1));
    }

    public double getMaxX() {
        Iterator itr = data.iterator();
        double minVal = Double.NEGATIVE_INFINITY;
        double currVal = Double.NEGATIVE_INFINITY;

        while (itr.hasNext()) {
            currVal = ((Point2D.Double) itr.next()).x;
            if (currVal > minVal) {
                minVal = currVal;
            }
        }
        return minVal;
    }

    public double getMaxY() {
        Iterator itr = data.iterator();
        double minVal = Double.NEGATIVE_INFINITY;
        double currVal = Double.NEGATIVE_INFINITY;

        while (itr.hasNext()) {
            currVal = ((Point2D.Double) itr.next()).y;
            if (currVal > minVal) {
                minVal = currVal;
            }
        }
        return minVal;
    }

    public Point2D.Double getMinCoords() {
        Iterator itr = data.iterator();
        Point2D.Double tmp;
        double minValX = Double.POSITIVE_INFINITY;
        double minValY = Double.POSITIVE_INFINITY;
        double currValX = Double.POSITIVE_INFINITY;
        double currValY = Double.POSITIVE_INFINITY;

        while (itr.hasNext()) {
            tmp = ((Point2D.Double) itr.next());
            currValX = tmp.x;
            currValY = tmp.y;
            if (currValX < minValX) {
                minValX = currValX;
            }
            if (currValY < minValY) {
                minValY = currValY;
            }
        }
        return new Point2D.Double(minValX, minValY);
    }

    public Point2D.Double getMaxCoords() {
        Iterator itr = data.iterator();
        Point2D.Double tmp;
        double maxValX = Double.NEGATIVE_INFINITY;
        double maxValY = Double.NEGATIVE_INFINITY;
        double currValX = Double.NEGATIVE_INFINITY;
        double currValY = Double.NEGATIVE_INFINITY;

        while (itr.hasNext()) {
            tmp = ((Point2D.Double) itr.next());
            currValX = tmp.x;
            currValY = tmp.y;
            if (currValX > maxValX) {
                maxValX = currValX;
            }
            if (currValY > maxValY) {
                maxValY = currValY;
            }
        }
        return new Point2D.Double(maxValX, maxValY);
    }

    public void translatePoints(double x, double y) {
        Point2D.Double temp;
        double tempx, tempy;
        Iterator itr = data.iterator();
        while (itr.hasNext()) {
            temp = (Point2D.Double) itr.next();
            tempx = temp.x;
            tempy = temp.y;
            temp.x = tempx + x;
            temp.y = tempy + y;
        }
    }

    public void scalePoints(double scalex, double scaley) {
        Point2D.Double temp;
        double tempx, tempy;
        Iterator itr = data.iterator();
        while (itr.hasNext()) {
            temp = (Point2D.Double) itr.next();
            tempx = temp.x;
            tempy = temp.y;
            temp.x = tempx * scalex;
            temp.y = tempy * scaley;
        }
    }

    public void oldLinearTransform() {
        // TODO: Investigate --> This algorithm strikes me as not preserving the Pearson's correlation
        //		 as it ends up performing a rectangular scaling

        // TODO: Add some sort of divide-by-zero exception handling

        Point2D.Double temp;
        double minx, miny, maxx, maxy;


        // Get the minimum coordinates
        temp = getMinCoords();
        minx = temp.x;
        miny = temp.y;

        // Shift all points so most minimum x-y pair is (0,0)
        this.translatePoints(-minx, -miny);

        // Get the maximum coordinates
        temp = getMaxCoords();
        maxx = temp.x;
        maxy = temp.y;

        // Scale so maximum x-y pair is at (1,1)
        this.scalePoints(1 / maxx, 1 / maxy);
    }

    public void linearTransform() {


        // TODO: Add some sort of divide-by-zero exception handling

        Point2D.Double temp;
        double minx, miny, maxx, maxy;


        // Get the minimum coordinates
        temp = getMinCoords();
        minx = temp.x;
        miny = temp.y;

        double min = Math.min(minx, miny);

        // Originally shifted all points so most minimum x-y pair was on a 0 axis
        // Now shifts all points so minimum x-y pair is at a 0.05 axis
        // (Emily made this change on 290710 because boundary points were not visible)
        //this.translatePoints(.05 - min, .05 - min);
        this.translatePoints(-minx, -miny);

        // Get the maximum coordinates
        temp = getMaxCoords();
        maxx = temp.x;
        maxy = temp.y;

        double max = Math.max(maxx, maxy);

        // Originally shifted all points so most maximum x-y pair was on a 1 axis
        // Now shifts all points so maximum x-y pair is on a 0.95 axis
        // (Emily made this change on 290710 because boundary points were not visible)
        //this.scalePoints(.95/max, .95/max);
        this.scalePoints(1 / maxx, 1 / maxy);
    }
    protected int TOTAL_POINTS = 200;
    protected int pointSizes[] = new int[TOTAL_POINTS];
    protected int pointStyles[] = new int[TOTAL_POINTS];
    protected int pointHues[] = new int[TOTAL_POINTS];

    // a method to fill array with proper sizes
    private void fillSizes(double currSize, int length) {

        switch ((int) currSize) {
            case 1:
                for (int i = 0; i < length; i++) {
                    pointSizes[i] = (int) currSize;
                }
                break;
            case 4:
                for (int i = 0; i < length; i++) {
                    pointSizes[i] = (int) currSize;
                }
                break;
            case 8:
                for (int i = 0; i < length; i++) {
                    pointSizes[i] = (int) currSize;
                }
                break;
            case 12:
                for (int i = 0; i < length; i++) {
                    pointSizes[i] = (int) currSize;
                }
                break;
            case 16:
                for (int i = 0; i < length; i++) {
                    pointSizes[i] = (int) currSize;
                }
                break;
            case 77:
                for (int l = 0; l < length; l = l + 4) {
                    pointSizes[l] = 1;
                    pointSizes[l + 1] = 4;
                    pointSizes[l + 2] = 8;
                    pointSizes[l + 3] = 16;
                    //pointSizes[l] = (l % 4) + 1;
                }
                break;
        }
    }

    // a method to fill the list of styles
    private void fillStyles(int type, int length) {
        switch (type) {
            case 5:
                for (int i = 0; i < length; i++) {
                    pointStyles[i] = (i % 4) + 1;
                }
                break;
            case 10:
                for (int l = 0; l < length; l++) {
                    pointStyles[l] = (l % 4) + 6;
                }
                break;
            // need a mix condition of 12, 13, 14, 15
            case 13:
                for (int i = 0; i < length; i++) {
                    pointStyles[i] = (i % 4) + 12;
                }
                break;
            // mix of 16,17,18,19
            case 14:
                for (int i = 0; i < length; i++) {
                    pointStyles[i] = (i % 4) + 16;
                }
                break;
            // mix of 11,12,13,14,15
            case 15:
                for (int i = 0; i < length; i++) {
                    pointStyles[i] = (i % 5) + 11;
                }
                break;
       
            case 25:
                for (int l = 0; l < length; l++) {
                    pointStyles[l] = (l % 4) + 21;
                }
                break;
            default:
                 for (int i = 0; i < length; i++) {
                    pointStyles[i] = type;
                }

        }

    }

    // a method to fill the hues
    private void fillHues(int hue, int length) {

        switch (hue) {
            case 5:
                for (int i = 0; i < length; i++) {
                    //pointHues[i] = 2;
                    pointHues[i] = (i % 4) + 1;
                }
                break;
            case 10:
                for (int i = 0; i < length; i++) {
                    pointHues[i] = (i % 4) + 6;
                }
                break;
            default:
                for (int i = 0; i < length; i++) {
                    pointHues[i] = hue;
                }
        }
    }

    public Image getScaledScatterSetImage(int width, int height, int Points, double scalingFactor, double pointSize, int pointStyle, int dotHue) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        double x, y;
        double origx = (width - width * scalingFactor) / 2;
        double origy = (height - height * scalingFactor) / 2;
        double sizex = width * scalingFactor;
        double sizey = height * scalingFactor;
        double resY = height;

        int currDotHue = dotHue;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background
        //g2.setColor(Color.BLACK);
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        //g2.fill(new Rectangle.Float(0,0,width,height));


        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        Iterator itr = data.iterator();

        fillSizes(pointSize, Points);
        fillHues(currDotHue, Points);
        fillStyles(pointStyle, Points);

        int counter = 0;
        while (itr.hasNext()) {

            Point2D.Double temp = (Point2D.Double) itr.next();
            x = temp.x * sizex;
            y = temp.y * sizey;
            DrawUtil.drawPoint(g2, pointSizes[counter], DrawUtil.locToScreenX(origx, x - (pointSizes[counter] / 2.0)), DrawUtil.locToScreenY(origy, y + (pointSizes[counter] / 2.0), resY), pointHues[counter], pointHues[counter], pointStyles[counter]);
            //DrawUtil.drawPoint(g2, pointSize, DrawUtil.locToScreenX(origx, x), DrawUtil.locToScreenY(origy,y,resY), Color.BLACK, Color.BLACK);
            counter++;

        }
        counter = 0;
        g2.dispose();
        return Util.toImage(bi);

    }

    public Image getImage(int width, int height, int numPoints, double scaling, double dpointSize, int iPointStyle, int idotHue) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        Font myFon = new Font("sanserif", Font.PLAIN, 12);
        String xlabel = "independent variable";
        String ylabel = "dependent variable";

        int axisPadding = 8;
        int graphOriginX = Util.getStringHeight(xlabel, myFon) + axisPadding;
        int graphOriginY = Util.getStringHeight(ylabel, myFon) + axisPadding;


        int graphHeight = height - graphOriginY;
        int graphWidth = width - graphOriginX;

        if (Globals.isDebug) {
            System.out.println("Height: " + height);
            System.out.println("Width: " + width);
            System.out.println("OrigX: " + graphOriginX);
            System.out.println("OrigY: " + graphOriginY);
            System.out.println("gHeight: " + graphHeight);
            System.out.println("gWidth: " + graphWidth);
        }


        // Background
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle.Float(0, 0, width, height));

        // Axis
        if (isDrawAxis) {
            DrawUtil.drawAxis(g2, graphOriginX, width, height - graphOriginY, height - height, Color.BLACK);
        }

        // Scatter set
        // Reminder: drawImage takes in the location of the top-left corner

        // *~*~*~*~*~*~   THIS IS WHAT YOU NEED TO CHANGE FOR THE GAUSSIAN CONDITIONS  ~*~*~**~*~*~*~*~
        // double scalingFactor = 0.685;  // uniform
        double scalingFactor = scaling;      // gaussian


        g2.drawImage(this.getScaledScatterSetImage(graphWidth, graphHeight, numPoints, scalingFactor, dpointSize, iPointStyle, idotHue),
                graphOriginX, 0, null);

        if (Globals.isDebug) {
            System.out.println((this.getScaledScatterSetImage(graphWidth, graphHeight, numPoints, scalingFactor, dpointSize, iPointStyle, idotHue)).getWidth(null));
            System.out.println((this.getScaledScatterSetImage(graphWidth, graphHeight, numPoints, scalingFactor, dpointSize, iPointStyle, idotHue)).getHeight(null));
        }

        // Text - horizontal
        g2.setColor(Color.BLACK);
        g2.setFont(myFon);
        if (isDrawLabels) {
            g2.drawString(xlabel, width / 2 - Util.getStringWidth(xlabel, myFon) / 2, height - graphOriginY / 2 + 1);
        }

        // Text - vertical
        g2.rotate(Math.PI / 2.0);
        if (isDrawLabels) {
            g2.drawString(ylabel, height / 2 - Util.getStringWidth(ylabel, myFon) / 2, -(graphOriginX / 2 + 1));
        }

        // Free resources
        g2.dispose();
        return Util.toImage(bi);
    }

    public void setDrawAxis(boolean isDrawAxis) {
        this.isDrawAxis = isDrawAxis;
    }

    public void setDrawLabels(boolean isDrawLabels) {
        this.isDrawLabels = isDrawLabels;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Distribution2D myDist = new Distribution2D();
        myDist.turnIntoCorrelatedGaussian(0.25, 100, 0.1);
        System.out.println(myDist.toString());
        myDist.translatePoints(20, -10);
        System.out.println(myDist.toString());
        myDist.scalePoints(100, 100);
        System.out.println(myDist.toString());
        myDist.linearTransform();
        System.out.println(myDist.toString());
        System.out.println(myDist.getXMLString());
    }
}
