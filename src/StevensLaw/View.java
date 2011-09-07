/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import interaction.ExperimentInteraction;
import interaction.ExperimentInteractionProducer;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import screens.AbstractStrictScreen;
import static java.awt.event.KeyEvent.*;
import static interaction.BasicInteraction.*;
import static StevensLaw.GraphInteraction.*;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Jul 20, 2011
 */
public class View extends ExperimentInteractionProducer implements KeyListener {

    /** default member variables **/
    private int width = 1024;
    private int height = 768;
    private AbstractStrictScreen scr;
    
    
    /**
     * Might be an idea to use a dirty flag, one in which another thread is moving along looking for dirty, if dirty is seen than an update on the screen is run.
     * @param scr 
     */

    void setScreen(AbstractStrictScreen scr) {
        this.scr = scr;
    }

    void update() {
        update(scr.getImage());
    }

    /**
     * Check if ESCAPE key was typed.
     * @param keyCode 
     */
    private void checkForEsc(int keyCode) {
            // always close this window when ESC is pressed
        if (keyCode == KeyEvent.VK_ESCAPE) {
            container.getToolkit().getSystemEventQueue().postEvent(new WindowEvent(container, WindowEvent.WINDOW_CLOSING));
        }
    }

    public enum States {

        FULL,
        MIN,
        MAX
    }
    private Image currImage;
    protected SingleImage container = new SingleImage();

    public View() {
        container.addKeyListener(this);
        invisibleCursor();
        container.h = height;
        container.w = width;
        container.setSize(container.w, container.h);
        
        
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         // Handle a closing window
        container.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                handleWindowClose();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        checkForEsc(ke.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        final int currentKey = ke.getKeyCode();
        checkForEsc(currentKey);
        
        
        ExperimentInteraction notifier;
        
        switch (currentKey) {
            case VK_Z:
                notifier = CorrelationDown;
                break;
            case VK_M:
                notifier = CorrelationUp;
                break;
            case VK_ENTER:
                notifier = TaskCompleted;
                break;
            case VK_SPACE:
                notifier = Continue;
                break;
            default:
                notifier = InvalidKeyPress;
                break;
        }
        notifyListeners(notifier);
    }

    public void start() {
    //    doFullScreen();
        container.setVisible(true);
    }

    public void update(Image image) {
        this.currImage = image;
        container.img = this.currImage;
        if (!isFullscreen()){
            start();
        }
        container.update(container.getGraphics()); //getGraphics will return null until the jframe is visible
    }

    /** private methods **/
    private void doFullScreen() {
        // set background color
        container.setBackground(Color.BLACK);
        // switch to fullscreen mode
        scrdev().setFullScreenWindow(container);

    }

    private boolean isFullscreen() {
        Window win = scrdev().getFullScreenWindow();
        return win != null && win == container;
    }

    /** Set cursor in jframe to invisible **/
    private void invisibleCursor() throws HeadlessException, IndexOutOfBoundsException {
        // set cursor to invisible
        Toolkit tk = Toolkit.getDefaultToolkit();
        Cursor invisibleCursor = tk.createCustomCursor(tk.createImage(""), new Point(), null);
        container.setCursor(invisibleCursor);
    }

    private GraphicsDevice scrdev() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice();
    }

    /** Classes **/
    protected class SingleImage extends JFrame {

        public Image img;
        public int w;
        public int h;

        public void paint(Graphics g) {
            if (img != null) {
                g.drawImage(img,
                        w / 2 - img.getWidth(this) / 2,
                        h / 2 - img.getHeight(this) / 2,
                        this);
            }
        }
    }
    
     /**
     * What to do when the experiment window is closed.
     */
    private void handleWindowClose() {
        container.setVisible(false);
        container.dispose();
        container.removeKeyListener(this);
    }
    
    
}
