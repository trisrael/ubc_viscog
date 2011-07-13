package screens;

import correlation.Distribution2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com)
 */
public abstract class ManyCorrelationScreen extends AbstractScreen {

    /**
     *Returns first Enum class that contains the word 'Constant' & 'Screen' 
     * @return 
     */
    Class<Enum> getDistribution2dNames() {

        Class<?>[] classes = this.getClass().getClasses();
        for (Class<?> clazz : classes) {
            final String name = clazz.getName();
            if (name.contains("Constant") && clazz.isEnum()) {
                return (Class<Enum>) clazz;
            }


        }
        return null;
    }
    
    public HashMap<Enum, Distribution2D> graphs = new HashMap<Enum, Distribution2D>();
}
