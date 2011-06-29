package log;

/**
 * Temporary implementation of a row for a log file (since I wanted to separate
 * the main test file from this implementation)
 * 
 * @author Will
 *
 */
public class XML_LogfileRow {
	
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
        static public final int COL_POINT_SIZE = 8;
        static public final int COL_DOT_STYLE = 9;
        static public final int COL_DOT_HUE = 10;
	
	static protected int printOrder[] =
								 {COL_TRIAL_NUM,
								  COL_TRIAL_TIME,
								  COL_BASE,
								  COL_COMPARE,
								  COL_DIFFERENCE,
								  COL_IS_CORRECT,
								  COL_IS_ASCENDING,
								  COL_SIDE,
                                                                  COL_POINT_SIZE,
                                                                  COL_DOT_STYLE,
                                                                  COL_DOT_HUE
                                                                    };
	
	static protected String str_trialNum = "trialNum";
	static protected String str_base = "base";
	static protected String str_compare = "compare";
	static protected String str_difference = "difference";
	static protected String str_isCorrect = "isCorrect";
	static protected String str_isAscending = "isAscending";
	static protected String str_side = "side";
	static protected String str_trialTime = "trialTime";
        static protected String str_pointSize = "pointSize";
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
        protected int pointSize;        //dot size?
        protected int dotStyle;         // dot style
        protected int dotHue;           // dot hue
	
	public XML_LogfileRow(int trialNum, double trialTime, double base, double compare, double difference, boolean isCorrect, boolean isAscending, String side, int numPoints, int dotStyle, int dotHue){
		this.trialNum = trialNum;
		this.trialTime = trialTime;
		this.base = base;
		this.compare = compare;
		this.difference = difference;
		this.isCorrect = isCorrect;
		this.isAscending = isAscending;
		this.side = side;
                this.pointSize = numPoints;
                this.dotStyle = dotStyle;
                this.dotHue = dotHue;
	}
	
	
	public String toString(){
		String temp = "\t<row>";
		String curr;
		
		for(int i=0; i<printOrder.length; i++){
			curr = "";
			
			switch(printOrder[i]){
			case COL_BASE:
				curr = curr+"<base>"+base+"</base>";
				break;
			case COL_COMPARE:
				curr = curr+"<compare>"+compare+"</compare>";
				break;
			case COL_DIFFERENCE:
				curr = curr+"<difference>"+difference+"</difference>";
				break;
			case COL_IS_ASCENDING:
				curr = curr+"<isAscending>"+isAscending+"</isAscending>";
				break;
			case COL_IS_CORRECT:
				curr = curr+"<isCorrect>"+isCorrect+"</isCorrect>";
				break;
			case COL_SIDE:
				curr = curr+"<side>"+side+"</side>";
				break;
			case COL_TRIAL_NUM:
				curr = curr+"<trialNum>"+trialNum+"</trialNum>";
				break;
			case COL_TRIAL_TIME:
				curr = curr+"<trialTime>"+trialTime+"</trialTime>";
				break;
                        case COL_POINT_SIZE:
                                curr = curr+"<pointSize>"+pointSize+"</pointSize>";
                                break;
                        case COL_DOT_STYLE:
                                curr = curr+"<dotStyle>"+dotStyle+"</dotStyle>";
                                break;
                        case COL_DOT_HUE:
                                curr = curr+"<dotHue>"+dotHue+"</dotHue>";
                                break;
			default:
				curr = curr+"<err>"+"err"+"</err>";
				break;	
			}
			
			temp = temp+curr;
			if(i==printOrder.length-1){
				//temp = temp + "\n";
				temp = temp + "</row>";
			}else{
				temp = temp + DELIMITER;
			}
			
		}
		
		return temp;
	}
	
	static public String getTitle(){
		String temp = "<experiment>\n<titles>";
		String curr = "";
		
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
                        case COL_POINT_SIZE:
                                curr = curr+str_pointSize;
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
				temp = temp + "</titles>";
			}else{
				temp = temp + DELIMITER;
			}
			
		}
		
		return temp;
	}
	
	static public String getFooter(){
		return "</experiment>\n";
	}

	
	
}
