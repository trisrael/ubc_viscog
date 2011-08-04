/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 3, 2011
 */
public class StevensLevel {

    private double correlationLevel;

    public StevensLevel(double startPoint) {
        this.correlationLevel = startPoint;
    }

    public void stepDown() {
        this.correlationLevel -= 0.03;
    }

    public void stepUp() {
        this.correlationLevel += 0.03;
    }

    public double getLevel() {
        return this.correlationLevel; 
    }

    public void resetLevel(double level) {
        this.correlationLevel = level;
    }
}
