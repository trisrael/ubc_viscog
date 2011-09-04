/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.parts.EndingPart;
import StevensLaw.parts.BeginningPart;

import interaction.ExperimentInteractionListener;
import java.util.List;

import screens.AbstractStrictScreen;
import StevensLaw.parts.Round;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 *
 * @author tristangoffman
 */
@RunWith(Enclosed.class)
public class ExperimentControlTest extends TestBase {

    public static ExperimentControl ex;

    public ExperimentControlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public static void setUp() {
        ex = new ExperimentControl();
    }

    @After
    public void tearDown() {
    }

    public static class AfterConstruct {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
        }

        @Test
        public void isListener() {
            ExperimentInteractionListener.class.cast(new ExperimentControl());
        }

        @Test
        public void hasViewControl() {
            hasFieldWith(ViewControl.class);
        }
        
         @Test
        public void hasSequence() {
            assertThat(ex.getSequence(), notNullValue());
        }
         
       

        @Test
        public void viewControlInstantiated() {
            assertThat(ex.getViewControl(), notNullValue());
        }

        @Test
        public void hasTaskRounds() {
            assertThat(ex.getRounds(), notNullValue());
        }
        
          @Test
        public void addPart(){
            ex.addPart(new BeginningPart());
        }
    }
    
    public static class addPartExtras extends SetupWithoutConfiguration{
       
        
        @Test
        public void addPartOrderering(){
            BeginningPart add1 = new BeginningPart();
            EndingPart add2 = new EndingPart();
            ex.setSequence(new Sequence()); //Clean slate for order testing
            ex.addPart(add1);
            ex.addPart(add2);
            assertEquals(ex.getSequence().last(), add2 );
            assertEquals(ex.getSequence().first(), add1);
        }
    }

    public static class TaskRounds {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
        }

        @Test
        public void addTaskRound() {
            Round rnd = new Round(1.0, 0);
            ex.addRound(rnd);
            assertThat(ex.getRounds(), contains(rnd));
        }

        @Test
        public void addTaskRoundisLast() {
            Round rnd = new Round(1.0, 0);
            Round rnd2 = new Round(1.0, 0);
            ex.addRound(rnd);
            ex.addRound(rnd2);
            List<Round> rnds = ex.getRounds();
            assertThat(rnds.get(rnds.size() - 1), is(rnd2));
        }
    }

    public static class Configuration {
       
        @Test
        public void placeHolder(){
            
        } 
    }

    public static class SetupWithoutConfiguration extends AfterConstruct{

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
            ex.setup();
        }
        
        public Sequence setSeqAndSpy(ExperimentControl cont){
            Sequence seq = new Sequence();
            Sequence spy = spy(seq);
            cont.setSequence(spy);
            return spy;
        }
        
        @Test
        public void canSetSequence(){
            hasSequence();
            Sequence spy = setSeqAndSpy(ex);
            
            assertThat(ex.getSequence(), is(spy));
        }
        
        public class TestPart extends ExperimentPart<AbstractStrictScreen>{

            @Override
            public Class<AbstractStrictScreen> getScreenClass() {
                return AbstractStrictScreen.class;
            }
            
        }
        
        @Test
        public void addToSequence(){
            hasSequence();
            canSetSequence();
            Sequence spy = setSeqAndSpy(ex);
            TestPart part = new TestPart();
            ex.addPart(part);
            verify(spy , times(1)).add(ex.getSequence().size(), part);
        }
        
        @Test
        public void getPartByScreen(){
            addToSequence();
            ex.setSequence(new Sequence());
            
            TestPart part = new TestPart();
            ex.addPart(part);
            assertEquals(part, ex.getPart(AbstractStrictScreen.class));
        }
        
        @Test
        public void getPartByScreenNullWithNoCorrespondingPart(){
            getPartByScreen();
            
            ex.setSequence(new Sequence());
            assertEquals(null, ex.getPart(AbstractStrictScreen.class));
        }
        
        public void containsClass(Class<? extends ExperimentPart> clazz){
            boolean contains = false;
            for (ExperimentPart obj :  ex.getSequence()) {
                try {
                    clazz.cast(obj);
                    contains = true;
                } catch (Exception e) {
                }
                
            }
           assertTrue(contains);
        }
        
        @Test
        public void hasStartScreenInSequence(){
            containsClass(BeginningPart.class);        
        }

        @Test
        public void startScreenFirstInSequence() {
            assertTrue(ex.getSequence().first() instanceof BeginningPart);
        }

        @Test
        public void endScreenLastInSequence() {             
             assertTrue(ex.getSequence().last() instanceof EndingPart);
        }
    }

    public static class SetupWithConfiguration extends SetupWithoutConfiguration {

        @Before
        public void setUp() {
            ExperimentControlTest.setUp();
        }
        
        
    }
}
