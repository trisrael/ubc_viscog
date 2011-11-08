package render;

import common.condition.DotHueType;
import common.condition.DotStyleType;
import configuration.TaskDesign;

/**
 *An object containing stylings for a particularly graph, non-data related only aesthetics
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 17, 2011
 */
public class GraphStyleSheet {
    private boolean axisOn;
    private boolean labelsOn;
    private DotHueType dotHue;
    private DotStyleType dotStyle;
    private Double pointSize;
    private Double dotScaling;

    
    public GraphStyleSheet(TaskDesign des){
        setAxisOn(des.prop("axisOn", Boolean.class));
        setLabelsOn(des.prop("labelsOn", Boolean.class));
        setDotHue(des.prop("dotHue", DotHueType.class));
        setDotStyle(des.prop("dotStyle", DotStyleType.class));
        setDotSize(des.doubprop("dotSize"));
        setDotScaling(des.doubprop("dotScaling"));
    }

    public boolean isAxisOn() {
        return axisOn;
    }

    private void setAxisOn(boolean axisOn) {
        this.axisOn = axisOn;
    }

    public DotHueType getDotHue() {
        return dotHue;
    }

    private void setDotHue(DotHueType dotHue) {
        this.dotHue = dotHue;
    }

    public DotStyleType getDotStyle() {
        return dotStyle;
    }

    private void setDotStyle(DotStyleType dotStyle) {
        this.dotStyle = dotStyle;
    }

    public boolean isLabelsOn() {
        return labelsOn;
    }

    private void setLabelsOn(boolean labelsOn) {
        this.labelsOn = labelsOn;
    }
    
    
    public double getDotScaling() {
        return dotScaling;
    }

    public void setDotScaling(double dotScaling) {
        this.dotScaling = dotScaling;
    }

    public double getDotSize() {
        return pointSize;
    }

    public void setDotSize(double pointSize) {
        this.pointSize = pointSize;
    }
}
