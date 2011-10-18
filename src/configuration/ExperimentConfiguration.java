/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;
import StevensLevel.filesystem.FileSystemConstants;
import common.condition.DotHueType;
import common.condition.DotStyleType;
import common.filesystem.FileSystem;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    
    public ExperimentConfiguration(){
       
    }
    
    // All member variables start with defaults
    
    private Style style = new Style();
    private StevensLevelDesign design;

    public StevensLevelDesign getDesign() {
        return design;
    }

    public void setDesign(StevensLevelDesign design) {
        this.design = design;
    }
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
    
    public void setDefaultDesign(){ //Used for testing
        design = new StevensLevelDesign();
        design.setDesign(getBaseDesign());
        List<RoundDesign> li = new ArrayList<RoundDesign>();
        RoundDesign cond = new RoundDesign();
        cond.setLabelsOn("true");
        cond.setLowCorr(0.0);
        cond.setHighCorr(1.0);
        cond.setDotHue(DotHueType.IsoRed);
        cond.setDotStyle(DotStyleType.MedRing);
        cond.setNumPoints(200);
        cond.setNumTrials(4);
        cond.setAxisOn("true");
        cond.setStepLevel(0.03);
        cond.setBaseDesign(baseDesign);
        
        li.add(cond);
        
        design.setSequential(li);
    }

    public Iterable<RoundDesign> getRoundDesigns() {
        List<RoundDesign> seqs = design.getSequential();
        for (RoundDesign roundDesign : seqs) {
            roundDesign.setBaseDesign(design.getDesign());
        }
        
        return seqs;
    }
    
    
   
    
}
