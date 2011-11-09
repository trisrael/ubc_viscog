/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;
import StevensLevel.filesystem.FileSystemConstants;
import common.condition.DotHueType;
import common.condition.DotStyleType;
import common.counterbalancing.CounterBalancedOrdering;
import common.counterbalancing.CounterBalancedOrdering.NotEnoughPermutations;
import common.filesystem.FileSystem;
import experiment.Subject;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.SizeLimitExceededException;
import javax.swing.filechooser.FileNameExtensionFilter;
import static util.InferenceUtil.*;
import static sun.misc.Sort.*;
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
    private List<RoundDesign> finalRounds;

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
        cond.setPoints(200);
        cond.setGroups(4);
        cond.setAxisOn("true");
        cond.setStepLevel(0.03);
        cond.setBaseDesign(baseDesign);
        
        li.add(cond);
        
        design.setSequential(li);
    }

    /**
     * Get the resulting RoundDesigns that this configuration implies.
     * @return 
     */
    public List<RoundDesign> getRoundDesigns() {
       return getFinalRounds();
    }

    /**
     * Given a Subject, attempt to counter balance the rounds available if necessary
     * @param subject 
     */
    public void counterbalance(Subject subject) {
        List<RoundDesign> newordered = null;
        RoundDesign val;
        if(needsCounterBalance()){
            int numGroups = getDesign().getDesign().intprop("groups");
            try {
                newordered = CounterBalancedOrdering.reorder(getDesign().getCounterbalanced(), subject.getNumber(),  numGroups);
            } catch (SizeLimitExceededException ex) {
                Logger.getLogger(ExperimentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotEnoughPermutations ex) {
                Logger.getLogger(ExperimentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        List<RoundDesign> seqs = getDesign().getSequential();
        
        for (int i = 0; i < seqs.size(); i++) {
            val = seqs.get(i);
                    val.merge(newordered.get(i));
            seqs.set(i, val);
        }
            setFinalRounds(seqs);
        }else{
            setFinalRounds(getDesign().getSequential()); //Simply take the sequential in this case (hopefully its not null or empty, but it certainly may be)
            
        }
        
       addAncestorDesign();
    }

    /**
     * Decides whether the StevensLevelDesign needs to be counter balanced. Explanation of times not needing to counterbalance.
     * 1) List is null or empty
     * 2) (1) && either Sequential is empty or null OR the two lists (sequential and counterbalanced) are of the same size 
     * 
     * @return 
     */
    private boolean needsCounterBalance() {
       return getDesign().getCounterbalanced() != null && !getDesign().getCounterbalanced().isEmpty() && ((getDesign().getSequential() == null || getDesign().getSequential().isEmpty()) || getDesign().getSequential().size() == getDesign().getCounterbalanced().size());
    }
    
    
    public List<RoundDesign> getFinalRounds() {
        return finalRounds;
    }

    private void setFinalRounds(List<RoundDesign> finalRounds) {
        this.finalRounds = finalRounds;
    }

    /**
     * Add an ancestor RoundDesign to each RoundDesign found with final rounds. At this point the List should have been initialized with 
     * its final RoundDesign list
     * @return 
     */
    private List<RoundDesign> addAncestorDesign() {
        List<RoundDesign> seqs = getFinalRounds();
        for (RoundDesign roundDesign : seqs) {
            roundDesign.setBaseDesign(getDesign().getDesign());
        }
        
        return seqs;
    }
   
    
}
