/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import util.Globals;
import util.Util;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 6, 2011
 */
public abstract class SimpleTextScreen extends AbstractStrictScreen{
    	
    	
	public SimpleTextScreen(){
		this.timeout = -1;
                setDirty(true); //these screens are always 'dirty'
		this.currentImage = generateImage();
	}
        
        //Text to be displayed in center of screen
        public abstract String text();
        
        //Color to display test in
        public abstract Color fontColor();
        
	private Font myFon = Globals.FONT_FEEDBACK;

	
	protected Image generateImage(){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		
		// Background
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle.Float(0,0,width,height));
		
		// Text
		g2.setColor(fontColor());
		g2.setFont(myFon);		
		g2.drawString(text(), width/2-Util.getStringWidth(text(), myFon)/2, height/2);
		
		// Free resources
		g2.dispose();
		return Util.toImage(bi);
	}

}
