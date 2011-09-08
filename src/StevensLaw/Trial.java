/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import StevensLaw.Validator.InvalidCorrelation;

/**
 *A single task for a participant in which they must choose between lowering or raising a single distribution until they have deemed that it
 * has a correlation that is between that of two other distributions.
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 17, 2011
 */
public class Trial extends WithStateImpl{

    //Member variables
    
    private final double highCorr;
    private final double lowCorr;
    private double adjustedCorr;

    public Trial(double highCorr, double lowCorr) {
        this.highCorr = highCorr;
        this.lowCorr = lowCorr;
    }

    public Trial(double highCorr, double lowCorr, double startCorr) {
        this(highCorr, lowCorr);
        this.adjustedCorr = startCorr;
    }
    
    /**
     * Set adjusted correlation to new value, when an invalid correlation is supplied throw an exception (invalid correlation should never be supplied)
     * @return 
     */
    public double adjustCorrelation(double val) throws InvalidCorrelation{
        Validator.validateCorr(val);
        
        this.adjustedCorr = val;
        return this.adjustedCorr;
    }

    public void run() {
        
    }
    
}
