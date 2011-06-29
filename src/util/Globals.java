package util;

import deploy.JND.AnimatedLoadingFrame;
import java.awt.Font;
import java.util.Calendar;
import java.util.Random;

public class Globals {
	public static final Font FONT_INSTRUCTIONS = new Font("sanserif", Font.PLAIN, 16);
	public static final Font FONT_FEEDBACK = new Font("sanserif", Font.PLAIN, 16);
	public static final Random GLOBAL_RANDOM = new Random(Calendar.getInstance().getTimeInMillis());
	public static final SimpleTimer GLOBAL_TIMER = new SimpleTimer();

    // get rid of this if it's sucking up cycles or causing delays
    // public static final AnimatedLoadingFrame ANIMATED_LOADING_FRAME = new AnimatedLoadingFrame();
	
	public static final boolean isDebug = false;
}
