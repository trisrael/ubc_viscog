/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

/**
 *
 * @author tristangoffman
 */
public interface WithState {
    public State getState();
    //Returns whether instance is state given as parameter
    public boolean inState(State state);
}
