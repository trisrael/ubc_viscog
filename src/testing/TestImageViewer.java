package testing;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import screens.Screen;
import screens.TestTwoCorrScreen;

public class TestImageViewer extends JFrame{
	
	private static final long serialVersionUID = -1602025884539444688L;
	protected Image img;
	
	public TestImageViewer(Image img){
		this.img = img;
		this.setSize(img.getWidth(null), img.getHeight(null));
		this.setVisible(true);
	}
	
	public void paint(Graphics g){
		if (img != null)
			g.drawImage(img,
					0,
					0,
					this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
                int pointSize =1;
                int pointStyle = 1;
		Screen myScreen = new TestTwoCorrScreen(0.7, 0.3, 400, 0.0001, 1, pointSize, pointStyle, 1);
		TestImageViewer myImage = new TestImageViewer(myScreen.getImage());
	}

}
