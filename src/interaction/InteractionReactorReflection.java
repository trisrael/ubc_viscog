package interaction;

import StevensLevel.HasInteractionReactor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 8, 2011
 */
public class InteractionReactorReflection {

    private static ExecutorService serv; //Since interaction can go off causing chaing reactions

    static {
        resetExecutor();
    }

    public static boolean reactAll(Set<InteractionReactor> blacklist, List<InteractionReactor> reactors, ExperimentInteraction e) {
        return reactAll(blacklist, reactors, e, null);

    }

    /**
     *NOTE: This method should never be used, shuts down the thread pool. Causing all work to be completed first
     */
    public static void finishWork() {
        serv.shutdown();
    }

    public static void resetExecutor() {
        serv = newFixedThreadPool(10);
    }

    public static boolean reactAll(Set<InteractionReactor> blacklist, List<InteractionReactor> reactors, ExperimentInteraction e, Object payload) {
        HashSet bools = new HashSet<Boolean>();

        if (blacklist != null) {
            reactors.removeAll(blacklist); //Ensure original 'caller' is not in subsequent calls to avoid infinite recursion
        }

        if (reactors != null) {


            for (InteractionReactor interactionReactor : reactors) {
                blacklist.add(interactionReactor);
                bools.add(reactSingle(blacklist, interactionReactor, e, payload));
            }
        }

        return bools.contains(true);
    }

    /**
     * Given an object and an ExperimentInteraction (and payload) check the obj to see whether it can respond to such events and then invoke the method
     * that deals with such methods if it is available. The method should be able to tell what to do with the payload given.
     * @param blacklist, set of objects that have already been invoked upon or tried, usually includes the original calling object (to stop infinite loops)
     * @param obj, Object to have method invoked upon it with the ExperiementInteraction, and payload
     * @param e, the ExperimenInteraction which occurred
     * @param payload, some object containing additional data corresponding to the ExperimentInteraction
     * @return 
     */
    public static boolean reactSingle(Set<InteractionReactor> blacklist, final InteractionReactor obj, final ExperimentInteraction e, final Object payload) {
        boolean hadAtleastOneMethod = false;

        if (HasInteractionReactor.class.isAssignableFrom(obj.getClass()) && ((HasInteractionReactor) obj).hasInteractionReactor()) { //Walk down InteractionReactor hierarchy (hopefully quite shallow) Caution: Should think about those reactions that might be triggered from a reaction
            hadAtleastOneMethod = reactAll(blacklist, ((HasInteractionReactor) obj).getInteractionReactors(), e, payload);
        }

        if (!hadAtleastOneMethod) { //If lower objects didn't respond to the ExperimentInteraction class in question
            List<Method> methods = retrieveReactTos(obj.getClass(), e.getClass());
            for (final Method method : methods) {
                Runnable worker = new Runnable() {

                    @Override
                    public void run() {

                        try {
                            method.invoke(obj, e, payload);
                        } catch (Exception ex) {
                            Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };

                serv.submit(worker); //execute doesn't work, but submit certainly does
                //If the annotatoin has the specic class that this is Enum is set with then invoke the method with the supplied arguments

                hadAtleastOneMethod = true;
            }
        }

        return hadAtleastOneMethod;
    }

    /**
     * Helper testing whether the Method given is of correct type to be used for 'reaction' inflection.
     * @param meth, the method to be tested
     * @param enumClass, the type of ExperimentInteraction extending class which is expected by a Method wishing to react
     */
    private static boolean isValidReactToMethod(Method meth, Class<? extends ExperimentInteraction> enumClass) {
        ReactTo anno = meth.getAnnotation(ReactTo.class);
        Class<?>[] par_types = meth.getParameterTypes();
        boolean has_two_pars = par_types.length == 2;
        return anno != null && anno.value() == enumClass && has_two_pars && Enum.class.isAssignableFrom(par_types[0]) && par_types[1] == Object.class;
    }

    /**
     * Given class of an InteractionReactor and a specific ExperimentInteraction class, retrieve all methods that would be invoked by a 'sendReaction' call using an object 
     * of this InterationReactor using the ExperimentInteraction given.
     * @param reactor
     * @param intClass
     * @return 
     */
    public static List<Method> retrieveReactTos(Class<? extends InteractionReactor> reactor, Class<? extends ExperimentInteraction> intClass) {
        List<Method> meths = Arrays.asList(reactor.getMethods());
        List<Method> keptMeths = new ArrayList<Method>();

        for (Method method : meths) {
            if (isValidReactToMethod(method, intClass)) {
                keptMeths.add(method);
            }
        }

        return keptMeths;
    }
}