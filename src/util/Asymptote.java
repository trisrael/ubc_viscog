package util;

import java.util.Vector;

public class Asymptote {
	Vector values = null;
	boolean isConverged = false;
	private final double k = .25;	// critical value for FTest convergence
	
	int interval = 8;
	int numIntervals = 3;
	
	public Asymptote(){
		values = new Vector();
	}
	
	public int size(){return values.size();}
	public boolean isConverged(){return isConverged;}
	public void addValue(double value){
		values.add(new Double(value));
		testConverge();
	}

    public double getConvergeWindowAverage(){
        double result = 0.0;
        int startIndex = getConvergeStartIndex();
        result = BasicStats.average(BasicStats.subVec(values, startIndex, values.size()-1), values.size()-startIndex);

        return result;
    }
    
    public double getTrialsToConverge(){
    	return values.size();
    }

    /**
     * Get the start index of the convergence window
     * @return
     */
    private int getConvergeStartIndex(){
        return values.size()-interval*numIntervals;
    }
	
	private void testConverge(){
		if(isConverged) return;
		
		// basic F-test
		double fTest = Double.NEGATIVE_INFINITY;	// sentinal value

        // Gideon debug
		// int startIndex = values.size()+1-interval*numIntervals;
        // int startIndex = values.size()-interval*numIntervals;
        int startIndex = getConvergeStartIndex();
		
		if(startIndex < 0) return;

        // Gideon debug
		//if(startIndex+interval*numIntervals > values.size()) return;
		
		try{
			// TODO: make a faster method than basicFTest()... it's very slow!
			fTest = BasicStats.basicFTest(values, startIndex, interval, numIntervals);
		}catch(Exception e){
			// didn't converge if something messed up
			return;
		}
		
		if(fTest < k) isConverged = true;
	}
	
	public String toString(){
		return values.toString();
	}
}
