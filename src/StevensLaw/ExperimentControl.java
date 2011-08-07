/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.ExperimentInteractionEvent;
import interaction.ExperimentInteractionListener;
import java.util.ArrayList;
import java.util.List;
import static StevensLaw.State.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ExperimentControl extends HasState implements ExperimentInteractionListener, Runnable {

    ViewControl vCon;
    List<TaskRound> rounds;
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

    public List<TaskRound> getTaskRounds() {
        if (rounds == null) {
            rounds = new ArrayList<TaskRound>();
        }
        return rounds;
    }

    public boolean addTaskRound(TaskRound rnd) {
        return getTaskRounds().add(rnd);
    }
    
    //Get ExperimentControl ready for running
    public void setup(){
       
        
    }
    
    protected Sequence getSequence(){
        if (seq == null){ seq = new Sequence(); };
        return seq;
    }
    
    

    //Run experiment
    @Override
    public void run() {
        setup();
    }
    
}
