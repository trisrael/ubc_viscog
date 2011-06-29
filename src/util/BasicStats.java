package util;



import java.util.Vector;


// TODO: Re-do the methods in this class to use arrays or something (as
//		 the current implementation is NOT stable in Java 1.4
// TODO: Make a simpler implementation for FTest
public class BasicStats {
	
	
	/**
	 * Calculates the average of the vector of Double, vec.
	 * <p>
	 * The average is calculated for items from index(0) to
	 * index(size-1).
	 * <p>
	 * A uniform probability distribution is assumed.
	 * <p>
	 * Adapted from wFFRunAvgConv.
	 * 
	 * @param vec	a Vector of Double values
	 * @param size	the size of the Vector the average should be calculated to
	 * @return		the average of Double values from index(0) to index(size-1)
	 * @throws		ArithmeticException if size is 0 to indicate a divide by zero
	 */
	public static double average(Vector vec, int size) throws ArithmeticException{
		double sum = 0;
		
		for(int i = 0; i < size; i++){
			sum += ((Double)(vec.elementAt(i))).doubleValue();
		}
		
		return sum/(double)size;
	}

	
	/**
	 * Calculates the variance of items in vec from index(0) to
	 * index(size-1).
	 * <p>
	 * The variance calculated assumes all measurements in a population
	 * are in vec. (Versus a random sample.)
	 * <p>
	 * Note that this function may not be the safest in terms of
	 * numerical computation errors (such as roundoff, etc.)
	 * <p>
	 * Adapted from wFFRunAvgConv.
	 *
	 * @param vec	a Vector of Double values
	 * @param size	the size of the Vector the variance should be calculated over
	 * @return		the variance over a population
	 * @throws		ArithmeticException if size is 0 to indicate a divide by zero
	 */
	public static double varPop(Vector vec, int size) throws ArithmeticException{
		double avg = average(vec, size);
		double sqDist = 0;
		double val = 0;
		
		for(int i=0; i<size; i++){
			val = ((Double)vec.elementAt(i)).doubleValue();
			sqDist += (val-avg)*(val-avg);
		}
		return sqDist/size;
	}
	
	
	/**
	 * Calculates the sample variance of items in vec from index(0) to
	 * index(size-1).
	 * <p>
	 * The variance calculated assumes all measurements in a sample
	 * are in vec.
	 * <p>
	 * Note that this function may not be the safest in terms of
	 * numerical computation errors (such as roundoff, etc.)
	 * <p>
	 * Adapted from wFFRunAvgConv.
	 *
	 * @param vec	a Vector of Double values
	 * @param size	the size of the Vector the variance should be calculated over
	 * @return		the variance over a population
	 * @throws		ArithmeticException if size is 0 to indicate a divide by zero
	 */
	public static double varSample(Vector vec, int size) throws ArithmeticException{
		double avg = average(vec, size);
		double sqDist = 0;
		double val = 0;
		
		for(int i=0; i<size; i++){
			val = ((Double)vec.elementAt(i)).doubleValue();
			sqDist += (val-avg)*(val-avg);
		}
		return sqDist/ (size-1);
	}
	
	/**
	 * Returns the subvector of vec that lies between the specified indices.
	 * <p>
	 * Adapted from wFFRunAvgConv.
	 * 
	 * @param vec			the Vector
	 * @param startIndex	the starting index (inclusive)
	 * @param endIndex		the ending index (inclusive)
	 * @return				the subvector of vec between startIndex and endIndex inclusive
	 */
	public static Vector subVec(Vector vec, int startIndex, int endIndex){
		Vector temp = new Vector(endIndex-startIndex);
		for(int i=0; i<=(endIndex-startIndex); i++){
			temp.add(vec.elementAt(startIndex+i));
		}
		if(Globals.isDebug)System.out.println("subVec: " + temp.toString());
		return temp;
	}
	
	
	
	/**
	 * Gets the averages of each subwindow.
	 * <p>
	 * This looks at the <code>numIntervals</code> sub-windows of size interval
	 * in the range [startIndex, startIndex+numIntervals*interval)
	 * <p>
	 * Adapted from wFFRunAvgConv. 
	 * 
	 * @param vec			the Vector of Double to test matching averages in
	 * @param startIndex	the start index of the first subvector
	 * @param interval		the size of the subvectors
	 * @param numIntervals	the number of intervals
	 * @return				the averages
	 * @throws IndexOutOfBoundsException
	 */
	public static Vector getConsecAvg(Vector vec, int startIndex, int interval, int numIntervals) throws IndexOutOfBoundsException{
		// If start index is negative
		if(startIndex < 0){
			throw new IndexOutOfBoundsException("[startIndex="+startIndex+"] < 0");
		}
		
		// If number of intervals is < 1, fire off an exception
		if(numIntervals < 1){
			throw new IllegalArgumentException("Number of intervals ("+numIntervals+") is too small.");
		}
		
		// If start index is out of bounds, fire off an exception
		if(startIndex >= vec.size()){
			throw new IndexOutOfBoundsException("[startIndex=" + startIndex + "]>=[vec.size()=" + vec.size()+"]");
		}
		
		// See if start + 2*interval is actually possible to measure, if not
		if(startIndex + numIntervals*interval > vec.size()){
			throw new IndexOutOfBoundsException("[startIndex("+startIndex+")+numIntervals*interval("+interval+")=" + (startIndex+numIntervals*interval)+"]>[vec.size()="+vec.size()+"]");
		}
		
		Vector averages = new Vector();
		Vector subVectors = new Vector();	// Vector that stores vectors
		
		// get subvectors
		for(int i=0; i<numIntervals; i++){
			subVectors.add(i, subVec(vec, startIndex+interval*i, startIndex+interval*(i+1)-1)); 
		}
		
		// get averages
		for(int i=0; i<numIntervals; i++){
			averages.add(i, new Double(average((Vector)subVectors.elementAt(i), interval)));
		}
		
		return averages;
	}

	/**
	 * Gets the population variance of each subwindow.
	 * <p>
	 * This looks at the <code>numIntervals</code> sub-windows of size interval
	 * in the range [startIndex, startIndex+numIntervals*interval)
	 * <p>
	 * Adapted from wFFRunAvgConv.
	 * 
	 * @param vec			the Vector to test matching averages in
	 * @param startIndex	the start index of the first subvector
	 * @param interval		the size of the subvectors
	 * @param numIntervals	the number of intervals
	 * @return				the variances
	 * @throws IndexOutOfBoundsException
	 */
 	 public static Vector getConsecVarPop(Vector vec, int startIndex, int interval, int numIntervals) throws IndexOutOfBoundsException{
		// If start index is negative
		if(startIndex < 0){
			throw new IndexOutOfBoundsException("[startIndex="+startIndex+"] < 0");
		}
		
		// If number of intervals is < 1, fire off an exception
		if(numIntervals < 1){
			throw new IllegalArgumentException("Number of intervals ("+numIntervals+") is too small.");
		}
		
		// If start index is out of bounds, fire off an exception
		if(startIndex >= vec.size()){
			throw new IndexOutOfBoundsException("[startIndex=" + startIndex + "]>=[vec.size()=" + vec.size()+"]");
		}
		
		// See if start + 2*interval is actually possible to measure, if not
		if(startIndex + numIntervals*interval > vec.size()){
			throw new IndexOutOfBoundsException("[startIndex("+startIndex+")+numIntervals*interval("+interval+")=" + (startIndex+numIntervals*interval)+"]>[vec.size()="+vec.size()+"]");
		}
		
		Vector variances = new Vector();
		Vector subVectors = new Vector(); // vector of vectors
		
		// get subvectors
		for(int i=0; i<numIntervals; i++){
			subVectors.add(i, subVec(vec, startIndex+interval*i, startIndex+interval*(i+1)-1)); 
		}
		
		// get averages
		for(int i=0; i<numIntervals; i++){
			variances.add(i, new Double(varPop((Vector)subVectors.elementAt(i), interval)));
		}
		
		return variances;
	}


 	/**
 	 * Gets the population variance of each subwindow.
 	 * <p>
 	 * This looks at the <code>numIntervals</code> sub-windows of size interval
 	 * in the range [startIndex, startIndex+numIntervals*interval)
 	 * <p>
 	 * Adapted from wFFRunAvgConv.
 	 * 
 	 * @param vec			the Vector to test matching averages in
 	 * @param startIndex	the start index of the first subvector
 	 * @param interval		the size of the subvectors
 	 * @param numIntervals	the number of intervals
 	 * @return				the variances
 	 * @throws IndexOutOfBoundsException
 	 */
  	 public static Vector getConsecVarSample(Vector vec, int startIndex, int interval, int numIntervals) throws IndexOutOfBoundsException{
 		// If start index is negative
 		if(startIndex < 0){
 			throw new IndexOutOfBoundsException("[startIndex="+startIndex+"] < 0");
 		}
 		
 		// If number of intervals is < 1, fire off an exception
 		if(numIntervals < 1){
 			throw new IllegalArgumentException("Number of intervals ("+numIntervals+") is too small.");
 		}
 		
 		// If start index is out of bounds, fire off an exception
 		if(startIndex >= vec.size()){
 			throw new IndexOutOfBoundsException("[startIndex=" + startIndex + "]>=[vec.size()=" + vec.size()+"]");
 		}
 		
 		// See if start + 2*interval is actually possible to measure, if not
 		if(startIndex + numIntervals*interval > vec.size()){
 			throw new IndexOutOfBoundsException("[startIndex("+startIndex+")+numIntervals*interval("+interval+")=" + (startIndex+numIntervals*interval)+"]>[vec.size()="+vec.size()+"]");
 		}
 		
 		Vector variances = new Vector();
 		Vector subVectors = new Vector(); // vector of vectors
 		
 		// get subvectors
 		for(int i=0; i<numIntervals; i++){
 			subVectors.add(i, subVec(vec, startIndex+interval*i, startIndex+interval*(i+1)-1)); 
 		}
 		
 		// get averages
 		for(int i=0; i<numIntervals; i++){
 			variances.add(i, new Double(varSample((Vector)subVectors.elementAt(i), interval)));
 		}
 		
 		return variances;
 	}
	
	/**
	 * Returns the FTest value of the Double values between
	 * [startIndex, startIndex+interval*numIntervals)
	 * <p>
	 * Adapted from wFFRunAvgConv.
	 * 
	 * @param vec
	 * @param startIndex
	 * @param interval
	 * @param numIntervals
	 * @return
	 */
	static public double basicFTest(Vector vec, int startIndex, int interval, int numIntervals){
		double result;
		
		Vector variances = getConsecVarSample(vec, startIndex, interval, numIntervals);
		Vector averages = getConsecAvg(vec, startIndex, interval, numIntervals);
		double grandVariance = average(variances, numIntervals);
		double betweenVariance = varSample(averages, numIntervals);
		
		result = betweenVariance / grandVariance;
		return result;
	}

}
