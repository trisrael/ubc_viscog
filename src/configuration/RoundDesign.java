package configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.InferenceUtil;

public class RoundDesign extends TaskDesign {

    private static final RoundDesign defRnd = new RoundDesign();

    /**
     * Takes a RoundDesign and gives back a list of methods that it contains 
     * @param other
     * @return 
     */
    protected List<Method> differenceBetween(RoundDesign other) {
        final List<Method> li = difference();
        return filter(other.difference(), new FilterMethod() {

            @Override
            public boolean keepMethod(RoundDesign dsg, Method meth) {
                for (Method method : li) {
                    if (method == meth) {
                        return false;
                    }
                }
                return true;
            }
        });
    }

    /**
     *Returns a map of which methods have changed from the default
     */
    public List<Method> difference() {
        List<Method> meths = getGetters();
        meths = filter(meths, new FilterMethod() {

            @Override
            public boolean keepMethod(RoundDesign dsg, Method meth) {
                try {
                    return !meth.invoke(dsg).equals(meth.invoke(defRnd));
                } catch (Exception ex) {
                    return false;
                }
            }
        });

        return meths;
    }

    private List<Method> getGetters() {
        Method[] meths = RoundDesign.class.getMethods();
        List<Method> li = Arrays.asList(meths);

        return filter(li, new FilterMethod() {

            @Override
            public boolean keepMethod(RoundDesign dsg, Method method) {
                return !method.getName().equalsIgnoreCase("getBaseDesign") && (method.getName().startsWith(InferenceUtil.PRE_GETTER) && method.getParameterTypes().length == 0);
            }
        });
    }

    /**
     * Takes a RoundDesign and applies properties to current RoundDesign that have not been set yet
     * @param rnd2 
     */
    void merge(RoundDesign other) {
        List<Method> toOverwrite = differenceBetween(other);
        for (Method method : toOverwrite) {
            try {
                InferenceUtil.getterFromSetter(RoundDesign.class, method).invoke(this, method.invoke(other));
            } catch (IllegalAccessException ex) {
                Logger.getLogger(RoundDesign.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(RoundDesign.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(RoundDesign.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    interface FilterMethod {

        /**
         * Simple function which decided whether a function should be kept or thrown away
         * @param dsg, current rounddesign
         * @param meth
         * @return 
         */
        boolean keepMethod(RoundDesign dsg, Method meth);
    }

    /**
     * Given a list of Methods and a Filterer, keep only those Methods that pass the keepMethod calling
     * @param meths
     * @param filterer
     * @return 
     */
    private List<Method> filter(List<Method> meths, FilterMethod filterer) {
        List<Method> filtered = new ArrayList<Method>();
        for (Method method : meths) {
            if (filterer.keepMethod(this, method)) {
                filtered.add(method);
            }
        }

        return filtered;
    }

    /**
     * Equality is based on qualities of an object itself not those of simply its hashcode...(default behaviour)
     * @param des
     * @return 
     */
    @Override
    public boolean equals(Object des) {
        if (des instanceof RoundDesign) {
            RoundDesign rdes = RoundDesign.class.cast(des);
            return differenceBetween(rdes).size() > 0;
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "";
        for (Method meth : difference()) {
            res += meth.getName() + ",";
        }

        return res;
    }
}