/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;
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
    private TaskDesign[] designs = new TaskDesign[0];
    private Design baseDesign = new Design();

    public Design getBaseDesign() {
        return baseDesign;
    }

    public void setBaseDesign(Design baseDesign) {
        this.baseDesign = baseDesign;
    }

    public TaskDesign[] getDesigns() {
        return designs;
    }

    public void setDesigns(TaskDesign[] designs) {
        this.designs = designs;
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
        if(designs.length == 0)
            addDefaultDesign();
        
        
        for (int i = 0; i < designs.length; i++) {
            TaskDesign des = designs[i];
            des.setBaseDesign(this.baseDesign);
        }
        
        
        
    }
    
    private void addDefaultDesign(){
        TaskDesign[] darr = new TaskDesign[1];
        darr[0] =  new TaskDesign();
        darr[0].setBaseDesign(getBaseDesign());
        setDesigns(darr);
    }
    
}
