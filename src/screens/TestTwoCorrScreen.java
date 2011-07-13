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

public class TestTwoCorrScreen extends AbstractScreen implements XMLWriteable {
    //private Font myFon = Globals.FONT_FEEDBACK;

    // Correlation values
    protected double R_left;
    protected double R_right;
    // Other values relevant to the correlation
    protected int size;
    protected double error;
    protected double ptSize;
    protected double scalingVal;
    protected int ptStyle;
    protected int ptHue;
    // Dimensions of the graphs to be displayed
    //Ben adjusted these on new mac mini 10/21/10
    private final int graphWidth = 183;
    private final int graphHeight = 187;
    protected Distribution2D myDistLeft = new Distribution2D();
    protected Distribution2D myDistRight = new Distribution2D();
    // what to draw
    protected boolean isAxisOn = true;
    protected boolean isLabelsOn = true;
    protected final static String objectVersion = "2009-04-28";

    public String getXMLString() {
        String temp = "<distributions>\n";
        temp = temp + "\t<left>" + myDistLeft.getXMLString() + "\t</left>\n";
        temp = temp + "\t<right>" + myDistRight.getXMLString() + "\t</right>\n";
        temp = temp + "</distributions>\n";

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
    public TestTwoCorrScreen(double R_left, double R_right, int size, double error, double scaleVal, double pointSize, int pointStyle, int pointHue) {

        this.scalingVal = scaleVal;
        this.ptSize = pointSize;
        this.ptStyle = pointStyle;
        this.ptHue = pointHue;

        this.size = size; // number of points?
        this.error = error;
        this.R_left = R_left;
        this.R_right = R_right;
        this.correctKey = KeyEvent.VK_SPACE;
        this.timeout = -1;

        generateDistributions();
    }

    public void setDrawAxis(boolean isDrawAxis) {
        this.isAxisOn = isDrawAxis;
    }

    public void setDrawLabels(boolean isDrawLabels) {
        this.isLabelsOn = isDrawLabels;
    }

    //adjust here for the uniform condition
    private void generateDistributions() {
        //Uniform
        // myDistLeft.nextReliableCorrelatedUniforms(R_left, size, error);
        // myDistRight.nextReliableCorrelatedUniforms(R_right, size, error);

        //Gaussian
        myDistLeft.turnIntoTransformedCorrelatedGaussian(R_left, size, error);
        myDistRight.turnIntoTransformedCorrelatedGaussian(R_right, size, error);
    }

    /**
     * Returns the image representing this screen.
     * <p>
     * Drawing this on the fly to save memory. Note that this might be slower
     * than generating in advance and blitting to the screen.
     *
     * @return
     */
    public Image getImage() {
        return generateImage();
    }

    /**
     * @see Screen.generateImage();
     * @return	the image representing this screen
     */
    private Image generateImage() {
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

        g2.setColor(new Color(255, 255, 255));
        g2.fill(new Rectangle.Float(0, 0, width, height));

        // For Gaussian, no standard deviation Correction
        Image imLeft = myDistLeft.getImage(graphWidth, graphHeight, size, scalingVal, ptSize, ptStyle, ptHue);
        Image imRight = myDistRight.getImage(graphWidth, graphHeight, size, scalingVal, ptSize, ptStyle, ptHue);

        //Ben adjusted these on new mac mini 10/21/10
        //adjusted to use 7ths instead of 4ths to bring the two plots
        //closer together on the screen. original was with 1/4 and 3/4
        //now uses 2/7 and 5/7
        int ypos = (height / 2) - (graphHeight / 2);
        int xlpos = (2 * width) / 7 - (graphWidth / 2);
        int xrpos = (5 * width) / 7 - (graphWidth / 2);

        g2.drawImage(imLeft, xlpos, ypos, null);
        g2.drawImage(imRight, xrpos, ypos, null);

        // Debug
        if (Globals.isDebug) {
            System.out.println("RL = " + myDistLeft.getPearsonCorrelation());
            System.out.println("RR = " + myDistRight.getPearsonCorrelation());
        }

        // Free resources
        g2.dispose();
        return Util.toImage(bi);
    }

    static public void main(String[] args) {
        TestTwoCorrScreen myScreen = new TestTwoCorrScreen(0.1, 0.4, 20, 0.0001, 3, 1, 1, 1);
        System.out.println(myScreen.getXMLString());
    }
}
