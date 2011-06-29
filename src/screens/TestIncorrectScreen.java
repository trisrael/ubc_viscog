package screens;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import util.Globals;
import util.Util;


public class TestIncorrectScreen extends AbstractScreen{
	
	public TestIncorrectScreen(){
		this.correctKey = KeyEvent.VK_SPACE;
		this.timeout = -1;
		this.currentImage = generateImage();
	}
	
	private Image generateImage(){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		
		// Background
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle.Float(0,0,width,height));
		
		// Text
		g2.setColor(Color.BLACK);
		g2.setFont(Globals.FONT_FEEDBACK);		
		g2.drawString("Incorrect", width/2, height/2);
		
		// Free resources
		g2.dispose();
		return Util.toImage(bi);
	}
}
