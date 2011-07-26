package StevensLaw.screens;

import correlation.Distribution2D;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import screens.ManyCorrelationScreen;
import util.Util;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com)
 */
public class TaskScreen extends ManyCorrelationScreen {

    enum Constants {
        HIGH,
        MID,
        LOW
    }

    protected Image generateImage() {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();

        final Distribution2D mydist = getDistribution(Constants.HIGH);

        mydist.turnIntoTransformedCorrelatedGaussian(0.7, 500, 0.001);
        setGraphicDefaults(g2);

        // Draw the distribution in the centre of the screen
        Image im = mydist.getImage(300, 300, 1, 1, 1, 1, 1);

        g2.drawImage(im, width / 2 - im.getWidth(null) / 2, height / 2 - im.getHeight(null) / 2, null);

        System.out.println("R = " + mydist.getPearsonCorrelation());

        // Free resources
        g2.dispose();
        return Util.toImage(bi);
    }
}
