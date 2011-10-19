package util;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class which contains all information needed in order to draw a point on a canvas.
 */
public class PointData {

    public static PointData builder() {
        return new PointData();
    }
    double x;
    double y;
    Color pointColor;
    double xCenter;
    double yCenter;
    double size;
    private Graphics2D g;

    public PointData() {
    }

    PointData x(double x) {
        this.x = x;
        return this;
    }

    PointData y(double y) {
        this.y = y;
        return this;
    }

    public PointData graphics(Graphics2D g) {
        this.g = g;
        return this;
    }

    public PointData pointColor(Color color) {
        this.pointColor = color;
        return this;
    }

    public PointData xCenter(double center) {
        this.xCenter = center;
        return this;
    }

    public PointData yCenter(double center) {
        this.yCenter = center;
        return this;
    }

    public PointData size(double size) {
        this.size = size;
        return this;
    }

    //Getters
    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Color pointColor() {
        return pointColor;
    }

    public double yCenter() {
        return yCenter;
    }

    public double xCenter() {
        return xCenter;
    }

    public double size() {
        return size;
    }

    public Graphics2D graphics() {
        return g;
    }
}