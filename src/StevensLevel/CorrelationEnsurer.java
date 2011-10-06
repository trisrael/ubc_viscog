/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 * Ensure a correlation is between appropriate values (1 -- 0)
 */
public class CorrelationEnsurer {
    private static final double MAX = 1.0;
    private static final double MIN = 0.0;
    
    /**
     * Check
     * @param corrValue
     
     */
    public static double ensureCorr(double corrValue) {
        if(!isValidCorr(corrValue))
        {           
            return getClosest(corrValue);
        }
        return corrValue;
    }   
    
    /**
     * Valid correlations fall within 0 > 1 (double)
     * @param corrVal
     * @return 
     */
    public static boolean isValidCorr(double corrVal){
         return corrVal < MAX && corrVal > MIN;
    }

    /**
     * Find whether a correlation between 0 and 1 is closer to 0 or 1, and return which one it is.
     * @param corrValue
     * @return 
     */
    private static double getClosest(double corrValue) {
        double dist1 = Math.abs(MIN - corrValue);
        double dist2 = Math.abs(MAX - corrValue);
        return (dist1 < dist2 ?  MIN : MAX);
    } 
   
            
    
}
