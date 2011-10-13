/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.util.List;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class StevensLevelDesign extends Design{
    List<RoundCondition> sequentialorder; //RoundConditions that will keep their sequential ordering
    List<RoundCondition> mixedorder; //Conditions which will be rearranged dependent on some factor

    public List<RoundCondition> getMixedorder() {
        return mixedorder;
    }

    public void setMixedorder(List<RoundCondition> mixedorder) {
        this.mixedorder = mixedorder;
    }

    public List<RoundCondition> getSequentialorder() {
        return sequentialorder;
    }

    public void setSequentialorder(List<RoundCondition> sequentialorder) {
        this.sequentialorder = sequentialorder;
    }
}
