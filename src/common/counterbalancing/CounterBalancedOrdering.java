package common.counterbalancing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Nov 7, 2011
 */
public class CounterBalancedOrdering {

    private int groups;
    private int trials;
    private int numOrderables;

    public int getNumOrderables() {
        return numOrderables;
    }

    public void setNumOrderables(int numOrderables) {
        this.numOrderables = numOrderables;
    }

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public int getTrials() {
        return trials;
    }

    public void setTrials(int trials) {
        this.trials = trials;
    }

    List<List<Integer>> getOrderings() {
        ArrayList<List<Integer>> li = new ArrayList<List<Integer>>();


        final int numOs = this.getNumOrderables();
        ArrayList<Integer> colArr = new ArrayList<Integer>();

        for (int i = 0; i < numOs; i++) {
          colArr.add(new Integer(i));
        }
        
        while (li.size() < getGroups()) {

            int temp = 0;
            //NOTE: Not the best implementation by any means
            List<Integer> attempt = new ArrayList<Integer>();

            for (int i = 0; i < numOs; i++) {
                temp = (int) Math.floor(Math.random() * numOs);
                attempt.add(new Integer(temp));
            }

            boolean isDup = false;
            for (List<Integer> other : li) {
                if (other.equals(attempt)) {
                    isDup = true;
                    break;
                }
            }
            
            if (!isDup && attempt.containsAll(colArr)) {
                li.add(attempt);
            }


        }

        return li;
    }
}
