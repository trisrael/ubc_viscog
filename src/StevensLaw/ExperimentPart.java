/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import java.lang.reflect.TypeVariable;
import screens.AbstractStrictScreen;

/**
 * Represents a part/fragment of an Experiment, an Experiment like a presentation is broken up into different groups of 'parts'. Each runnable as long as in correct state.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 4, 2011
 */
public abstract class ExperimentPart<ScreenClass extends AbstractStrictScreen> extends HasState implements Runnable{
    
    Class<ScreenClass> getScreenClass(){
        TypeVariable<?>[] typePars = this.getClass().getTypeParameters();
        if(typePars.length > 0 ){
            return (Class<ScreenClass>) (typePars[0]).getClass();
        }
        
        return null;
        
    }
    
    public void run(){
        if(getState() == State.COMPLETE){ throw new RuntimeException("Attempted to run ExperimentPart when in complete stage");}
        
        
    }
}
