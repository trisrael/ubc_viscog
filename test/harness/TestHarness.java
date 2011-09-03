/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harness;

import org.mockito.Mockito;
import static org.mockito.Mockito.spy;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 11, 2011
 */
public abstract class TestHarness<T> {
    protected T instance;
    private T spy;
    
    public void setInstance(T inst){
        instance = inst;        
    }
    
    public T obj(){
        return getInstance();
    }
    
    public T spy(){
        if(spy == null){ spy = Mockito.spy(getInstance());}
        
        return spy;
    }
    
    public T obj(T inst){
        setInstance(inst);
        return obj();
    }
    
    public T getInstance(){
        return instance;
    }
}
