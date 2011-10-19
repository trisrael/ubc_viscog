/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import configuration.Design;
import configuration.RoundDesign;
import configuration.TaskDesign;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class InferenceUtil {

    public static final String PRE_GETTER = "get";
    public static final String PRE_SETTER = "set";

    /**
     * Set value of member on a java bean
     */
    public static boolean prop(Design toUse, String memberName, Object val) {
        try {
            invokeOn(toUse, memberName, val);
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
    public static Object prop(Design toUse, String memberName) throws Exception {

        return invokeOn(toUse, memberName, null);
    }

    private static Object invokeOn(Design stl, String name, Object val) {

        String pre;


        Class<?>[] types;

        Class[] clazz = new Class[1];

        if (val != null) {
            clazz[0] = val.getClass();
            pre = PRE_SETTER;
        } else {
            pre = PRE_GETTER;
            clazz = null;
        }
        Method meth;
        try {
            meth = stl.getClass().getMethod(pre + upFirst(name), clazz);
        } catch (Exception ex) {
            return null; //invalid method looking attempt
        }
        Object toReturn;

        try {

            if (val != null) {
                toReturn = meth.invoke(stl, val);
            } else {
                toReturn = meth.invoke(stl);
            }
        } catch (Exception ex) {


            toReturn = null;
        }


        if (isDefault(toReturn, meth, val)) {
            return keepInvoking(stl, name, val);
        } else {
            return toReturn;
        }
//NOTE
    }

    private static Object keepInvoking(Design stl, String name, Object val) {
        if (stl instanceof TaskDesign) {
            return invokeOn(TaskDesign.class.cast(stl).getBaseDesign(), name, val);
        } else {
            return null;
        }
    }

    private static String upFirst(String name) {
        String frst = name.substring(0, 1);
        String frstUp = frst.toUpperCase();
        name = name.replaceFirst(frst, frstUp);
        return name;
    }
    private static TaskDesign defTskDes = new TaskDesign();

    private static boolean isDefault(Object toReturn, Method meth, Object val) {
        try {
            if (val != null) {
                return toReturn.equals(meth.invoke(defTskDes, val));
            } else {

                return toReturn.equals(meth.invoke(defTskDes));

            }
        } catch (Exception ex) {
            Logger.getLogger(InferenceUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Given a Getter method java beans style, return the setter associated with it (assuming it has one, otherwise will blow up)
     * @param <E>
     * @param aClass
     * @param method
     * @return 
     */
    public static <E> Method getterFromSetter(Class<E> aClass, Method method) {
        String str = method.getName();
       str = str.replaceFirst(PRE_GETTER, PRE_SETTER);
        try {
            return aClass.getMethod(str, method.getReturnType());
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InferenceUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(InferenceUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
