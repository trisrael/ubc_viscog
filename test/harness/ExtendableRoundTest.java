/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harness;

import StevensLaw.parts.Round;
import org.junit.Before;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Aug 14, 2011
 */
public class ExtendableRoundTest extends TestHarness<Round> {
    
        @Before
        public void setUp() throws InstantiationException, IllegalAccessException {
            setInstance(new Round(1, 0));
        }

        public Class<?> getClazz() {
            return Round.class;
        }
}