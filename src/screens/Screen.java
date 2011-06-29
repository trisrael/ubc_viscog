package screens;

import java.awt.Image;

/**
 * The interface for experiment screens.
 * <p>
 * Each screen has a correct key, an image, and a timeout associated
 * with it.
 * 
 * @author Will
 *
 */
public interface Screen {
	
	/**
	 * Get the current screen's image.
	 * 
	 * @return	the screen's image
	 */
	public Image getImage();
	
	/**
	 * Get the correct key of this screen.
	 * 
	 * @return	the screen's correct key
	 */
	public int getCorrectKey();
	
	/**
	 * Get the timeout of this screen.
	 * 
	 * @return	the timeout of this screen, with -1 meaning infinity
	 */
	public float getTimeout();
	
	/**
	 * Set the key considered "correct" for this screen.
	 * 
	 * @param key	integer value representing the correct key
	 */
	public void setCorrectKey(int key);
			
}
