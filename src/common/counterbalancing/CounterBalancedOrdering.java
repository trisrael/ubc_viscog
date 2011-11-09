package common.counterbalancing;

import common.filesystem.FileSystem;
import common.filesystem.FileSystemConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Integer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.SizeLimitExceededException;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Nov 7, 2011
 */
public class CounterBalancedOrdering {
    private static String foldPath = FileSystem.FOLDER_CONF + File.separatorChar + FileSystemConstants.COUNTERBALANCED_ORDERING_FOLDER;
 

    private int groups;
    
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
    
    protected <E> List<E> reorder(List<E> toReorder, int subjectNumber) throws SizeLimitExceededException{
        if(toReorder.size() != getNumOrderables()){
            throw new SizeLimitExceededException("Number of orderables is different size than list supplied");
        }
        
        List<List<Integer>> orders = getOrderings();
        //Save to file
        
         
        try {
            Yaml yaml = new Yaml();
            yaml.dump(orders, new FileWriter(getOrdFile(toReorder.size(), getGroups())));
        } catch (IOException ex) {
            Logger.getLogger(CounterBalancedOrdering.class.getName()).log(Level.WARNING, "Error while attempting to dump out yaml of orderings", ex);
        }
        
        int rawIndex = getGroups() % subjectNumber;
        
        List<Integer> reodering = orders.get(rawIndex);
        return reorderinplace(reodering, toReorder);
    }
    
    
    private static <E> List<E> reorderinplace(List<Integer> reodering, List<E> toReorder) {
        for (int i = 0; i < reodering.size(); i++) {
            Integer newIndex = reodering.get(i);
            toReorder = swap(toReorder, i, newIndex);
        }
        return toReorder;
    }
    
    public static <E> List<E> reorder(List<E> toReorder, int subjectNumber, int numGroups) throws SizeLimitExceededException, NotEnoughPermutations{
        if(containsFile(toReorder.size(), numGroups))
            try{
                InputStream input = new FileInputStream( getOrdFile(toReorder.size(), numGroups));
                Yaml yaml = new Yaml();
                List data = (List) yaml.load(input);
                List arr = (List) data.get(numGroups % subjectNumber);
                List<Integer> newarr = new ArrayList<Integer>();
                
                for (Object object : arr) {
                    newarr.add(new Integer((int) object));
                }
                
                return reorderinplace(newarr, toReorder);
                
            }catch(FileNotFoundException ex){
                //don't do anything continue on with rest (non IO based solution
            }
              
   

        
        
        if(numGroups > fact(toReorder.size()))
            throw new NotEnoughPermutations("Given the amount of elements in the list given enough permutations of the elements are not available for" + Integer.toString(numGroups));
        
        CounterBalancedOrdering ord = new CounterBalancedOrdering();
        ord.setGroups(numGroups);
        ord.setNumOrderables(toReorder.size());
        
        return ord.reorder(toReorder, subjectNumber);
    }

    
    /**
     * Swaps the position of two element within a list
     * @param <E>
     * @param li
     * @param oldindex
     * @param newindex
     * @return 
     */
    private static <E> List<E> swap(List<E> li, int oldindex,int newindex){
        E val = li.get(oldindex);
        li.set(oldindex, li.get(newindex));
        li.set(newindex, val);
        return li;
    }
    
    private static int fact(int n){
        int temp = 1;
        for(int i = n; n > 0;n--){
            temp*= i;
        }
        return temp;
    }
    
       public static class NotEnoughPermutations extends Exception {

        public NotEnoughPermutations(String string) {
            super(string);
        }
    }
       
        private static String pathToOrdFile(int size, int numGroups){
        return foldPath + File.separatorChar + "ord_groups_" + Integer.toString(numGroups) + "_orderables_" + Integer.toString(size) + ".conf";
    }
    
    private static File getOrdFile(int size, int numGroups){
        return new File(pathToOrdFile(size, numGroups));
    }

    private static boolean containsFile(int size, int numGroups) {
        File file = new File(foldPath);
        if (file.exists()){
            file = new File(pathToOrdFile(size, numGroups));
            return file.exists();}
        else{
            file.mkdir();
        }
        return false;
    }

  
    
}
