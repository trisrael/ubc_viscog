/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ExperimentControl extends WithStateImpl implements ExperimentInteractionListener, Runnable{

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
        vCon.addListener(this);
        conf = new ExperimentConfiguration();
    }

    @Override
    public void interactionOccured(ExperimentInteractionEvent ev) {
       Enum e  = ev.getType();
       
       if(e == BasicInteraction.Continue && isWaiting()){
           this.nextPart();
       }
       
       
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
    public void setup(){
       addPart(new BeginningPart());
       
       addTasks();
       
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
        setState(State.WAITING); //waiting for first part to begin
        
        getConfiguration().ready();
        setup();
      
        
        nextPart();
        
    }

    void addPart(ExperimentPart<?> part) {
        Sequence local_sequence = getSequence();
        
        local_sequence.add(local_sequence.size(), part);
        
    }
    
    ExperimentPart getPart(Class<? extends AbstractStrictScreen> clazz){
        for (ExperimentPart part : this.seq) {
            if(part.getScreenClass() == clazz){ return part;}
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
    
    
    
    private Object styleInfer(String name, String pre, Object val) throws Exception{
        
        ExperimentConfiguration con = getConfiguration();
        Style stl = con.getStyle();
        Class<?>[] types;
        String frst = name.substring(0, 1);
        String frstUp = frst.toUpperCase();
        name = name.replaceFirst(frst, frstUp);
        Class[] clazz = new Class[1];
        
        if(val != null){
             clazz[0] = val.getClass(); 
        }
        else{
            clazz = null;
        }
                
       
        Method meth = stl.getClass().getMethod(pre + name , clazz);
        
        if( val != null){
           return meth.invoke(stl, val); 
        }else{
            return meth.invoke(stl);
        }
    }

    /**
     * Check underlying Style class if it has a setter method of the name and parameter class of value given, if so will set that value to the corresponding style key/name.
     * 
     * 
     * @param name, name of style (e.g. "startTitle" will look for a method named setStartTitle)
     * @param val, value to set style, the underlying class associated with this value will be checked against the method so ensure the correct value is passed
     */
    void setStyle(String name, Object val) {
  
        try {
            styleInfer(name, "set", val);
        }catch(Exception ex){
             Logger.getLogger(ExperimentControl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    /**
     * Return the value associated with the style in question or null when not found.
     * @param name
     * @return 
     */
    Object getStyle(String name){
        try {
            return styleInfer(name, "get", null);
        }catch(Exception ex){
             Logger.getLogger(ExperimentControl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }

    /**
     * Adds tasks to be performed by users
     */
    private void addTasks() {
        ExperimentConfiguration ec = getConfiguration();
        
        for (TaskDesign design: ec.getDesigns()) {
            addPart(new Round(design));
        }
    }
    
    
    

    /**
     * Run the next part in the sequence, listen to events from it and pass on messages to the view control
     */
    private void nextPart() {
        if(getPart() != null){
            getPart().stop();
        }
        setPart(getSequence().get(this.partsComplete));
        getPart().run();
        setState(getPart().getState()); //ExperimentControl has the same state as the current part running. Logic should prevail as each part finishes and a new one
        //starts the control should take the state from the incoming part.. only as the final part completes will ExperimentControl complete as well.
        
        partsComplete++;//One more part complete
    }

    private void setPart(ExperimentPart get) {
        if(getPart() != null)
            getPart().removeUIListener();
        this.currPart = get;
        getPart().setUIListener(getViewControl());
    }
    
    

 
    
}
