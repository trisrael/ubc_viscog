package configuration;

import java.util.List;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 13, 2011
 */
public class StevensLevelDesign extends Design{
   private List<RoundDesign> sequential; //RoundConditions that will keep their sequential ordering
   private List<RoundDesign> counterbalanced; //Conditions which will be rearranged dependent on some factor
   private TaskDesign design;

    public void setDesign(TaskDesign design) {
        this.design = design;
    }

    public List<RoundDesign> getCounterbalanced() {
        return counterbalanced;
    }

    public void setCounterbalanced(List<RoundDesign> counterbalanced) {
        this.counterbalanced = counterbalanced;
    }

    public List<RoundDesign> getSequential() {
        return sequential;
    }

    public void setSequential(List<RoundDesign> sequential) {
        this.sequential = sequential;
    }

    TaskDesign getDesign() {
        return design;
    }
}
