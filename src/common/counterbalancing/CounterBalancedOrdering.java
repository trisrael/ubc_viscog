package common.counterbalancing;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        ArrayList li = new ArrayList();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < this.getNumOrderables(); i++) {
            li.add(i);
        }
        li.add(temp);
        return li;
    }
}
