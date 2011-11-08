package common.counterbalancing;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
/**
 *
 * @author tristangoffman
 */
public class CounterBalancedOrderingTest {
    private CounterBalancedOrdering inst;
    
    public CounterBalancedOrderingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        inst = new CounterBalancedOrdering();
        inst.setTrials(15);
        inst.setGroups(2);
        inst.setNumOrderables(4);
    }
    
    @Test
    public void singleOrdering(){
     inst.setGroups(1);
     assertThat(inst.getOrderings(),hasSize(1));
    }
    
    @Test
    public void containsRangeOfIndexes(){
        inst.setGroups(1);
        assertThat(inst.getOrderings().get(0), containsInAnyOrder(0, 1,3, 4));
    }
            
    
}
