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

package org.yaml.snakeyaml.constructor;

import java.io.IOException;

import junit.framework.TestCase;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

public class IncompleteBeanConstructorTest extends TestCase {

    public void testRepresentor() throws IOException {
        IncompleteJavaBean bean = new IncompleteJavaBean();
        DumperOptions options = new DumperOptions();
        options.setAllowReadOnlyProperties(true);
        Yaml yaml = new Yaml(options);
        String output = yaml.dump(bean);
        String className = this.getClass().getPackage().getName();
        assertEquals("!!" + className + ".IncompleteJavaBean {name: No name}\n", output);
    }

    public void testConstructor() throws IOException {
        String className = "!!" + this.getClass().getPackage().getName()
                + ".IncompleteJavaBean {number: 2}";
        Yaml yaml = new Yaml();
        IncompleteJavaBean bean = (IncompleteJavaBean) yaml.load(className);
        assertNotNull(bean);
        assertEquals("No name", bean.getName());
        assertEquals(2, bean.obtainNumber());
    }

    public void testConstructor2() throws IOException {
        String className = "!!" + this.getClass().getPackage().getName()
                + ".IncompleteJavaBean {number: 2, name: Bill}";
        Yaml yaml = new Yaml();
        try {
            yaml.load(className);
            fail("'name' property does not have setter.");
        } catch (YAMLException e) {
            assertEquals(
                    "Cannot create property=name for JavaBean=<IncompleteJavaBean name=No name>; Unable to find property 'name' on class: org.yaml.snakeyaml.constructor.IncompleteJavaBean",
                    e.getCause().getMessage());
        }
    }
}
