package common.counterbalancing;

import common.counterbalancing.CounterBalancedOrdering.NotEnoughPermutations;

import java.util.Arrays;
import java.util.List;
import javax.naming.SizeLimitExceededException;
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

    
    @Before
    public void setUp() {
        inst = new CounterBalancedOrdering();
        inst.setGroups(2);
        inst.setNumOrderables(4);
    }
    
    @Test
    public void singleOrdering(){
     inst.setGroups(1);
     assertThat(inst.getOrderings(), hasSize(1));
    }
    
    @Test
    public void containsRangeOfIndexes(){
        inst.setGroups(1);
        assertThat(inst.getOrderings().get(0), containsInAnyOrder(0, 1,3, 2));
    }
    
    
    @Test
    public void multi(){
        inst.setGroups(2);
        List<List<Integer>> orders = inst.getOrderings();
        assertThat(orders, hasSize(2));
        assertThat(orders.get(0), not(equalTo(orders.get(1))));
    } 
    
    @Test
    public void reorderList() throws SizeLimitExceededException, NotEnoughPermutations{
        List<Character> li = Arrays.asList('a', 'b', 'c', 'd');
        
        assertThat(CounterBalancedOrdering.reorder(li, 1, 1), containsInAnyOrder(li.toArray()));
        assertThat(CounterBalancedOrdering.reorder(li, 1, 1), containsInAnyOrder(li.toArray()));
    }
    
    @Test(expected=NotEnoughPermutations.class)
    public void smallList() throws SizeLimitExceededException, NotEnoughPermutations{
        CounterBalancedOrdering.reorder(Arrays.asList('a'), 1, 2);
    }
    
    @Test(expected=SizeLimitExceededException.class)
    public void biggerList() throws SizeLimitExceededException, NotEnoughPermutations{
        inst.setNumOrderables(7);
        inst.setGroups(2);
        inst.reorder(Arrays.asList('a', 'a', 'a'), 1);
    }
    
    
    @Test
    public void sameOrdering() throws SizeLimitExceededException, NotEnoughPermutations{
        assertThat(CounterBalancedOrdering.reorder(Arrays.asList('a', 'b', 'c', 'd', 'e', 'g', 'u', '3'), 2, 4) , equalTo( CounterBalancedOrdering.reorder(Arrays.asList('a', 'b', 'c', 'd', 'e', 'g', 'u', '3'), 2, 4)) );
    }
    
    @Test
    public void differentOrdering() throws SizeLimitExceededException, NotEnoughPermutations{
        assertThat(CounterBalancedOrdering.reorder(Arrays.asList('a', 'b', 'c', 'd', 'e', 'g', 'u', '3'), 1, 5) , not(equalTo( CounterBalancedOrdering.reorder(Arrays.asList('a', 'b', 'c', 'd', 'e', 'g', 'u', '3'), 3 , 5)) ));
        
    }        
    
}
