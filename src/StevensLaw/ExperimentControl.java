/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.parts.BeginningPart;
import StevensLaw.parts.EndingPart;
import StevensLaw.parts.Round;
import interaction.ExperimentInteractionEvent;
import interaction.ExperimentInteractionListener;
import java.util.ArrayList;
import java.util.List;
import screens.AbstractStrictScreen;
import static StevensLaw.State.*;


/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ExperimentControl extends WithStateImpl implements ExperimentInteractionListener, Runnable {

    ViewControl vCon;
    List<Round> rounds;
    protected Sequence seq;
   
    //Has many TaskRounds (task within)
    public ExperimentControl() {
        vCon = new ViewControl();
    }

    @Override
    public void interactionOccured(ExperimentInteractionEvent ev) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //JavaBeans methods
    public ViewControl getViewControl() {
        return vCon;
    }

    public void setViewControl(ViewControl vCon) {
        this.vCon = vCon;
    }

    public List<Round> getTaskRounds() {
        if (rounds == null) {
            rounds = new ArrayList<Round>();
        }
        return rounds;
    }

    public boolean addTaskRound(Round rnd) {
        return getTaskRounds().add(rnd);
    }
    
    //Get ExperimentControl ready for running
    public void setup(){
       addPart(new BeginningPart());
       
       //From configuration add in  middle bits
       
       addPart(new EndingPart());
    }
    
    protected Sequence getSequence(){
        if (seq == null){ seq = new Sequence(); };
        return seq;
    }
    
    protected void setSequence(Sequence seq) {
        this.seq = seq;
    }

    //Run experiment
    @Override
    public void run() {
        setup();
    }

    void addPart(ExperimentPart<?> part) {
        Sequence loc_seq = getSequence();
        
        loc_seq.add(loc_seq.size(), part);
        
    }
    
    ExperimentPart getPart(Class<? extends AbstractStrictScreen> clazz){
        for (ExperimentPart part : this.seq) {
            if(part.getScreenClass() == clazz){ return part;}
        }
        return null;
    }

 
    
}
