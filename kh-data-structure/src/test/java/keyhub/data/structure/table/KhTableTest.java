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

package keyhub.data.structure.table;

import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.function.KhCellSelector;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.schema.KhSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static keyhub.data.structure.function.KhColumnSelector.column;
import static keyhub.data.structure.function.KhRowPredicate.is;
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

    @Nested
    class KhTableQueryTest {

        @Test
        public void testTblStream(){
            KhSchema schema = KhSchema.from(List.of(
                    KhColumn.of("name", String.class),
                    KhColumn.of("age", Integer.class),
                    KhColumn.of("height", Double.class),
                    KhColumn.of("weight", Float.class)
            ));
            KhTable tbl = KhTable.of(schema, List.of(
                    List.of("Alice", 20, 160.0, 50.0f),
                    List.of("Bob", 30, 170.0, 60.0f),
                    List.of("Charlie", 40, 180.0, 70.0f)
            ));

            KhTable result = tbl.query()
                    .select("name", "age", "height")
                    .select(
                            KhColumn.of("name", String.class),
                            KhColumn.of("height", String.class),
                            KhColumn.of("age", Integer.class)
                    )
                    .select(
                            KhCellSelector.column("name"),
                            KhCellSelector.column("age")
                    )
                    .where(is("age", age -> (Integer) age.getValue() > 20))
                    .toTbl();
            System.out.println(result);
            assertEquals(2, result.count());
        }
    }

    @Nested
    class TblInnerJoinTest {

        KhTable mockTbl1;
        KhTable mockTbl2;

        @BeforeEach
        public void before(){
            KhSchema mockSchema = KhSchema.builder()
                    .addColumn("column1", String.class)
                    .addColumn("column3", Integer.class)
                    .addColumn("column2", LocalDateTime.class)
                    .build();
            mockTbl1 = KhTable.builder(mockSchema)
                    .addRawRow(List.of("value1", 1, LocalDateTime.of(2021, 1, 1, 0, 0)))
                    .addRawRow(List.of("value2", 2, LocalDateTime.of(2021, 1, 2, 0, 0)))
                    .addRawRow(Arrays.asList("value3", 3, LocalDateTime.of(2021, 1, 3, 0, 0)))
                    .build();
            KhSchema mockSchema2 = KhSchema.builder()
                    .addColumn("column3", Integer.class)
                    .addColumn("column4", String.class)
                    .build();
            mockTbl2 = KhTable.builder(mockSchema2)
                    .addRawRow(List.of(1, "A"))
                    .addRawRow(List.of(2, "B"))
                    .build();
        }

        @Test
        void testInnerJoinSuccess() {
            KhTable left = mockTbl1;
            KhTable right = mockTbl2;

            KhTable result = left.innerJoin(right)
                    .on("column3")
                    .selectAll()
                    .toOne();

            Map<String, List<Object>> expected = new HashMap<>();
            expected.put("column1", Arrays.asList("value1", "value2"));
            expected.put("column2", Arrays.asList(
                    LocalDateTime.of(2021, 1, 1, 0, 0),
                    LocalDateTime.of(2021, 1, 2, 0, 0)
            ));
            expected.put("column3", Arrays.asList(1, 2));
            expected.put("column4", Arrays.asList("A", "B"));
            Assertions.assertEquals(expected, result.toColumnListMap());
        }

        @Test
        void testInnerJoinByTblCellSelector(){
            KhTable left = mockTbl1;
            KhTable right = mockTbl2;

            KhTable result = left.innerJoin(right)
                    .on("column3")
                    .selectFromLeft(
                            column("column1"),
                            column("column2")
                    )
                    .selectFromRight(
                            column("column3"),
                            column("column4")
                    )
                    .toOne();

            Map<String, List<Object>> expected = new HashMap<>();
            expected.put("column1", Arrays.asList("value1", "value2"));
            expected.put("column2", Arrays.asList(
                    LocalDateTime.of(2021, 1, 1, 0, 0),
                    LocalDateTime.of(2021, 1, 2, 0, 0)
            ));
            expected.put("column3", Arrays.asList(1, 2));
            expected.put("column4", Arrays.asList("A", "B"));
            Assertions.assertEquals(expected, result.toColumnListMap());
        }
    }

    @Nested
    class TblLeftJoinTest {

        KhTable mockTbl1;
        KhTable mockTbl2;

        @BeforeEach
        public void before(){
            KhSchema mockSchema = KhSchema.builder()
                    .addColumn("column1", String.class)
                    .addColumn("column3", Integer.class)
                    .addColumn("column2", LocalDateTime.class)
                    .build();
            mockTbl1 = KhTable.builder(mockSchema)
                    .addRawRow(List.of("value1", 1, LocalDateTime.of(2021, 1, 1, 0, 0)))
                    .addRawRow(List.of("value2", 2, LocalDateTime.of(2021, 1, 2, 0, 0)))
                    .addRawRow(Arrays.asList("value3", 3, LocalDateTime.of(2021, 1, 3, 0, 0)))
                    .build();
            KhSchema mockSchema2 = KhSchema.builder()
                    .addColumn("column3", Integer.class)
                    .addColumn("column4", String.class)
                    .build();
            mockTbl2 = KhTable.builder(mockSchema2)
                    .addRawRow(List.of(1, "A"))
                    .addRawRow(List.of(2, "B"))
                    .build();
        }

        @Test
        void testLeftJoinSuccess() {
            KhTable left = mockTbl1;
            KhTable right = mockTbl2;

            KhTable result = left.leftJoin(right)
                    .on("column3")
                    .selectAll()
                    .toOne();

            Map<String, List<Object>> expected = new HashMap<>();
            expected.put("column1", Arrays.asList("value1", "value2", "value3"));
            expected.put("column2", Arrays.asList(
                    LocalDateTime.of(2021, 1, 1, 0, 0),
                    LocalDateTime.of(2021, 1, 2, 0, 0),
                    LocalDateTime.of(2021, 1, 3, 0, 0))
            );
            expected.put("column3", Arrays.asList(1, 2, null));
            expected.put("column4", Arrays.asList("A", "B", null));
            Assertions.assertEquals(expected, result.toColumnListMap());
        }


        @Test
        void testInnerJoinByTblCellSelector(){
            KhTable left = mockTbl1;
            KhTable right = mockTbl2;

            KhTable result = left.leftJoin(right)
                    .on("column3")
                    .selectFromLeft(
                            column("column1"),
                            column("column2")
                    )
                    .selectFromRight(
                            column("column4")
                    )
                    .toOne();

            Map<String, List<Object>> expected = new HashMap<>();
            expected.put("column1", Arrays.asList("value1", "value2", "value3"));
            expected.put("column2", Arrays.asList(
                    LocalDateTime.of(2021, 1, 1, 0, 0),
                    LocalDateTime.of(2021, 1, 2, 0, 0),
                    LocalDateTime.of(2021, 1, 3, 0, 0))
            );
            expected.put("column4", Arrays.asList("A", "B", null));
            Assertions.assertEquals(expected, result.toColumnListMap());
        }

    }

}