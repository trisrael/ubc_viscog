package screens;

import java.awt.Image;

/**
 * This class is used when copies of Screen
 * @author Will
 *
 */
public class BasicScreen extends AbstractScreen {
	public BasicScreen(int correctKey, Image currentImage, float timeout, int width, int height){
		this.correctKey = correctKey;
		this.currentImage = currentImage;	// this is stupid... clone instead
		this.timeout = timeout;
		this.width = width;
		this.height = height;
	}
}
