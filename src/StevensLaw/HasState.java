/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 3, 2011
 */
public abstract class HasState implements WithState{
    private State state = State.WAITING;

    public State getState() {
        return state;
    }

    protected void setState(State state) {
        if(this.state == State.COMPLETE)
        {
            throw new IllegalStateException("In 'COMPLETE' state, attempt to change state disallowed.");
        }
        
        this.state = state;
    }
    
}
