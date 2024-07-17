package keyhub.data.simpleimplement;

import keyhub.data.tbl.Tbl;
import keyhub.data.join.InnerJoinSet;
import keyhub.data.join.JoinSet;
import keyhub.data.join.LeftJoinSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TblValueTest {

    @Nested
    class SelectTest{
        @Test
        public void testSelectAll() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(data1)
                    .addRow(data2)
                    .addRow(data3)
                    .build();

            Tbl result = tbl.selectAll();
            assertEquals(2, result.getColumns().size());
            assertEquals(3, result.getRows().size());
            assertEquals(2, result.getRows().getFirst().size());
        }

        @Test
        public void testSelect(){
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(data1)
                    .addRow(data2)
                    .addRow(data3)
                    .build();

            Tbl result = tbl.select("id");
            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            assertEquals(1, result.getColumns().size());
            assertEquals(3, result.getRows().size());
            assertEquals(1, result.getRows().getFirst().size());
        }


    }

    @Nested
    class WhereTest {
        @Test
        public void testWhereEquals() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(data1)
                    .addRow(data2)
                    .addRow(data3)
                    .build();

            tbl.where("id", "==", 2);
            System.out.println(tbl.getRows());
            System.out.println(tbl.getRow(0));
            assertEquals(1, tbl.getRows().size());
            assertEquals(2, tbl.getRow(0).getFirst());
        }

        @Test
        public void testWhereEquals2() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(data1)
                    .addRow(data2)
                    .addRow(data3)
                    .build();

            tbl.where("name", "==", "bbb");
            System.out.println(tbl.getRows());
            System.out.println(tbl.getRow(0));
            assertEquals(1, tbl.getRows().size());
            assertEquals(2, tbl.getRow(0).getFirst());
        }

        @Test
        public void testWhereNotEquals() {
            List<String> columns = List.of("id", "name");
            List<Object> row1 = Arrays.asList(1, "aaa");
            List<Object> row2 = Arrays.asList(2, "bbb");
            List<Object> row3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(row1)
                    .addRow(row2)
                    .addRow(row3)
                    .build();

            tbl.where("name", "!=", "bbb");
            assertEquals(2, tbl.getRows().size());
            assertTrue(tbl.getRows().stream().noneMatch(row -> row.get(1).equals("bbb")));
        }

        @Test
        public void testWhereGreater() {
            List<String> columns = List.of("id", "name");
            List<Object> row1 = Arrays.asList(1, "aaa");
            List<Object> row2 = Arrays.asList(2, "bbb");
            List<Object> row3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(row1)
                    .addRow(row2)
                    .addRow(row3)
                    .build();

            tbl.where("id", ">", 1);
            assertEquals(2, tbl.getRows().size());
            assertTrue(tbl.getRows().stream().allMatch(row -> (int) row.getFirst() > 1));
        }

        @Test
        public void testWhereLess() {
            List<String> columns = List.of("id", "name");
            List<Object> row1 = Arrays.asList(1, "aaa");
            List<Object> row2 = Arrays.asList(2, "bbb");
            List<Object> row3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(row1)
                    .addRow(row2)
                    .addRow(row3)
                    .build();

            tbl.where("id", "<", 3);

            assertEquals(2, tbl.getRows().size());
            assertTrue(tbl.getRows().stream().allMatch(row -> (int) row.getFirst() < 3));

        }
    }

    @Nested
    class InnerJoinTest {
        @Test
        @DisplayName("Test Inner Join Success")
        public void testInnerJoin(){
            Tbl left = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRow(List.of(1, "Alice"))
                    .addRow(List.of(2, "Bob"))
                    .addRow(List.of(3, "Charlie"))
                    .build();

            Tbl right = Tbl.builder()
                    .addColumns(List.of("id", "age"))
                    .addRow(List.of(1, 20))
                    .addRow(List.of(3, 30))
                    .addRow(List.of(4, 40))
                    .build();

            JoinSet joinSet = InnerJoinSet.of(left, right)
                    .on("id")
                    .selectAll();

            Tbl result = joinSet.toTbl();

            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            // 4
            // [id, name, id, age]
            assertEquals(left.getColumnSize()+ right.getColumnSize(), result.getColumns().size());
            // 2
            // [[1, Alice, 1, 20], [3, Charlie, 3, 30]]
            assertEquals(2, result.getRows().size());
            // 4
            assertEquals(4, result.getRow(0).size());
        }

    }

    @Nested
    class LeftJoinTest {
        @Test
        @DisplayName("Test Left Join Success")
        public void testLeftJoin(){
            Tbl left = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRow(List.of(1, "Alice"))
                    .addRow(List.of(2, "Bob"))
                    .addRow(List.of(3, "Charlie"))
                    .build();

            Tbl right = Tbl.builder()
                    .addColumns(List.of("id", "age"))
                    .addRow(List.of(1, 20))
                    .addRow(List.of(3, 30))
                    .addRow(List.of(4, 40))
                    .build();

            JoinSet joinSet = LeftJoinSet.of(left, right)
                    .on("id")
                    .selectAll();

            Tbl result = joinSet.toTbl();

            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            // 4
            // [id, name, id, age]
            assertEquals(left.getColumnSize()+ right.getColumnSize(), result.getColumns().size());
            // 3
            // [[1, Alice, 1, 20], [2, Bob, null, null], [3, Charlie, 3, 30]]
            assertEquals(left.size(), result.getRows().size());
            // 4
            assertEquals(4, result.getRow(0).size());
        }

    }

    @Nested
    class ToRowMapListTest {
        @Test
        public void testToRowMapList() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(data1)
                    .addRow(data2)
                    .addRow(data3)
                    .build();

            List<Map<String, Object>> result = tbl.toRowMapList();
            System.out.println(tbl.getColumns());
            System.out.println(tbl.getRows());
            System.out.println(result);

            assertEquals(3, result.size());
            assertEquals(1, result.get(0).get("id"));
            assertEquals("aaa", result.get(0).get("name"));
            assertEquals(2, result.get(1).get("id"));
            assertEquals("bbb", result.get(1).get("name"));
            assertEquals(3, result.get(2).get("id"));
            assertEquals("ccc", result.get(2).get("name"));
        }
    }

    @Nested
    class ToColumnMapListTest {
        @Test
        public void testToColumnMapList() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.builder()
                    .addColumns(columns)
                    .addRow(data1)
                    .addRow(data2)
                    .addRow(data3)
                    .build();

            Map<String, List<Object>> result = tbl.toColumnListMap();
            System.out.println(tbl.getColumns());
            System.out.println(tbl.getRows());
            System.out.println(result);

            assertEquals(2, result.size());
            assertEquals(List.of(1, 2, 3), result.get("id"));
            assertEquals(List.of("aaa", "bbb", "ccc"), result.get("name"));
        }
    }
}
