/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Validator {
    
    public static void validateCorr(double corrValue) throws InvalidCorrelation{
        if(!isValidCorr(corrValue))
        {           
            throw new InvalidCorrelation(corrValue);
        }
    }   
    
    public static boolean isValidCorr(double corrVal){
         return corrVal < 1.0 && corrVal > 0.0;
    }
    
    public static class InvalidCorrelation extends Exception{
        public static final String msg = "Invalid correlation value supplied. Valid correlation's are between 0.0-1.0 received value of ";
        
        public InvalidCorrelation(double val){
            super(msg + " " + val);
        }
    }
            
    
}
