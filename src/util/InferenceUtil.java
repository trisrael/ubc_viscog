/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.sun.org.apache.bcel.internal.generic.Select;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hamcrest.SelfDescribing;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class InferenceUtil {
    
    private static final String PRE_GETTER = "get";
    private static final String PRE_SETTER = "set";
    
 
   
    
    /**
     * Set value of member on a java bean
     */
    public static boolean prop(Object toUse, String memberName, Object val){
        try {
            invokeOn(toUse, memberName, val );
            return true;
        } catch (Exception ex) {
            Logger.getLogger(InferenceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    } 
            /**
     * Get value of member from java bean
     * @param toUse
     * @param memberName
     * @return 
     */
    public static Object prop(Object toUse, String memberName) throws Exception{
        
      return invokeOn(toUse, memberName, null );  
    } 
    
    
     
    private static Object invokeOn(Object stl, String name, Object val) throws Exception{
        
        String pre;
        
        
        Class<?>[] types;
    
        Class[] clazz = new Class[1];
        
        if(val != null){
             clazz[0] = val.getClass(); 
             pre = PRE_SETTER;
        }
        else{
            pre = PRE_GETTER;
            clazz = null;
        }
                
       
        Method meth = stl.getClass().getMethod(pre + upFirst(name) , clazz);
        
        if( val != null){
           return meth.invoke(stl, val); 
        }else{
            return meth.invoke(stl);
        }
    }

    private static String upFirst(String name) {
        String frst = name.substring(0, 1);
        String frstUp = frst.toUpperCase();
        name = name.replaceFirst(frst, frstUp);
        return name;
    }

   
}
