package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import screens.AbstractStrictScreen;
import util.Globals;
import util.Util;




import java.awt.Image;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Nov 4, 2011
 */
public class BlankScreen extends SimpleTextScreen{

    @Override
    public String text() {
        return "";
    }

    @Override
    public Color fontColor() {
        return Color.WHITE;
    }

   
    
}
