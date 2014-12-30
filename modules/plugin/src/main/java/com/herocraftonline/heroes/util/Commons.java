package com.herocraftonline.heroes.util;
/*
 * Original code copyright Apache Software Foundation
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * The license for this file does not extend to the rest of the program, nor does the license for the rest of the
 * program apply to this file
 */

import java.lang.reflect.Array;

/**
 * Utility methods from the Apache Commons library, reproduced here for consistency purposes
 * as well as reduction in build complexity
 */
public class Commons {

    public static String[] addAll(String[] array1, String[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        String[] joinedArray = (String[]) Array.newInstance(array1.getClass().getComponentType(),
                array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class type1 = array1.getClass().getComponentType();
            final Class type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "+type1.getName());
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }

    public static String[] clone(String[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
}
