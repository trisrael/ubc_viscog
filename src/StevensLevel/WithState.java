/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

/**
 *
 * @author tristangoffman
 */
public interface WithState {
    public State getState();
    //Returns whether instance is state given as parameter
    public boolean inState(State state);
    //Returns whether the object is in Running state
    public boolean isRunning();
    //Returns whether the object is in Stopped state
    public boolean isStopped();
}
