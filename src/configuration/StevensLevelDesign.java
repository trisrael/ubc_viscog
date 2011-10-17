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
   private List<RoundCondition> sequential; //RoundConditions that will keep their sequential ordering
   private List<RoundCondition> counterbalanced; //Conditions which will be rearranged dependent on some factor
  private  Design design;

    public void setDesign(Design design) {
        this.design = design;
    }

    public List<RoundCondition> getCounterbalanced() {
        return counterbalanced;
    }

    public void setCounterbalanced(List<RoundCondition> counterbalanced) {
        this.counterbalanced = counterbalanced;
    }

    public List<RoundCondition> getSequential() {
        return sequential;
    }

    public void setSequential(List<RoundCondition> sequential) {
        this.sequential = sequential;
    }
   


    Design getDesign() {
        return design;
    }
}
