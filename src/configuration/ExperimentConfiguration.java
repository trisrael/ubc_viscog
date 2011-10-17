/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;
import common.condition.DotHueType;
import common.condition.DotStyleType;
import java.util.List;
import java.util.ArrayList;
import static util.InferenceUtil.*;
/**
 * Experiment configuration sets up the entire experiment.
 * 
 * BaseDesign contains the design configuration elements which TaskDesign should fall back to check when not found locally.
 * Style contains elements which effect the look of the graph outputted.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class ExperimentConfiguration {
    
    // All member variables start with defaults
    
    private Style style = new Style();
    private StevensLevelDesign design;
    private TaskDesign baseDesign = new TaskDesign();

    private TaskDesign getBaseDesign() {
        return baseDesign;
    }

    private void setBaseDesign(TaskDesign baseDesign) {
        this.baseDesign = baseDesign;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Configuration is about to be used in production. Ensure that it is cleaned based on
     * what is available at the moment.
     */
    public void ready() {
        if(design == null)
            setDefaultDesign();
    }
    
    private void setDefaultDesign(){ //Used for testing
        design = new StevensLevelDesign();
        design.setDesign(getBaseDesign());
        List<RoundDesign> li = new ArrayList<RoundDesign>();
        RoundDesign cond = new RoundDesign();
        cond.setLabelsOn(true);
        cond.setLowCorr(0.0);
        cond.setHighCorr(1.0);
        cond.setDotHue(DotHueType.IsoBlue);
        cond.setDotStyle(DotStyleType.Plus);
        cond.setBaseDesign(baseDesign);
        
        li.add(cond);
        
        design.setSequential(li);
    }

    public Iterable<RoundDesign> getRoundDesigns() {
        return design.getSequential();
    }
    
}
