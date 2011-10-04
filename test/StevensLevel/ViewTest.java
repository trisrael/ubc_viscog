/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

import StevensLevel.events.StevensLevelInteraction;
import StevensLevel.listeners.ScreenUpdateListener;
import StevensLevel.parts.Round;
import StevensLevel.screens.TaskScreen;
import configuration.TaskDesign;
import java.awt.Panel;
import interaction.ExperimentInteraction;
import java.awt.Component;
import static java.awt.event.KeyEvent.*;
import javax.swing.JFrame;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import java.awt.event.KeyEvent;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static interaction.BasicInteraction.*;
import static StevensLevel.EventBusHelper.*;

/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class ViewTest {

    protected static View instance;
    private static JFrame comp;
    private static View spy;

    public static void setUp() {
        setupEventBus();
        instance = new View();
        spy = spy(instance);
        comp = new JFrame();
     
    }

    public static class Notifies {

        @Before
        public void setUp() {
            ViewTest.setUp();
        }
        
        
        @Test
        public void screenUpdatedCalledAfterRoundIsRun(){
           spy.setScreen(new TaskScreen());
           eb().removeListener(instance);
           listen(spy, ScreenUpdateListener.class);
           
            Round round = new Round(new TaskDesign());
            
            round.run();
            
            verify(spy, atMost(1)).screenUpdated();
        }
        
       

        /**
         * Test of keyTyped method, of class View.
         */
        @Test
        public void keyTyped() {
            System.out.println("keyTyped");
            spy.keyTyped(buildKeyEvent(KEY_TYPED, VK_UNDEFINED));
            shouldNotNotify(spy);
        }

        /**
         * Test of keyPressed method, of class View.
         */
        @Test
        public void keyPressed() {
            System.out.println("keyPressed");
            spy.keyPressed(buildKeyEvent(KEY_PRESSED, VK_V));
            shouldNotNotify(spy);
        }

        /**
         * Test of keyReleased method, of class View.
         */
        @Test
        public void incorrectKeyReleased() {
            testKeyReleased(VK_G, InvalidKeyPress);
        }

        @Test
        public void correlationUpKeyReleased() {
            testKeyReleased(VK_Z, StevensLevelInteraction.CorrelationDown);
        }

        @Test
        public void correlationDownKeyReleased() {
            testKeyReleased(VK_M, StevensLevelInteraction.CorrelationUp);
        }

        @Test
        public void finishedTaskReleased() {
            testKeyReleased(VK_ENTER, Complete);
        }

        //HELPERS
        /**
         * Ensure no notifications are sent out on interactions uninterested in
         * @param instance 
         */
        private void shouldNotNotify(View instance) {
            setNotify(instance, 0);
        }
        
   
        private void shouldNotify(View instance) {
            setNotify(instance, 1);
        }
        
        private void setNotify(View instance, int times){
            verify(instance, times(times)).sendReaction(Mockito.any(ExperimentInteraction.class));
        }

        /**
         * Ensure a particular key was released and received by View
         * @param keyCode, key press/typed/released (Type keyCode should away be VK_UNDEFINED)
         * @param shouldReceive, ExperimentInteraction that should be received by all observers
         */
        private void testKeyReleased(int keyCode, ExperimentInteraction shouldReceive) {
            System.out.println("keyReleased");
            spy.keyReleased(buildKeyEvent(KEY_RELEASED, keyCode));

            
            shouldNotify(spy);
        }
    }

    public static class AfterConstruct {

        @Before
        public void setUp() {
            ViewTest.setUp();
        }

     
    }

    public static class PhysicalView {

        private Panel pane;

        public class TestFrame extends JFrame {

            public void processKey(KeyEvent ev) {
                processKeyEvent(ev);
            }
        }

        @Before
        public void setUp() {
            ViewTest.setUp();
            

            pane = new Panel();
            spy.container.add(pane);
            spy.container.addKeyListener(spy);
        }

        @Test
        public void hearsKeyReleased() {
            keyed(KEY_RELEASED, VK_V);
            verify(spy, times(1)).keyReleased(Mockito.any(KeyEvent.class));
        }

        @Test
        public void hearsKeyPressed() {
            keyed(KEY_PRESSED, VK_UNDEFINED);
            verify(spy, times(1)).keyPressed(Mockito.any(KeyEvent.class));
        }

        @Test
        public void hearsKeyTyped() {
            keyed(KEY_TYPED, VK_UNDEFINED);
            verify(spy, times(1)).keyTyped(Mockito.any(KeyEvent.class));
        }

        /** Touch key within 'Frame' **/
        private void keyed(int id, int keyCode) {
          
        }
    }

    //HELPERS
    /**
     * @param id, type of KeyEvent
     * @param keyCode, key press/typed/released (Type keyCode should away be VK_UNDEFINED)
     * @return 
     */
    private static KeyEvent buildKeyEvent(int id, int keyCode) {
        return buildKeyEvent(id, keyCode, comp);

    }

    private static KeyEvent buildKeyEvent(int id, int keyCode, Component src) {
        return new KeyEvent(src, id, 0, 0, keyCode);
    }
}
