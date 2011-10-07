package screens;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import util.Globals;
import util.Util;


public class BeginScreen extends SimpleTextScreen{
    

    @Override
    public String text() {
        return "Please press spacebar to begin.";
    }

    @Override
    public Color fontColor() {
        return Color.BLACK;
    }


 
}
