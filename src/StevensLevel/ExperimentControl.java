package StevensLevel;

import configuration.RoundDesign;
import StevensLevel.listeners.PartInteractionListener;
import StevensLevel.parts.ExperimentPart;
import StevensLevel.parts.BeginningPart;
import StevensLevel.parts.EndingPart;
import StevensLevel.parts.Round;
import configuration.ExperimentConfiguration;
import configuration.TaskDesign;
import interaction.InteractionReactor;
import java.util.ArrayList;
import java.util.List;
import screens.AbstractStrictScreen;
import static StevensLevel.EventBusHelper.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class ExperimentControl extends WithStateWithInteractionReactorImpl implements InteractionReactor, Runnable, PartInteractionListener {

    ViewControl vCon;
    List<Round> rounds;
    protected Sequence seq;
    private ExperimentConfiguration conf;
    private ExperimentPart currPart;
    /** Simple counter for keeping track how many parts have been played so far **/
    private int partsComplete = 0;

    //Has many TaskRounds (task within)
    public ExperimentControl() {
        setViewControl(new ViewControl());
    }

    //JavaBeans methods
    protected ViewControl getViewControl() {
        return vCon;
    }

    private void setViewControl(ViewControl vCon) {
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
        listen(this, PartInteractionListener.class);
        
        
        
        
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

        for (RoundDesign design : ec.getRoundDesigns()) {
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
        getPart().setup();
        getPart().run();
        setState(getPart().getState()); //ExperimentControl has the same state as the current part running. Logic should prevail as each part finishes and a new one
        //starts the control should take the state from the incoming part.. only as the final part completes will ExperimentControl complete as well.

        partsComplete++;//One more part complete
    }

    private void setPart(ExperimentPart get) {
        if (getPart() != null) {
            getPart().removeInteractionReactor(this); //
            this.removeInteractionReactor(getPart());
        }
        
        this.currPart = get;
        getPart().addInteractionReactor(this);
        this.addInteractionReactor(getPart());
    }

    @Override
    public void partComplete() {
        if(hasNextPart())
            nextPart();
        else
            exit();
    }

    @Override
    public void exit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Checks whether there is another ExperimentPart remaining in sequence to run.
     * @return 
     */
    private boolean hasNextPart() {
         return partsComplete < getSequence().size(); 
    }
}
