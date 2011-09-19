/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import interaction.ReactTo;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mockito.Mockito;


import interaction.BasicInteraction;
import interaction.InteractionReactor;
import interaction.InteractionReactorReflection;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static interaction.InteractionReactorReflection.*;



import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 14, 2011
 */
public class InteractionReactorTest extends TestBase{
    
    @Before
    public void setup(){
        resetExecutor();
    }
    
    @Test
    public void invokesMethod(){
        ExperimentControl xp = spy(new ExperimentControl());
        View view = xp.getViewControl().getView();
        xp.getViewControl().addInteractionReactor(xp);
      
         view.sendReaction(BasicInteraction.Continue);
         verify(xp).basicInteractionOccurred(Mockito.any(BasicInteraction.class), Mockito.any(BasicInteraction.class));
         
    } 
    
    @Test
    public void invokesOnAncestorAndNoMiddleReactions(){
        Controller upmost = new Controller();
        Controlled middle = new Controlled();
        SuperControlled low = new SuperControlled();
        
        low.addInteractionReactor(middle);
        middle.addInteractionReactor(upmost);
        
        low.sendReaction(BasicInteraction.Continue);
        finishWork();
        
        assertTrue(upmost.wasInvoked());
    }
    
    @Test
    public void invokesReactionOnControlled(){
        Controller con = new Controller();
        reactSingle(new HashSet<InteractionReactor>(), con, BasicInteraction.Continue, null);
        
        finishWork();
        
        assertTrue(con.wasInvoked());
    }
    
    @Test
    public void invokesOnAncestorFromChild(){
        Controller upmost = new Controller();
        Controlled middle = new Controlled();
        middle.addInteractionReactor(upmost);
        
        middle.sendReaction(BasicInteraction.Continue);
        finishWork();
        
        assertTrue(upmost.wasInvoked());
    }
    
    @Test
    public void ancestorContainsReactToMethod(){
        Method meth = null;
        try {
             meth = Controller.class.getMethod("reactThis", BasicInteraction.class, Object.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InteractionReactorTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(InteractionReactorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertThat(InteractionReactorReflection.retrieveReactTos(Controller.class, BasicInteraction.class), contains(meth));
    }
  
    
    public class Controller extends WithInterationReactorImpl implements InteractionReactor{
        private boolean w_inv = false;

        public boolean wasInvoked() {
            return w_inv;
        }

        public void setWasInvoked(boolean wasInvoked) {
            this.w_inv = wasInvoked;
        }
       @ReactTo(BasicInteraction.class)
       public void reactThis(BasicInteraction in, Object obj) {
           setWasInvoked(true);
       }
             
    }
    
    
    public class Controlled extends WithInterationReactorImpl implements InteractionReactor{
        
    }
    
    public class SuperControlled extends WithInterationReactorImpl{
        
    }
    
    
}
