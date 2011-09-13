package interaction;

import StevensLaw.HasInteractionReactor;
import java.lang.Class;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 8, 2011
 */
public class InteractionReactorReflection {
    
    
    public static void reactAll(InteractionReactor obj, ExperimentInteraction e){
        reactAll(obj, e, null);
    }
    
    public static boolean reactAll(InteractionReactor obj, ExperimentInteraction e, Object payload){
        boolean hadAtleastOneMethod = false;
        
        if(HasInteractionReactor.class.isAssignableFrom(obj.getClass()) && ((HasInteractionReactor) obj).hasInteractionReactor()){ //Walk down InteractionReactor hierarchy (hopefully quite shallow) Caution: Should think about those reactions that might be triggered from a reaction
            hadAtleastOneMethod =  reactAll(((HasInteractionReactor)obj).getInteractionReactor(), e, payload);
        }
        
        if(!hadAtleastOneMethod){ //If lower objects didn't respond to the ExperimentInteraction class in question
            Class<? extends InteractionReactor> clazz = obj.getClass();
        Method[] meths = clazz.getMethods();
        
        for (int i = 0; i < meths.length; i++) {
            Method meth = meths[i];
            ReactTo anno = meth.getAnnotation(ReactTo.class);
            Class<?>[] par_types = meth.getParameterTypes();
            boolean has_two_pars = par_types.length == 2;
            
            //If the method is annotated with class of Enum 'reacting' and the two parameter types are correct, invoke the method
            if(anno.value() == clazz && has_two_pars && ExperimentInteraction.class.isAssignableFrom(par_types[0]) && par_types[1] == Object.class){ 
                
                try {
                    //If the annotatoin has the specic class that this is Enum is set with then invoke the method with the supplied arguments
                    meth.invoke(obj, e, payload);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(InteractionReactorReflection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }   
        }
        
     return hadAtleastOneMethod;
    }
}
