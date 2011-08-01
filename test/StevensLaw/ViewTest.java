/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import java.awt.Panel;
import interaction.ExperimentInteraction;
import interaction.ExperimentInteractionProducer;
import java.awt.Component;
import static java.awt.event.KeyEvent.*;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.awt.event.KeyEvent;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static interaction.BasicInteraction.*;

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
        instance = new View();
        spy = spy(instance);
        comp = new JFrame();
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
    
    private static KeyEvent buildKeyEvent(int id, int keyCode, Component src)
    {
         return new KeyEvent(src, id, 0, 0, keyCode);
    }
    
    public static class ListeningClassReceives {

        @Before
        public void setUp() {
            ViewTest.setUp();
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
            testKeyReleased(VK_Z, GraphInteraction.CorrelationDown);
        }

        @Test
        public void correlationDownKeyReleased() {
            testKeyReleased(VK_M, GraphInteraction.CorrelationUp);
        }

        @Test
        public void finishedTaskReleased() {
            testKeyReleased(VK_ENTER, TaskCompleted);
        }

        //HELPERS
        /**
         * Ensure no notifications are sent out on interactions uninterested in
         * @param instance 
         */
        private void shouldNotNotify(ExperimentInteractionProducer instance) {
            verify(instance, times(0)).notifyListeners(Mockito.any(ExperimentInteraction.class));
        }

        /**
         * @param keyCode, key press/typed/released (Type keyCode should away be VK_UNDEFINED)
         * @param shouldReceive, ExperimentInteraction that should be received by all observers
         */
        private void testKeyReleased(int keyCode, ExperimentInteraction shouldReceive) {
            System.out.println("keyReleased");
            spy.keyReleased(buildKeyEvent(KEY_RELEASED, keyCode));

            verify(spy, times(1)).notifyListeners(shouldReceive);
        }
    }

    public static class AfterConstruct {

        @Before
        public void setUp() {
            ViewTest.setUp();
        }

        @Test
        public void hasNoCurrentScreen() {
            assertNull("Should wait until a set screen/update screene vent before adding a screen to show", instance.getScreen());
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
            spy.container = new TestFrame();
            
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
            ((TestFrame) spy.container).processKey(buildKeyEvent(id, keyCode, pane));
        }
    }
}
