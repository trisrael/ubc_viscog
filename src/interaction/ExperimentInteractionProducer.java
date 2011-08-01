package interaction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 25, 2011
 */
public abstract class ExperimentInteractionProducer implements Observable{
    private List<ExperimentInteractionListener> listeners = null;
    

    public void addListener(ExperimentInteractionListener lis) {
        checkListeners();
        listeners.add(lis);        
    }   
    
    private void checkListeners(){
          if(listeners == null){ listeners = new ArrayList<ExperimentInteractionListener>(); };              
    }
    
    public List<ExperimentInteractionListener> getListeners(){
        checkListeners();        
        return listeners;
    }
    
    protected boolean removeListeners(ExperimentInteractionListener... lis){        
        ArrayList<ExperimentInteractionListener> list = new ArrayList<ExperimentInteractionListener>();
        for (int i = 0; i < list.size(); i++) {
            list.add(lis[i]);
        }
        
        return listeners.removeAll(list);       
    }
    
    protected boolean removeListener(ExperimentInteractionListener lis){
        return listeners.remove(lis);
    }
    
    public void notifyListeners(ExperimentInteraction in){
        notifyListeners(new ExperimentInteractionEvent(in));
    }   
    
    /**
     * Notify each Observer with an ExperimentInteractionEvent that an interaction occurred.
     * @param interaction, the interaction that occurred  
     */
    public void notifyListeners(ExperimentInteractionEvent in){         
        
        for (ExperimentInteractionListener lis : getListeners()) {
            lis.interactionOccured(in);
        }
    }
}
