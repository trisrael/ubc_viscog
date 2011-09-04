/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy.common;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class SubjectControl {
    private static int subjNumber;
    private static int NUM_ORDER_TYPES = 5;
    
    public static void setSubject(int num){
        subjNumber = num;
    }
    
    public static int getSubject(){
        return subjNumber;
    }
    
    /**
     * Returns what
     * 1 -> 5 possible return values (dependent on number_order_types)
     * @return 
     */
    private static int orderingType(){
        return ((SubjectControl.subjNumber + 3) % NUM_ORDER_TYPES) + 1;
    }
    
}
