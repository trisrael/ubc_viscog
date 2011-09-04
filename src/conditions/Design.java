/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conditions;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class Design {
    
    // ** Member Variables
    
    private int totalDots;
    private Class dotShape;
    private String dotHue; // May be a string (e.g. "black" or a hex tag for a color)
    private int dotWidth;
    private int pointShape;

    public String getDotHue() {
        return dotHue;
    }

    public void setDotHue(String dotHue) {
        this.dotHue = dotHue;
    }

    public Class getDotShape() {
        return dotShape;
    }

    public void setDotShape(Class dotShape) {
        this.dotShape = dotShape;
    }

    public int getDotWidth() {
        return dotWidth;
    }

    public void setDotWidth(int dotWidth) {
        this.dotWidth = dotWidth;
    }

    public int getPointShape() {
        return pointShape;
    }

    public void setPointShape(int pointShape) {
        this.pointShape = pointShape;
    }

    public int getTotalDots() {
        return totalDots;
    }

    public void setTotalDots(int totalDots) {
        this.totalDots = totalDots;
    }
    
    
    
    
    
    
}
