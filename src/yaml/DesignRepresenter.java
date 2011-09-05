/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaml;

import configuration.PointShapeFactory;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class DesignRepresenter extends Representer{
    public DesignRepresenter() {
        this.representers.put(PointShapeFactory.class, new RepresentDesign());
    }

    private class RepresentDesign implements Represent {
        public Node representData(Object data) {
            PointShapeFactory obj = (PointShapeFactory) data;
            String value = obj.getClassName();
            return representScalar(new Tag("!design"), value);
        }
    }
}
