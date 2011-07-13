
package screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import util.Globals;
import util.Util;
import correlation.Distribution2D;
import xml.sax.XMLWriteable;

public class basicUniformJND extends AbstractScreen implements XMLWriteable{
	//private Font myFon = Globals.FONT_FEEDBACK;
	
	// Correlation values
	protected double R_left;
	protected double R_right;
	
	// Other values relevant to the correlation
	protected int size;
	protected double error;
	
	// Dimensions of the graphs to be displayed
	private final int graphWidth = 188;
	private final int graphHeight = 188;

    protected Distribution2D myDistLeft = new Distribution2D();
    protected Distribution2D myDistRight = new Distribution2D();

    // what to draw
    protected boolean isAxisOn = true;
	protected boolean isLabelsOn = true;

    protected final static String objectVersion = "2009-04-28";

    public String getXMLString() {
        String temp = "<trialinfo>\n";
        temp = temp + "\t" + "<version>" + objectVersion + "</version>\n";
        temp = temp + "\t" + "<r_left>" + this.R_left + "</r_left>\n";
        temp = temp + "\t" + "<r_right>" + this.R_right + "</r_right>\n";
        temp = temp + "\t" + "<error>" + this.error + "</error>\n";
        temp = temp + "\t" + "<isAxisOn>" + this.isAxisOn + "</isAxisOn>\n";
        temp = temp + "\t" + "<isLabelsOn>" + this.isLabelsOn + "</isLabelsOn>\n";
        temp = temp + "</trialinfo>\n";

        temp = temp + "<left>" + myDistLeft.getXMLString() + "</left>\n";
        temp = temp + "<right>" + myDistRight.getXMLString() + "</right>\n";

        temp = temp + "</TestTwoCorrScreen>\n";
        return temp;
    }

    public String getXMLSaveVersion() {
        return objectVersion;
    }
	
	/**
	 * Constructor for this screen.
	 * <p>
	 * Displays two simple graphs of correlations, one on the left and
	 * the other on the right.
	 * 
	 * @param R_left
	 * @param R_right
	 * @param size
	 * @param error
	 */
	public basicUniformJND(double R_left, double R_right, int size, double error){
		this.size = size;
		this.error = error;
		this.R_left = R_left;
		this.R_right = R_right;
		this.correctKey = KeyEvent.VK_SPACE;
		this.timeout = -1;
        generateDistributions();
	}

    public void setDrawAxis(boolean isDrawAxis){
        this.isAxisOn = isDrawAxis;
    }

    public void setDrawLabels(boolean isDrawLabels){
        this.isLabelsOn = isDrawLabels;
    }

    private void generateDistributions(){
		myDistLeft.nextReliableCorrelatedUniforms(R_left, size, error);
		myDistRight.nextReliableCorrelatedUniforms(R_right, size, error);
    }

    /**
     * Returns the image representing this screen.
     * <p>
     * Drawing this on the fly to save memory. Note that this might be slower
     * than generating in advance and blitting to the screen.
     *
     * @return
     */
    public Image getImage(){
        return generateImage();
    }
	
	/**
	 * @see Screen.generateImage();
	 * @return	the image representing this screen
	 */
	private Image generateImage(){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		
		// Setup our distributions
		
        myDistLeft.setDrawAxis(this.isAxisOn);
        myDistRight.setDrawAxis(this.isAxisOn);
        myDistLeft.setDrawLabels(this.isLabelsOn);
        myDistRight.setDrawLabels(this.isLabelsOn);
		
		// enable anti-aliasing		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							 RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// Background
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle.Float(0,0,width,height));
		
		// Draw the distribution in the centre of the screen
		Image imLeft = myDistLeft.getImage(graphWidth, graphHeight, 1, 1, 1, 1, 1);
		Image imRight = myDistRight.getImage(graphWidth, graphHeight, 1, 1, 1, 1, 1);
		
		int ypos = height/2-graphHeight/2;
		int xlpos = width/4-graphWidth/2;
		int xrpos = 3*width/4-graphWidth/2;
		
		g2.drawImage(imLeft, xlpos, ypos, null);
		g2.drawImage(imRight, xrpos, ypos, null);
		
		// Debug
		if(Globals.isDebug){
			System.out.println("RL = " + myDistLeft.getPearsonCorrelation());
			System.out.println("RR = " + myDistRight.getPearsonCorrelation());
		}
		
		// Free resources
		g2.dispose();
		return Util.toImage(bi);
	}

    static public void main(String[] args){
        TestTwoCorrScreen myScreen = new TestTwoCorrScreen(0.1, 0.4, 20, 0.0001, 1, 1, 1, 1);
        System.out.println(myScreen.getXMLString());
    }


}


