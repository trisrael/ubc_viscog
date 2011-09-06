package screens;

import correlation.Distribution2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com)
 */
public abstract class ManyCorrelationScreen extends AbstractStrictScreen {
    /**
     * Instance variables
     */
     private HashMap<Enum, Distribution2D> graphs = null;

    /**
     *Returns first Enum class that contains the word 'Constant' & 'Screen' 
     * @return 
     */
    Class<Enum> getDistributionNamesEnum() {

        Class<?>[] classes = this.getClass().getClasses();
        for (Class<?> clazz : classes) {
            final String name = clazz.getName();
            if (clazz.isEnum()) { //Build distributions for first enum found within a class
                return (Class<Enum>) clazz;
            }


        }
        return null;
    }
    
    protected Distribution2D getDistribution(Enum name){
        
        if(graphs == null){
            buildDistributions();
        }
        
        return graphs.get(name);
    }

    private void buildDistributions() {
        Class<Enum> clazz = getDistributionNamesEnum();
        graphs = new HashMap<Enum, Distribution2D>();
        
        for (Enum n : clazz.getEnumConstants()) {
            graphs.put(n, new Distribution2D());
        }
    }
    
  
}
