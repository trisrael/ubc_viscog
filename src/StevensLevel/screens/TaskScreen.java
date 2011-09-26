package StevensLevel.screens;

import correlation.Distribution2D;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import screens.ManyCorrelationScreen;
import util.Util;

/**
 *Screen which generates three correlation graphs as output. Each image is cached separately to avoid repeated work/calculations from being performed
 * when it is possible to avoid extra work.
 * @author Tristan Goffman(tgoffman@gmail.com)
 */
public class TaskScreen extends ManyCorrelationScreen {

    private final double DEF_ERROR = 0.0001;
    private int totGWidth = (int) (width * 0.6); //width taking up by graphs 
    private int remWidth = width - totGWidth;
    //width measurements
    private int padInner = (remWidth / 7);
    private int offsetLeft = padInner * 2;
    private int gWidth = totGWidth / 3;
    //height measurements
    private int gHeight = gWidth;
    private int offTop = (height - gHeight) / 2;

    /**
     * Data object to hold update information for a point graph which draws different correlation levels.
     * Members(correlation value, number of points to draw)
     */
    public static class StevensLevelUpdateViewEvent {

        public double corr;
        public int numPoints;
        public Graphs graph;

        public StevensLevelUpdateViewEvent(double c, int pts, Graphs graph) {
            this.corr = c;
            this.numPoints = pts;
            this.graph = graph;

        }
    }

    
    public void update(StevensLevelUpdateViewEvent payload) {
        /**
         * Takes an id (enum) of a graph within this screen and updates the distribution with the information held within.
         */
         Distribution2D dist = getDistribution(payload.graph);
        if (dist != null) {
            dist.turnIntoTransformedCorrelatedGaussian(payload.corr, payload.numPoints, DEF_ERROR); //update distribution (re-calculate points)
        }
    }
    
    /**
     * Enum to describe the different graphs painted on this particular screen.
     */
    public enum Graphs {
        HIGH,
        USER_DEFINED,
        LOW
    }

    protected Image generateImage() {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();

        setGraphicDefaults(g2);

        drawImage(Graphs.HIGH, g2);
        drawImage(Graphs.USER_DEFINED, g2);
        drawImage(Graphs.LOW, g2);

        //System.out.println("R = " + mydist.getPearsonCorrelation());

        // Free resources
        g2.dispose();
        return Util.toImage(bi);
    }

    /**
     * Draws a single distrubtion into its spot on the Graphics2D object given.
     * @param graph
     * @param g 
     */
    private void drawImage(Graphs graph, Graphics2D g) {
        Distribution2D dist = getDistribution(graph);
        
        // Draw the distribution in the centre of the screen
        Image im = dist.getImage(gWidth, gHeight, dist.size(), 1, 1, 1, 1);
        
        int x=0;int y=0; 
        switch (graph) {
            case HIGH: //Right
                x = offsetLeft + 2*(gWidth + padInner);
            break;
                
            case USER_DEFINED: //Middle
                x = offsetLeft + gWidth + padInner; 
            break;
                
            case LOW: //Left
                x = offsetLeft; 
            break;
        }
        y = offTop;
        g.drawImage(im, x, y, null);
    }
}
