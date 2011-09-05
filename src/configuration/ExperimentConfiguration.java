/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

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
    private BaseDesign baseDesign = new BaseDesign();

    public BaseDesign getBaseDesign() {
        return baseDesign;
    }

    public void setBaseDesign(BaseDesign baseDesign) {
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
    
}
