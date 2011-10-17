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
    protected int numTrials = 4;
    private int numRounds = 1;
    private int numPoints = 200; //Number of points to draw on graphs (if a graph is available)
    private boolean axisOn = true;
    private boolean labelsOn = false; //Axis label     
    //Both of these return enum types to map to numbers using ConditionHelper
    DotStyleType dotStyle;
    DotHueType dotHue;


    public boolean isAxisOn() {
        return axisOn;
    }

    public void setAxisOn(boolean axisOn) {
        this.axisOn = axisOn;
    }

    public boolean isLabelsOn() {
        return labelsOn;
    }

    public void setLabelsOn(boolean labelsOn) {
        this.labelsOn = labelsOn;
    }


    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
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
