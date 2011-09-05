/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class Design {
    
    // ** Member Variables
    
    private int totalDots;
    private PointShapeDrawer[] dotShapes;
    private String[] dotHues; // May be a string (e.g. "black" or a hex tag for a color)
    private int[] dotWidths;

    public String[] getDotHues() {
        return dotHues;
    }

    public void setDotHues(String[] dotHues) {
        this.dotHues = dotHues;
    }

    public PointShapeDrawer[] getDotShapes() {
        return dotShapes;
    }

    public void setDotShapes(PointShapeDrawer[] dotShapes) {
        this.dotShapes = dotShapes;
    }

    public int[] getDotWidths() {
        return dotWidths;
    }

    public void setDotWidths(int[] dotWidths) {
        this.dotWidths = dotWidths;
    }

    public int getTotalDots() {
        return totalDots;
    }

    public void setTotalDots(int totalDots) {
        this.totalDots = totalDots;
    }
}
