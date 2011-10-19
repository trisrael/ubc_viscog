/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import common.condition.DotHueType;
import common.condition.DotStyleType;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class Design{
    //defaults used for values
    public static int DEFAULT_INT = -1;
    public static double DEFAULT_DOUBLE = -1.0;
    public static String DEFAULT_STRING = "placeholder";
    
    protected int numTrials = DEFAULT_INT;
    private int numPoints = DEFAULT_INT; //Number of points to draw on graphs (if a graph is available)
    private String axisOn = DEFAULT_STRING;
    private String labelsOn=  DEFAULT_STRING; //Axis label     
    //Both of these return enum types to map to numbers using ConditionHelper
    DotStyleType dotStyle = DotStyleType.Uninitialized;
    DotHueType dotHue = DotHueType.Uninitialized;


    public String getAxisOn() {
        return axisOn;
    }

    public void setAxisOn(String axisOn) {
        this.axisOn = axisOn;
    }

    public String getLabelsOn() {
        return labelsOn;
    }

    public void setLabelsOn(String labelsOn) {
        this.labelsOn = labelsOn;
    }


    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }


    public int getNumTrials() {
        return numTrials;
    }

    public void setNumTrials(int numTrials) {
        this.numTrials = numTrials;
    }
   
    public DotHueType getDotHue() {
        return dotHue;
    }

    public void setDotHue(DotHueType dotHue) {
        this.dotHue = dotHue;
    }

    public DotStyleType getDotStyle() {
        return dotStyle;
    }

    public void setDotStyle(DotStyleType dotStyle) {
        this.dotStyle = dotStyle;
    }
    
}
