/*
 * MIT License
 *
 * Copyright (c) 2024 KH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package keyhub.data.structure.converter;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Utility class for converting objects to maps.
 */
public class KhObjectConverter {

    /**
     * Converts an object to a map where the keys are the field names and the values are the field values.
     *
     * @param object the object to convert
     * @return a map representation of the object
     * @throws RuntimeException if the object fields cannot be accessed
     */
    public static Map<String, Object> convertToMap(Object object) {
        Map<String, Object> map = new WeakHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to convert object to map", e);
            }
        }
        return map;
    }

    /**
     * Converts a list of objects to a list of maps.
     *
     * @param objectList the list of objects to convert
     * @return a list of map representations of the objects
     */
    public static List<Map<String, Object>> convertToMapList(List<?> objectList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object object : objectList) {
            mapList.add(convertToMap(object));
        }
        return mapList;
    }
}