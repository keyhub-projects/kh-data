package keyhub.data.tbl;

import keyhub.data.tbl.join.InnerTblJoin;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.join.LeftTblJoin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TblVariableTest {

    @Nested
    class SelectTest{
        @Test
        public void testSelectAll() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.of(columns, List.of(data1, data2, data3));

            Tbl result = tbl.selectAll();
            assertEquals(2, result.getColumns().size());
            assertEquals(3, result.getRows().size());
            assertEquals(2, result.getRows().getFirst().size());
        }

        @Test
        public void testSelect(){
            List<String> columns = List.of("id", "name");
            Tbl tbl = Tbl.builder().addColumns(columns)
                    .addRow(Arrays.asList(1, "aaa"))
                    .addRow(Arrays.asList(2, "bbb"))
                    .addRow(Arrays.asList(3, "ccc"))
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
            Tbl tbl = Tbl.of(columns, List.of(data1, data2, data3));

            tbl.where("id", "==", 2);
            System.out.println(tbl.getRows());
            System.out.println(tbl.getRow(0));
            assertEquals(1, tbl.getRows().size());
            assertEquals(2, tbl.getRow(0).getFirst());
        }

        @Test
        public void testWhereEquals2() {
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            Tbl tbl = Tbl.of(List.of("id", "name"), List.of(data1, data2, data3));

            tbl.where("name", "==", "bbb");
            System.out.println(tbl.getRows());
            System.out.println(tbl.getRow(0));
            assertEquals(1, tbl.getRows().size());
            assertEquals(2, tbl.getRow(0).getFirst());
        }

        @Test
        public void testWhereNotEquals() {
            Tbl tbl = Tbl.builder()
                    .addColumn("id")
                    .addColumn("name")
                    .addRow(Arrays.asList(1, "aaa"))
                    .addRow(Arrays.asList(2, "bbb"))
                    .addRow(Arrays.asList(3, "ccc"))
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
            Tbl tbl = Tbl.of(columns, List.of(row1, row2, row3));

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
            Tbl tbl = Tbl.of(columns, List.of(row1, row2, row3));

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
            Tbl left =Tbl.of(List.of("id", "name"), List.of(
                    List.of(1, "Alice"),
                    List.of(2, "Bob"),
                    List.of(3, "Charlie")
            ));

            Tbl right = Tbl.of(List.of("id", "age"), List.of(
                    List.of(1, 20),
                    List.of(3, 30),
                    List.of(4, 40)
            ));

            TblJoin tblJoin = InnerTblJoin.of(left, right)
                    .on("id")
                    .selectAll();

            Tbl result = tblJoin.toTbl();

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
            Tbl left = Tbl.of(List.of("id", "name"), List.of(
                    List.of(1, "Alice"),
                    List.of(2, "Bob"),
                    List.of(3, "Charlie")
            ));

            Tbl right = Tbl.of(List.of("id", "age"), List.of(
                    List.of(1, 20),
                    List.of(3, 30),
                    List.of(4, 40)
            ));

            TblJoin tblJoin = LeftTblJoin.of(left, right)
                    .on("id")
                    .selectAll();

            Tbl result = tblJoin.toTbl();

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
            Tbl tbl = Tbl.of(columns, List.of(data1, data2, data3));

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
            Tbl tbl = Tbl.of(columns, List.of(data1, data2, data3));

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