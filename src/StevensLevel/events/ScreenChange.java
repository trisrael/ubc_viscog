package StevensLevel.events;

import screens.Screen;

/**
 *Javabean for delivering new Screen to ViewControl
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 20, 2011
 */
public class ScreenChange {
    private Class<? extends Screen> screenClass;
    
    public ScreenChange(Class<? extends Screen> clazz){
        screenClass = clazz;
    }

    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}
