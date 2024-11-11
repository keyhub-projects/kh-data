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

package keyhub.data.table;

import keyhub.data.column.KhColumn;
import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KhTableTest {
    @Nested
    class ConstructorTest{
        @Test
        public void testOfMethodWithRowMapList() throws IllegalAccessException {
            List<Map<String, Object>> rowMapList = new ArrayList<>();
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("key1", "value1");
            rowMap.put("key2", 2);
            rowMapList.add(rowMap);

            KhTable result = KhTable.from(rowMapList);

            System.out.println(result);
            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }

        @Test
        public void testOfMethodWithSchemaAndData() {
            List<List<Object>> data = new ArrayList<>();
            List<Object> row1 = new ArrayList<>();
            row1.add("value1");
            row1.add(1);
            data.add(row1);

            List<Object> row2 = new ArrayList<>();
            row2.add("value2");
            row2.add(2);
            data.add(row2);

            List<KhColumn> columnSchemas = new ArrayList<>();
            columnSchemas.add(KhColumn.of("key1", String.class));
            columnSchemas.add(KhColumn.of("key2", Integer.class));
            KhSchema schema = KhSchema.from(columnSchemas);

            KhTable result = KhTable.of(schema, data);

            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }

        @Test
        public void testBuilderMethod() {
            List<KhColumn> columnSchemas = new ArrayList<>();
            columnSchemas.add(KhColumn.of("key1", String.class));
            columnSchemas.add(KhColumn.of("key2", Integer.class));
            KhSchema schema = KhSchema.from(columnSchemas);

            KhTable result = KhTable.builder(schema)
                    .addRow(KhRow.of(schema, "value1", 1))
                    .addRow(KhRow.of(schema, "value2", 1))
                    .build();

            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }
    }

    @Nested
    class ToKhColumnListMapTest {
        @Test
        void testToColumnListMapMethod() {
            List<KhColumn> schemas = List.of(
                    KhColumn.of("column1", String.class),
                    KhColumn.of("column2", String.class),
                    KhColumn.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            KhSchema schema = KhSchema.from(schemas);
            KhTable tblInstance = KhTable.of(schema, inputData);

            Map<String, List<Object>> result = tblInstance.toColumnListMap();

            // Assertions
            assertEquals(3, result.size());
            assertEquals(Arrays.asList("A", "D", "G"), result.get("column1"));
            assertEquals(Arrays.asList("B", "E", "H"), result.get("column2"));
            assertEquals(Arrays.asList("C", "F", "I"), result.get("column3"));
        }
    }

    @Nested
    class ToKhRowMapListTest {
        @Test
        void testToRowMapListMethod() {
            List<KhColumn> schemas = List.of(
                    KhColumn.of("column1", String.class),
                    KhColumn.of("column2", String.class),
                    KhColumn.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            KhSchema schema = KhSchema.from(schemas);
            KhTable tblInstance = KhTable.of(schema, inputData);

            List<Map<String, Object>> result = tblInstance.toRowMapList();

            // Assertions
            assertEquals(3, result.size());
            assertEquals("A", result.get(0).get("column1"));
            assertEquals("B", result.get(0).get("column2"));
            assertEquals("C", result.get(0).get("column3"));
        }
    }

}