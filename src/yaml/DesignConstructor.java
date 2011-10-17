/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaml;


import configuration.Design;
import configuration.PointShapeFactory;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class DesignConstructor extends CustomClassLoaderConstructor{
     private final ClassLoader cloader;
     
      public DesignConstructor(Class<? extends java.lang.Object> theRoot, java.lang.ClassLoader theLoader) {
        super(theRoot, theLoader);
        if (theLoader == null) {
            throw new NullPointerException("Loader must be provided.");
        }
        this.yamlConstructors.put(new Tag("!shape"), new Construct());
        this.cloader = theLoader;    
    }
     
     public DesignConstructor(ClassLoader cLoader) {
       this(Design.class, cLoader);
    }
      
   

    private class Construct extends AbstractConstruct {
        public Object construct(Node node) {
            String val = (String) constructScalar((ScalarNode)node);
            PointShapeFactory fact = new PointShapeFactory();
            fact.setClassName(val);
            fact.setCl(cloader);
            return fact.build();
        }
    }
  
}
