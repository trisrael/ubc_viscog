package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import correlation.Distribution2D;

import util.DrawUtil;
import util.Globals;
import util.Util;

public class TestOneCorrScreen extends AbstractScreen{
	
	private String myStr = "Please press spacebar to begin.";
	private Font myFon = Globals.FONT_FEEDBACK;
	
	public TestOneCorrScreen(){
		this.correctKey = KeyEvent.VK_SPACE;
		this.timeout = -1;
		this.currentImage = generateImage();
	}
	
	private Image generateImage(){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		Distribution2D myDist = new Distribution2D();
		myDist.turnIntoTransformedCorrelatedGaussian(0.7, 500, 0.001);
		
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
		g2.fill(new Rectangle.Float(0,0,width,height));
		
		// Draw the distribution in the centre of the screen
		Image im = myDist.getImage(300, 300, 1, 1, 1, 1);
		
		g2.drawImage(im, width/2-im.getWidth(null)/2, height/2-im.getHeight(null)/2, null);
		
		
		
		System.out.println("R = " + myDist.getPearsonCorrelation());
		
		// Free resources
		g2.dispose();
		return Util.toImage(bi);
	}
}