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

package keyhub.data.tbl;

import keyhub.data.tbl.operator.TblOperatorType;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TbTest {
    @Nested
    class ConstructorTest{
        @Test
        public void testOfMethodWithRowMapList() throws IllegalAccessException {
            List<Map<String, Object>> rowMapList = new ArrayList<>();
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("key1", "value1");
            rowMap.put("key2", 2);
            rowMapList.add(rowMap);

            Tbl result = Tbl.from(rowMapList);

            System.out.println(result);
            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }

        @Test
        public void testOfMethodWithColumnListMap() {
            Map<String, List<Object>> columnListMap = new HashMap<>();
            List<Object> columnList1 = new ArrayList<>();
            columnList1.add("value1");
            columnList1.add("value2");
            columnList1.add("value3");
            columnListMap.put("key1", columnList1);

            List<Object> columnList2 = new ArrayList<>();
            columnList2.add(1);
            columnList2.add(2);
            columnList2.add(3);
            columnListMap.put("key2", columnList2);

            Tbl result = Tbl.fromColumnListMap(columnListMap);

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

            List<TblColumnSchema> columnSchemas = new ArrayList<>();
            columnSchemas.add(TblColumnSchema.of("key1", String.class));
            columnSchemas.add(TblColumnSchema.of("key2", Integer.class));
            TblSchema schema = TblSchema.of(columnSchemas);

            Tbl result = Tbl.of(schema, data);

            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }

        @Test
        public void testBuilderMethod() {
            List<TblColumnSchema> columnSchemas = new ArrayList<>();
            columnSchemas.add(TblColumnSchema.of("key1", String.class));
            columnSchemas.add(TblColumnSchema.of("key2", Integer.class));
            TblSchema schema = TblSchema.of(columnSchemas);

            Tbl result = Tbl.builder(schema)
                    .addRow(TblRow.of(schema, "value1", 1))
                    .addRow(TblRow.of(schema, "value2", 1))
                    .build();

            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }
    }

    @Nested
    class ToColumnListMapTest{
        @Test
        void testToColumnListMapMethod() {
            List<TblColumnSchema> schemas = List.of(
                    TblColumnSchema.of("column1", String.class),
                    TblColumnSchema.of("column2", String.class),
                    TblColumnSchema.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.of(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            Map<String, List<Object>> result = tblInstance.toColumnListMap();

            // Assertions
            assertEquals(3, result.size());
            assertEquals(Arrays.asList("A", "D", "G"), result.get("column1"));
            assertEquals(Arrays.asList("B", "E", "H"), result.get("column2"));
            assertEquals(Arrays.asList("C", "F", "I"), result.get("column3"));
        }
    }

    @Nested
    class ToRowMapListTest{
        @Test
        void testToRowMapListMethod() {
            List<TblColumnSchema> schemas = List.of(
                    TblColumnSchema.of("column1", String.class),
                    TblColumnSchema.of("column2", String.class),
                    TblColumnSchema.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.of(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            List<Map<String, Object>> result = tblInstance.toRowMapList();

            // Assertions
            assertEquals(3, result.size());
            assertEquals("A", result.get(0).get("column1"));
            assertEquals("B", result.get(0).get("column2"));
            assertEquals("C", result.get(0).get("column3"));
        }
    }

    @Nested
    class SelectTest {
        @Test
        void testSelectMethod() {
            List<TblColumnSchema> schemas = List.of(
                    TblColumnSchema.of("column1", String.class),
                    TblColumnSchema.of("column2", String.class),
                    TblColumnSchema.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.of(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            // Select specific columns
            Tbl resultTbl = tblInstance.select("column1", "column2");

            // Assertions
            assertEquals(2, resultTbl.getSchema().getColumnSize());
            assertEquals(3, resultTbl.count());

            List<String> expectedColumns = Arrays.asList("column1", "column2");
            assertEquals(expectedColumns, resultTbl.getColumns());

            String expectedFirstRowFirstColumn = "A";
            assertEquals(expectedFirstRowFirstColumn, resultTbl.getRow(0).findCell("column1").orElseThrow());

            String expectedFirstRowSecondColumn = "B";
            assertEquals(expectedFirstRowSecondColumn, resultTbl.getRow(0).findCell("column2").orElseThrow());
        }
    }

    @Nested
    class WhereTest{
        @Test
        void testWhereMethod() {
            List<TblColumnSchema> schemas = List.of(
                    TblColumnSchema.of("column1", String.class),
                    TblColumnSchema.of("column2", String.class),
                    TblColumnSchema.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.of(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            // Where
            Tbl resultTbl = tblInstance.where("column1", TblOperatorType.EQUAL,"A");

            // Assertions
            assertEquals(1, resultTbl.count());
            assertEquals(3, resultTbl.getSchema().getColumnSize());
        }
    }
}