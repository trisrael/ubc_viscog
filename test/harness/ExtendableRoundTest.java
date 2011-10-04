/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harness;

import StevensLevel.parts.Round;
import configuration.BaseDesign;
import configuration.TaskDesign;
import org.junit.Before;
import static StevensLevel.EventBusHelper.*;
/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 14, 2011
 */
public class ExtendableRoundTest extends TestHarness<Round> {
    
        @Before
        public void setUp() throws InstantiationException, IllegalAccessException {
        TaskDesign tsk = new TaskDesign();
                    tsk.setBaseDesign(new BaseDesign());
                     setupEventBus();
        Round obj = new Round(tsk);
        
            setInstance(obj);
        }

        public Class<?> getClazz() {
            return Round.class;
        }
}
