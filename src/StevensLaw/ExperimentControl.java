package StevensLaw;

import StevensLaw.parts.BeginningPart;
import StevensLaw.parts.EndingPart;
import StevensLaw.parts.Round;
import configuration.ExperimentConfiguration;
import configuration.Style;
import configuration.TaskDesign;
import interaction.BasicInteraction;
import interaction.ExperimentInteractionEvent;
import interaction.ExperimentInteractionListener;
import interaction.InteractionReactor;
import interaction.ReactTo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import screens.AbstractStrictScreen;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ExperimentControl extends WithStateWithInteractionReactorImpl implements InteractionReactor, Runnable {

    ViewControl vCon;
    List<Round> rounds;
    protected Sequence seq;
    private ExperimentConfiguration conf;
    private ExperimentPart currPart;
    /** Simple counter for keeping track how many parts have been played so far **/
    private int partsComplete = 0;

    //Has many TaskRounds (task within)
    public ExperimentControl() {
        vCon = new ViewControl();
        vCon.setInteractionReactor(this);
        conf = new ExperimentConfiguration();
    }

    //JavaBeans methods
    public ViewControl getViewControl() {
        return vCon;
    }

    public void setViewControl(ViewControl vCon) {
        this.vCon = vCon;
    }

    public List<Round> getRounds() {
        if (rounds == null) {
            rounds = new ArrayList<Round>();
        }
        return rounds;
    }

    public boolean addRound(Round rnd) {
        return getRounds().add(rnd);
    }

    //Get ExperimentControl ready for running
    public void setup() {
        addPart(new BeginningPart());

        addTasks();

        addPart(new EndingPart());
    }

    protected Sequence getSequence() {
        if (seq == null) {
            seq = new Sequence();
        };
        return seq;
    }

    protected void setSequence(Sequence seq) {
        this.seq = seq;
    }

    //Run experiment
    @Override
    public void run() {
        setState(State.WAITING); //waiting for first part to begin

        getConfiguration().ready();
        setup();

        nextPart();
    }

    void addPart(ExperimentPart<?> part) {
        Sequence local_sequence = getSequence();
        local_sequence.add(local_sequence.size(), part);
    }

    ExperimentPart getPart(Class<? extends AbstractStrictScreen> clazz) {
        for (ExperimentPart part : this.seq) {
            if (part.getScreenClass() == clazz) {
                return part;
            }
        }
        return null;
    }

    /** \\ Configuration */
    void setConfiguration(ExperimentConfiguration exc) {
        this.conf = exc;
    }

    ExperimentConfiguration getConfiguration() {
        return this.conf;
    }

    private ExperimentPart getPart() {
        return this.currPart;
    }

    /**
     * Adds tasks to be performed by users
     */
    private void addTasks() {
        ExperimentConfiguration ec = getConfiguration();

        for (TaskDesign design : ec.getDesigns()) {
            addPart(new Round(design));
        }
    }

    /**
     * Run the next part in the sequence, listen to events from it and pass on messages to the view control
     */
    private void nextPart() {
        if (getPart() != null) {
            getPart().stop();
            
        }
        setPart(getSequence().get(this.partsComplete));
        getPart().run();
        setState(getPart().getState()); //ExperimentControl has the same state as the current part running. Logic should prevail as each part finishes and a new one
        //starts the control should take the state from the incoming part.. only as the final part completes will ExperimentControl complete as well.

        partsComplete++;//One more part complete
    }

    private void setPart(ExperimentPart get) {
        if (getPart() != null) {
            getPart().setInteractionReactor(null); //
        }
        this.currPart = get;
        getPart().setInteractionReactor(this);
    }
    
    //Interaction reactions.
    @ReactTo(BasicInteraction.class)
    private void basicInteractionOccurred(Enum e) {
        try {
            switch ((BasicInteraction) e) {
                case Continue:
                     
                    break;
                case Complete:
                    break;
                case Exit:
                    break;
            }
        } catch (Exception ex) {
        }
    }
}
