package interaction;

import StevensLaw.HasInteractionReactor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.print.resources.serviceui;

/**
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 8, 2011
 */
public class InteractionReactorReflection {

    private static ExecutorService serv = newFixedThreadPool(10); //Since interaction can go off causing chaing reactions

    public static void reactAll(InteractionReactor obj, ExperimentInteraction e) {
        reactAll(obj, e, null);
    }

    /**
     * Given an object and an ExperimentInteraction (and payload) check the obj to see whether it can respond to such events and then invoke the method
     * that deals with such methods if it is available. The method should be able to tell what to do with the payload given.
     * @param obj, Object to have method invoked upon it with the ExperiementInteraction, and payload
     * @param e, the ExperimenInteraction which occurred
     * @param payload, some object containing additional data corresponding to the ExperimentInteraction
     * @return 
     */
    public static boolean reactAll(final InteractionReactor obj, final ExperimentInteraction e, final Object payload) {
        boolean hadAtleastOneMethod = false;

        if (HasInteractionReactor.class.isAssignableFrom(obj.getClass()) && ((HasInteractionReactor) obj).hasInteractionReactor()) { //Walk down InteractionReactor hierarchy (hopefully quite shallow) Caution: Should think about those reactions that might be triggered from a reaction
            hadAtleastOneMethod = reactAll(((HasInteractionReactor) obj).getInteractionReactor(), e, payload);
        }

        if (!hadAtleastOneMethod) { //If lower objects didn't respond to the ExperimentInteraction class in question
            Class<? extends InteractionReactor> clazz = obj.getClass();
            Method[] meths = clazz.getMethods();

            for (int i = 0; i < meths.length; i++) {
                final Method meth = meths[i];
                ReactTo anno = meth.getAnnotation(ReactTo.class);
                Class<?>[] par_types = meth.getParameterTypes();
                boolean has_two_pars = par_types.length == 2;

                //If the method is annotated with class of Enum 'reacting' and the two parameter types are correct, invoke the method
                if (anno != null && anno.value() == e.getClass() && has_two_pars && Enum.class.isAssignableFrom(par_types[0]) && par_types[1] == Object.class) {


                    serv.execute(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                meth.invoke(obj, e, payload);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    
                    
                    //If the annotatoin has the specic class that this is Enum is set with then invoke the method with the supplied arguments

                    hadAtleastOneMethod = true;

                }
            }
        }

        return hadAtleastOneMethod;
    }
}
