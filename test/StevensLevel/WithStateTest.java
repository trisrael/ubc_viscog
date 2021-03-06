/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

import StevensLevel.WithStateImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static StevensLevel.State.*;

/**
 *
 * @author tristangoffman
 */
public class WithStateTest extends TestBase {

    private StateLike obj;

    public WithStateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    public class StateLike extends WithStateImpl {
    }

    @Before
    public void setUp() {
        obj = new StateLike();
    }

    @Test
    public void hasState() {
        hasFieldWith(State.class);
    }

    @Test
    public void startsInWaitingState() {
        assertThat(obj.getState(), is(WAITING));
    }
    
    @Test(expected=IllegalStateException.class)
    public void afterFinishedCanNoLongerSetState(){
        obj.setState(COMPLETE);
        obj.setState(WAITING);
    }

    @After
    public void tearDown() {
    }
}
