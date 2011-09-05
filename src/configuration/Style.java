/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

/**
 * Base Styling for Experiments/Graphs
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class Style {
    
    private String axes = "normal"; //normal or reduced
    private String backgroundColor = "white";
    private boolean tics = false; //true or false
    private String startTitle;

    public String getAxes() {
        return axes;
    }

    public void setAxes(String axes) {
        this.axes = axes;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getStartTitle() {
        return startTitle;
    }

    public void setStartTitle(String startTitle) {
        this.startTitle = startTitle;
    }

    public boolean isTics() {
        return tics;
    }

    public void setTics(boolean tics) {
        this.tics = tics;
    }
    
    
}
