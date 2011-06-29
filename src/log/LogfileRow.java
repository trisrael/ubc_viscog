package log;

/**
 * Temporary implementation of a row for an XML log file (since I wanted to separate
 * the main test file from this implementation)
 * 
 * @author Will
 *
 */
public class LogfileRow {
	
	static final public int NUM_COLS = 10;
	static final public String DELIMITER = "\t";
	
	static public final int COL_TRIAL_NUM = 0;
	static public final int COL_TRIAL_TIME = 1;
	static public final int COL_BASE = 2;
	static public final int COL_COMPARE = 3;
	static public final int COL_DIFFERENCE = 4;
	static public final int COL_IS_CORRECT = 5;
	static public final int COL_IS_ASCENDING = 6;
	static public final int COL_SIDE = 7;
        static public final int COL_SCALING_VAL = 8;
        static public final int COL_DOT_SIZE = 9;
        static public final int COL_NUM_POINTS = 10;
        static public final int COL_DOT_STYLE = 11;
        static public final int COL_DOT_HUE = 12;
	
	static protected int printOrder[] =
								 {COL_TRIAL_NUM,
								  COL_TRIAL_TIME,
								  COL_BASE,
								  COL_COMPARE,
								  COL_DIFFERENCE,
								  COL_IS_CORRECT,
								  COL_IS_ASCENDING,
								  COL_SIDE,
                                                                  COL_SCALING_VAL,
                                                                  COL_DOT_SIZE,
                                                                  COL_NUM_POINTS,
                                                                  COL_DOT_STYLE,
                                                                  COL_DOT_HUE
                                                                  };
	
	static protected String str_trialNum = "trialNum";
	static protected String str_base = "param_1";
	static protected String str_compare = "param_2";
	static protected String str_difference = "difference";
	static protected String str_isCorrect = "isCorrect";
	static protected String str_isAscending = "isAscending";
	static protected String str_side = "side";
	static protected String str_trialTime = "trialTime";
        static protected String str_scalingVal = "scalingVal";
        static protected String str_dotSize = "dotSize";
        static protected String str_numPoints = "numPoints";
        static protected String str_dotStyle = "dotStyle";
        static protected String str_dotHue = "dotHue";
	
	// in case one would like to redefine this for some reason
	static public String RIGHT = "right";
	static public String LEFT = "left";
	
	protected int trialNum;			// trial number
	protected double trialTime;		// trial time (in seconds)
	protected double base;			// base
	protected double compare;		// comparison value
	protected double difference;	// (absolute) difference
	protected boolean isCorrect;	// is the response correct?
	protected boolean isAscending;	// is the convergence an ascending type? 
	protected String side;			// what side was the variant on? (right or left)
        protected double scalingVal;        // what is the scaling value?
        protected double dotSize;           // size of the points
        protected double numPoints;         // how many points per plot?
        protected int dotStyle;             // which style of dot?
        protected int dotHue;               // which color?
	
	public LogfileRow(int trialNum, double trialTime, double base, double compare, double difference, boolean isCorrect, boolean isAscending, String side, double scalingVal, double dotSize, int numPoints, int dotStyle, int dotHue){
		this.trialNum = trialNum;
		this.trialTime = trialTime;
		this.base = base;
		this.compare = compare;
		this.difference = difference;
		this.isCorrect = isCorrect;
		this.isAscending = isAscending;
		this.side = side;
                this.scalingVal = scalingVal;
                this.dotSize = dotSize;
                this.numPoints = numPoints;
                this.dotStyle = dotStyle;
                this.dotHue = dotHue;
	}
	
	public String toString(){
		String temp = "";
		String curr;
		
		for(int i=0; i<printOrder.length; i++){
			curr = "";
			
			switch(printOrder[i]){
			case COL_BASE:
				curr = curr+base;
				break;
			case COL_COMPARE:
				curr = curr+compare;
				break;
			case COL_DIFFERENCE:
				curr = curr+difference;
				break;
			case COL_IS_ASCENDING:
				curr = curr+isAscending;
				break;
			case COL_IS_CORRECT:
				curr = curr+isCorrect;
				break;
			case COL_SIDE:
				curr = curr+side;
				break;
			case COL_TRIAL_NUM:
				curr = curr+trialNum;
				break;
			case COL_TRIAL_TIME:
				curr = curr+trialTime;
				break;
                        case COL_SCALING_VAL:
                                curr = curr+scalingVal;
                                break;
                        case COL_DOT_SIZE:
                                curr = curr+dotSize;
                                break;
			case COL_NUM_POINTS:
                                curr = curr+numPoints;
                                break;
                        case COL_DOT_STYLE:
                                curr = curr+dotStyle;
                                break;
                        case COL_DOT_HUE:
                                curr = curr+dotHue;
                                break;
                        default:
				curr = curr+"err";
				break;	
			}
			
			temp = temp+curr;
			if(i==printOrder.length-1){
				//temp = temp + "\n";
			}else{
				temp = temp + DELIMITER;
			}
			
		}
		
		return temp;
	}
	
	static public String getTitle(){
		String temp = "";
		String curr;
		
		for(int i=0; i<printOrder.length; i++){
			curr = "";
			
			switch(printOrder[i]){
			case COL_BASE:
				curr = curr+str_base;
				break;
			case COL_COMPARE:
				curr = curr+str_compare;
				break;
			case COL_DIFFERENCE:
				curr = curr+str_difference;
				break;
			case COL_IS_ASCENDING:
				curr = curr+str_isAscending;
				break;
			case COL_IS_CORRECT:
				curr = curr+str_isCorrect;
				break;
			case COL_SIDE:
				curr = curr+str_side;
				break;
			case COL_TRIAL_NUM:
				curr = curr+str_trialNum;
				break;
			case COL_TRIAL_TIME:
				curr = curr+str_trialTime;
				break;
                        case COL_SCALING_VAL:
                                curr = curr+str_scalingVal;
                                break;
                        case COL_DOT_SIZE:
                                curr = curr+str_dotSize;
                                break;
                        case COL_NUM_POINTS:
                                curr = curr+str_numPoints;
                                break;
                        case COL_DOT_STYLE:
                                curr = curr+str_dotStyle;
                                break;
                        case COL_DOT_HUE:
                                curr = curr+str_dotHue;
                                break;
			default:
				curr = curr+"err";
				break;	
			}
			
			temp = temp+curr;
			if(i==printOrder.length-1){
				//temp = temp + "\n";
			}else{
				temp = temp + DELIMITER;
			}
			
		}
		
		return temp;
	}	
}
