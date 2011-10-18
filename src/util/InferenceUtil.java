/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;
import common.condition.DotHueType;
import common.condition.DotStyleType;
import configuration.Design;
import configuration.TaskDesign;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hamcrest.SelfDescribing;
import org.mockito.internal.util.ArrayUtils;

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
}
