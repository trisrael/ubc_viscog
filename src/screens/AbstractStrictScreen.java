/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 * Class which forces implementation of generateImage method
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 16, 2011
 */
public abstract class AbstractStrictScreen extends AbstractScreen {

    @Override
    public Image getImage() {
        if (this.currentImage == null) {
            this.currentImage = generateImage();
        }

        return super.getImage();
    }

    protected abstract Image generateImage();
    
    protected void setGraphicDefaults(Graphics2D g2) {
        // enable anti-aliasing

        // code to check anti-aliasing
        // RenderingHints rhints = g2.getRenderingHints();
        // boolean antialiasOn = rhints.containsValue(RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle.Float(0, 0, width, height));
    }
}
