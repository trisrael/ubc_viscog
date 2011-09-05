/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import com.sun.tools.internal.xjc.api.ClassNameAllocator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class PointShapeFactory {
    static String DEFAULT_CLASS_EXTENSION = "render.";
    private String className = null;
    private ClassLoader cl;

    public ClassLoader getCl() {
        return cl;
    }

    public void setCl(ClassLoader cl) {
        this.cl = cl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        if(className.indexOf(".") == -1){
            className = DEFAULT_CLASS_EXTENSION + className;
        }
        
        this.className = className;
        
        
    }
    
    /** Constructs the class found within className variable (Returning an instance of it) **/
    public PointShapeDrawer build(){
        try {
            Class<?> clazz = Class.forName(className, false, getCl());
                return (PointShapeDrawer) clazz.newInstance();
          
        } catch (Exception ex) {
            Logger.getLogger(PointShapeFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Invalid class supplied as PointShapeDrawer, check design configuration files");
        }
    }
    
    
    
}
