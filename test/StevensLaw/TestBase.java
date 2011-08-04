/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLaw;

import java.lang.reflect.Field;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 3, 2011
 */
public class TestBase {
    
    //Helpers
    
    
    public static void hasFieldWith(Class<?> clazz) throws SecurityException {
        Field[] fields = ExperimentControl.class.getDeclaredFields();
        boolean hasField = false;
        //NOTE: with time make a matcher for this
        for (Field field : fields) {

            if (field.getType().equals(clazz)) {
                hasField = true;
            }; //One or more.. might want to change to singleton in future
        }

        assertTrue(hasField);
    }
    
}
