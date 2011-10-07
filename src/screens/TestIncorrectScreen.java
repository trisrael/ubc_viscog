package screens;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import util.Globals;
import util.Util;


public class TestIncorrectScreen extends SimpleTextScreen{

    @Override
    public String text() {
       return "Incorrect";
    }

    @Override
    public Color fontColor() {
       return Color.RED;
    }
	
}
