/**
 * Copyright (c) 2008-2011, http://www.snakeyaml.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yaml.snakeyaml.stress;

import java.io.IOException;

import org.yaml.snakeyaml.Invoice;
import org.yaml.snakeyaml.JavaBeanDumper;
import org.yaml.snakeyaml.JavaBeanLoader;
import org.yaml.snakeyaml.Util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StressEmitterTest extends TestCase {

    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(StressEmitterTest.class);
    }

    public void testPerformance() throws IOException {
        JavaBeanLoader<Invoice> loader = new JavaBeanLoader<Invoice>(Invoice.class);
        Invoice invoice = loader.load(Util.getLocalResource("specification/example2_27.yaml"));
        JavaBeanDumper dumper = new JavaBeanDumper();
        long time1 = System.nanoTime();
        dumper.dump(invoice);
        long time2 = System.nanoTime();
        float duration = (time2 - time1) / 1000000;
        System.out.println("\nSingle dump was " + duration + " ms.");

        int[] range = new int[] { 1000, 2000 /* , 8000 */};
        System.out.println("\nOne instance.");
        for (int number : range) {
            time1 = System.nanoTime();
            for (int i = 0; i < number; i++) {
                dumper.dump(invoice);
            }
            time2 = System.nanoTime();
            duration = ((time2 - time1) / 1000000) / (float) number;
            System.out.println("Duration for r=" + number + " was " + duration + " ms/dump.");
            // cobertura may make it very slow
            if (duration > 3) {
                System.err.println("!!!!!! Too long. Expected <1 but was " + duration);
            }
        }

        System.out.println("\nMany instances.");
        for (int number : range) {
            time1 = System.nanoTime();
            for (int i = 0; i < number; i++) {
                dumper = new JavaBeanDumper();
                dumper.dump(invoice);
            }
            time2 = System.nanoTime();
            duration = ((time2 - time1) / 1000000) / (float) number;
            System.out.println("Duration for r=" + number + " was " + duration + " ms/dump.");
            // cobertura may make it very slow
            if (duration > 3) {
                System.err.println("!!!!!! Too long. Expected <1 but was " + duration);
            }
            // assertTrue("duration=" + duration, duration < 3);
        }
    }
}