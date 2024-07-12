package keyhub.data.set;

import keyhub.data.DataSet;
import keyhub.data.InnerJoinSet;
import keyhub.data.JoinSet;
import keyhub.data.LeftJoinSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataSetImplementTest {

    @Nested
    class SelectTest{
        @Test
        public void testSelectAll() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(data1);
            dataSet.addRow(data2);
            dataSet.addRow(data3);

            DataSet result = dataSet.selectAll();
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
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(data1);
            dataSet.addRow(data2);
            dataSet.addRow(data3);

            DataSet result = dataSet.select("id");
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
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(data1);
            dataSet.addRow(data2);
            dataSet.addRow(data3);

            dataSet.where("id", "==", 2);
            System.out.println(dataSet.getRows());
            System.out.println(dataSet.getRow(0));
            assertEquals(1, dataSet.getRows().size());
            assertEquals(2, dataSet.getRow(0).getFirst());
        }

        @Test
        public void testWhereEquals2() {
            List<String> columns = List.of("id", "name");
            List<Object> data1 = Arrays.asList(1, "aaa");
            List<Object> data2 = Arrays.asList(2, "bbb");
            List<Object> data3 = Arrays.asList(3, "ccc");
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(data1);
            dataSet.addRow(data2);
            dataSet.addRow(data3);

            dataSet.where("name", "==", "bbb");
            System.out.println(dataSet.getRows());
            System.out.println(dataSet.getRow(0));
            assertEquals(1, dataSet.getRows().size());
            assertEquals(2, dataSet.getRow(0).getFirst());
        }

        @Test
        public void testWhereNotEquals() {
            List<String> columns = List.of("id", "name");
            List<Object> row1 = Arrays.asList(1, "aaa");
            List<Object> row2 = Arrays.asList(2, "bbb");
            List<Object> row3 = Arrays.asList(3, "ccc");
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(row1);
            dataSet.addRow(row2);
            dataSet.addRow(row3);

            dataSet.where("name", "!=", "bbb");
            assertEquals(2, dataSet.getRows().size());
            assertTrue(dataSet.getRows().stream().noneMatch(row -> row.get(1).equals("bbb")));
        }

        @Test
        public void testWhereGreater() {
            List<String> columns = List.of("id", "name");
            List<Object> row1 = Arrays.asList(1, "aaa");
            List<Object> row2 = Arrays.asList(2, "bbb");
            List<Object> row3 = Arrays.asList(3, "ccc");
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(row1);
            dataSet.addRow(row2);
            dataSet.addRow(row3);

            dataSet.where("id", ">", 1);
            assertEquals(2, dataSet.getRows().size());
            assertTrue(dataSet.getRows().stream().allMatch(row -> (int) row.getFirst() > 1));
        }

        @Test
        public void testWhereLess() {
            List<String> columns = List.of("id", "name");
            List<Object> row1 = Arrays.asList(1, "aaa");
            List<Object> row2 = Arrays.asList(2, "bbb");
            List<Object> row3 = Arrays.asList(3, "ccc");
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(row1);
            dataSet.addRow(row2);
            dataSet.addRow(row3);

            dataSet.where("id", "<", 3);

            assertEquals(2, dataSet.getRows().size());
            assertTrue(dataSet.getRows().stream().allMatch(row -> (int) row.getFirst() < 3));

        }
    }

    @Nested
    class InnerJoinTest {
        @Test
        @DisplayName("Test Inner Join Success")
        public void testInnerJoin(){
            DataSet left = DataSet.of(List.of("id", "name"))
                    .addRow(List.of(1, "Alice"))
                    .addRow(List.of(2, "Bob"))
                    .addRow(List.of(3, "Charlie"));

            DataSet right = DataSet.of(List.of("id", "age"))
                    .addRow(List.of(1, 20))
                    .addRow(List.of(3, 30))
                    .addRow(List.of(4, 40));

            JoinSet joinSet = InnerJoinSet.of(left, right)
                    .on("id")
                    .selectAll();

            DataSet result = joinSet.toDataSet();

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
            DataSet left = DataSet.of(List.of("id", "name"))
                    .addRow(List.of(1, "Alice"))
                    .addRow(List.of(2, "Bob"))
                    .addRow(List.of(3, "Charlie"));

            DataSet right = DataSet.of(List.of("id", "age"))
                    .addRow(List.of(1, 20))
                    .addRow(List.of(3, 30))
                    .addRow(List.of(4, 40));

            JoinSet joinSet = LeftJoinSet.of(left, right)
                    .on("id")
                    .selectAll();

            DataSet result = joinSet.toDataSet();

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
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(data1);
            dataSet.addRow(data2);
            dataSet.addRow(data3);

            List<Map<String, Object>> result = dataSet.toRowMapList();
            System.out.println(dataSet.getColumns());
            System.out.println(dataSet.getRows());
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
            DataSet dataSet = new DataSetImplement(columns);
            dataSet.addRow(data1);
            dataSet.addRow(data2);
            dataSet.addRow(data3);

            Map<String, List<Object>> result = dataSet.toColumnMapList();
            System.out.println(dataSet.getColumns());
            System.out.println(dataSet.getRows());
            System.out.println(result);

            assertEquals(2, result.size());
            assertEquals(List.of(1, 2, 3), result.get("id"));
            assertEquals(List.of("aaa", "bbb", "ccc"), result.get("name"));
        }
    }
}
