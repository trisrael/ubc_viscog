package yaml;

import configuration.RoundDesign;
import configuration.StevensLevelDesign;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 17, 2011
 */
public class StevensLevelDesignConstructor extends Constructor{
    public StevensLevelDesignConstructor(){
       super(StevensLevelDesign.class);
       TypeDescription sequential = new TypeDescription(StevensLevelDesign.class);
       sequential.putListPropertyType("sequential", RoundDesign.class);
       
       TypeDescription counterbalanced = new TypeDescription(StevensLevelDesign.class);
       counterbalanced.putListPropertyType("counterbalanced", RoundDesign.class);
       
       this.addTypeDescription(sequential);
       this.addTypeDescription(counterbalanced);
    }
           
       
       
       
    
}
