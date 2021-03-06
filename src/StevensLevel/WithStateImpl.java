/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

/**
 *Implementation of WithState interface as abstract class for extension purposes
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 3, 2011
 */
public abstract class WithStateImpl implements WithState{
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
    
    /** State checking helpers **/
    
    public boolean inState(State state){
        return state == getState();
    }

    public boolean isWaiting() {
        return inState(State.WAITING);
    }
    
    public boolean isRunning(){
        return inState(State.IN_PROGRESS);
    }
    
    public boolean isStopped(){
        return inState(State.COMPLETE);
    
    }
    
    
}
