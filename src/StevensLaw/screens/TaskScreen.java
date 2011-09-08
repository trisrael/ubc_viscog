package StevensLaw.screens;

import StevensLaw.UIEvent;
import correlation.Distribution2D;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Map;
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

    public class StevensLawPayload {

        public double corr;
        public int numPoints;

        public StevensLawPayload(double c, int pts) {
            this.corr = c;
            this.numPoints = pts;

        }
    }

    @Override
    public void uiEventOccurred(UIEvent uIEvent, Object payload) {
        StevensLawPayload pay;
        switch (uIEvent) {
            case UPDATE: //deal with an update event (cast the object into the appropriate type and update the screen associated with it)
                Map<Screens, StevensLawPayload> incCorrs = (Map<Screens, StevensLawPayload>) payload;
                if (!incCorrs.isEmpty()) {
                    for (Screens cons : incCorrs.keySet()) {
                        pay = incCorrs.get(cons);
                        updateDistribution(cons, pay);
                    }

                }

                break;
        }
    }

    private void updateDistribution(Screens screens, StevensLawPayload payload) {
        Distribution2D dist = getDistribution(screens);
        if (dist != null) {
            dist.turnIntoTransformedCorrelatedGaussian(payload.corr, payload.numPoints, DEF_ERROR); //update distribution (re-calculate points)
        }
    }

    public enum Screens {
        HIGH,
        USER_DEFINED,
        LOW
    }

    protected Image generateImage() {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();


       
        setGraphicDefaults(g2);

        drawImage(Screens.HIGH, g2);
        drawImage(Screens.USER_DEFINED, g2);
        drawImage(Screens.LOW, g2);

        //System.out.println("R = " + mydist.getPearsonCorrelation());

        // Free resources
        g2.dispose();
        return Util.toImage(bi);
    }

    private void drawImage(Screens screen, Graphics2D g) {
        Distribution2D dist = getDistribution(screen);
        
        // Draw the distribution in the centre of the screen
        Image im = dist.getImage(gWidth, gHeight, dist.size(), 1, 1, 1, 1);
        
        int x=0;int y=0; 
        switch (screen) {
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
