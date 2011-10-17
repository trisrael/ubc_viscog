/*
 * Created on 24-Feb-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package screens;

import java.awt.Image;

public abstract class AbstractScreen implements Screen{
	//TODO: Remove reliance on correct key in future
        protected int correctKey = 32; //space key still made use of by JND 
        
	protected Image currentImage = null;
	protected float timeout = -1;
	protected int width = 1280;
	protected int height = 800;
	
	public Image getImage(){return currentImage;}
	public int getCorrectKey(){return correctKey;}
	public float getTimeout(){return timeout;}
	public void setCorrectKey(int key){this.correctKey = key;}
	
	protected void setDimensions(int width, int height){this.width = width; this.height = height;}
}
