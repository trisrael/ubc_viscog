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

package org.yaml.snakeyaml.generics;

import java.beans.IntrospectionException;

import junit.framework.TestCase;

import org.yaml.snakeyaml.JavaBeanDumper;
import org.yaml.snakeyaml.JavaBeanLoader;
import org.yaml.snakeyaml.Util;
import org.yaml.snakeyaml.Yaml;

public class GenericArrayTypeTest extends TestCase {

    public void testPrimitives() {
        GenericPrimitivesArray ga = new GenericPrimitivesArray(3);
        Yaml yaml = new Yaml();
        try {
            yaml.dump(ga);
            fail("Arrays of primitives are not yet fully supported.");
        } catch (Exception e) {
            assertEquals("Arrays of primitives are not fully supported.", e.getMessage());
        }
    }

    public static class GenericPrimitivesArray extends AbstractAnimal<int[]> {
        private int[] home;

        public GenericPrimitivesArray(int count) {
            home = new int[count];
            for (int i = 0; i < home.length; i++) {
                home[i] = i + 1;
            }
        }

        @Override
        public int[] getHome() {
            return home;
        }

        @Override
        public void setHome(int[] home) {
            this.home = home;
        }
    }

    public void testClasses() throws IntrospectionException {
        GenericArray ga = new GenericArray();
        Yaml yaml = new Yaml();
        String doc = yaml.dump(ga);
        // System.out.println(doc);
        String etalon = "!!org.yaml.snakeyaml.generics.GenericArrayTypeTest$GenericArray\n"
                + "home: [1, 2, 3]\n" + "name: Array3\n";
        assertEquals(etalon, doc);
        if (GenericsBugDetector.isProperIntrospection()) {
            GenericArray parsed = (GenericArray) yaml.load(doc);
            assertEquals("Array3", parsed.getName());
            assertEquals(3, parsed.getHome().length);
        } else {
            try {
                yaml.load(doc);
            } catch (Exception e) {
                // TODO Check GenericArrayType
                String message = "Cannot create property=home for JavaBean=org.yaml.snakeyaml.generics.GenericArrayTypeTest$GenericArray";
                assertTrue(e.getMessage(), e.getMessage().contains(message));
            }
        }
    }

    public static class GenericArray extends AbstractAnimal<Integer[]> {
        private Integer[] home;

        public GenericArray() {
            home = new Integer[3];
            for (int i = 0; i < home.length; i++) {
                home[i] = i + 1;
            }
            setName("Array" + String.valueOf(3));
        }

        @Override
        public Integer[] getHome() {
            return home;
        }

        @Override
        public void setHome(Integer[] home) {
            this.home = home;
        }
    }

    public void testJavaBean() throws IntrospectionException {
        GenericArray ga = new GenericArray();
        ArrayBean bean = new ArrayBean();
        bean.setId("ID556677");
        bean.setGa(ga);
        JavaBeanDumper dumper = new JavaBeanDumper();
        String doc = dumper.dump(bean);
        // System.out.println(doc);
        assertEquals(Util.getLocalResource("javabeans/genericArray-1.yaml"), doc);
        //
        JavaBeanLoader<ArrayBean> beanLoader = new JavaBeanLoader<ArrayBean>(ArrayBean.class);
        if (GenericsBugDetector.isProperIntrospection()) {
            ArrayBean loaded = beanLoader.load(doc);
            assertEquals("ID556677", loaded.getId());
            assertEquals("Array3", loaded.getGa().getName());
            assertEquals(3, loaded.getGa().getHome().length);
        } else {
            try {
                beanLoader.load(doc);
            } catch (Exception e) {
                // TODO Check GenericArrayType
                String message = "Cannot create property=home for JavaBean=org.yaml.snakeyaml.generics.GenericArrayTypeTest$GenericArray";
                assertTrue(e.getMessage(), e.getMessage().contains(message));
            }
        }
    }

    public static class ArrayBean {
        private String id;
        private GenericArray ga;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public GenericArray getGa() {
            return ga;
        }

        public void setGa(GenericArray ga) {
            this.ga = ga;
        }
    }
}
