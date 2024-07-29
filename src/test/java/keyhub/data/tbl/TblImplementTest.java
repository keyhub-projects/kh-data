package keyhub.data.tbl;

import keyhub.data.tbl.join.InnerTblJoin;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.join.LeftTblJoin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TblImplementTest {

    @Nested
    class InnerJoinTest {
        @Test
        @DisplayName("Test Inner Join Success")
        public void testInnerJoin(){
            Tbl left = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "Alice"),
                            List.of(2, "Bob"),
                            List.of(3, "Charlie")
                    ))
                    .build();

            Tbl right = Tbl.builder()
                    .addColumns(List.of("id", "age"))
                    .addRow(List.of(1, 20))
                    .addRow(List.of(3, 30))
                    .addRow(List.of(4, 40))
                    .build();

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

        @Test
        @DisplayName("Test Inner Join Success")
        public void testInnerJoin2(){
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

            TblJoin tblJoin = InnerTblJoin.of(left, right)
                    .on("id")
                    .selectAllFromLeft()
                    .selectFromRight("age");

            Tbl result = tblJoin.toTbl();

            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            // 2
            // [id, name, age]
            assertEquals(3, result.getColumns().size());
            // 2
            // [[1, Alice, 20], [3, Charlie, 30]]
            assertEquals(2, result.getRows().size());
            // 2
            assertEquals(3, result.getRow(0).size());
        }

        @Test
        @DisplayName("Test Inner Join Success")
        public void testInnerJoin3(){
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

            TblJoin tblJoin = InnerTblJoin.of(left, right)
                    .on("id")
                    .selectFromLeft("name", "id")
                    .selectFromRight("age", "id");

            Tbl result = tblJoin.toTbl();

            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            // 2
            // [name, id, age, id]
            assertEquals(4, result.getColumns().size());
            // 2
            // [[1, Alice, 20, 1], [3, Charlie, 30, 3]]
            assertEquals(2, result.getRows().size());
            // 2
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
                    .addRows(List.of(
                            List.of(1, "Alice"),
                            List.of(2, "Bob"),
                            List.of(3, "Charlie")))
                    .build();

            Tbl right = Tbl.builder()
                    .addColumns(List.of("id", "age"))
                    .addRow(List.of(1, 20))
                    .addRow(List.of(3, 30))
                    .addRow(List.of(4, 40))
                    .build();

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

        @Test
        @DisplayName("Test Left Join Success")
        public void testLeftJoin2(){
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

            TblJoin tblJoin = LeftTblJoin.of(left, right)
                    .on("id")
                    .selectAllFromLeft()
                    .selectFromRight("age");

            Tbl result = tblJoin.toTbl();

            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            // 2
            // [id, name, age]
            assertEquals(3, result.getColumns().size());
            // 3
            // [[1, Alice, 20], [2, Bob, null], [3, Charlie, 30]]
            assertEquals(left.size(), result.getRows().size());
            // 3
            assertEquals(3, result.getRow(0).size());
        }

        @Test
        @DisplayName("Test Left Join Success")
        public void testLeftJoin3(){
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

            TblJoin tblJoin = LeftTblJoin.of(left, right)
                    .on("id")
                    .selectFromLeft("name", "id")
                    .selectFromRight("age", "id");

            Tbl result = tblJoin.toTbl();

            System.out.println(result.getColumns());
            System.out.println(result.getRows());
            // 2
            // [name, id, age, id]
            assertEquals(4, result.getColumns().size());
            // 3
            // [[1, Alice, 20, 1], [2, Bob, null, null], [3, Charlie, 30, 3]]
            assertEquals(left.size(), result.getRows().size());
            // 4
            assertEquals(4, result.getRow(0).size());
        }
    }

    @Nested
    public class ValidateRowsSizeTest{
        @Test
        public void testValidateRowsSize_True(){
            Tbl tbl = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRow(List.of(1, "aaa"))
                    .addRow(List.of(2, "bbb"))
                    .addRow(List.of(3, "ccc"))
                    .build();

            boolean result = tbl.validateRowsSize(tbl.getRows());
            assertTrue(result);
        }

        @Test
        public void testValidateRowsSize_False(){
            assertThrows(IllegalArgumentException.class, () -> {
                Tbl.builder()
                        .addColumns(List.of("id", "name"))
                        .addRow(List.of(1, "aaa"))
                        .addRow(List.of(3, "ccc", "extra data"))
                        .build();
            });
        }
    }

    @Nested
    class AdjustRowTest {
        @Test
        @DisplayName("Test adjustRow when row size is less than columns size")
        public void testAdjustRow_SizeLessThanColumns() {
            Tbl tbl = Tbl.builder().addColumns(List.of("id", "name", "age")).build();
            List<Object> adjustedRow = tbl.adjustRow(List.of(1, "aaa"));

            assertEquals(Arrays.asList(1, "aaa", null), adjustedRow);
        }

        @Test
        @DisplayName("Test adjustRow when row size equals to columns size")
        public void testAdjustRow_SizeEqualsToColumns() {
            Tbl tbl = Tbl.builder().addColumns(List.of("id", "name")).build();
            List<Object> adjustedRow = tbl.adjustRow(List.of(1, "aaa"));

            assertEquals(Arrays.asList(1, "aaa"), adjustedRow);
        }
    }
    @Nested
    public class EqualsTest {

        @Test
        public void testEquals_SameObject(){
            Tbl tbl = Tbl.builder().addColumns(List.of("id", "name"))
                    .addRow(List.of(1, "aaa"))
                    .addRow(List.of(2, "bbb"))
                    .addRow(List.of(3, "ccc"))
                    .build();

            assertTrue(tbl.equals(tbl));
        }

        @Test
        public void testEquals_DifferentObjectSameData(){
            Tbl tbl1 = Tbl.builder().addColumns(List.of("id", "name"))
                    .addRow(List.of(1, "aaa"))
                    .addRow(List.of(2, "bbb"))
                    .addRow(List.of(3, "ccc"))
                    .build();

            Tbl tbl2 = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "bbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            assertEquals(tbl1, tbl2);
        }

        @Test
        public void testEquals_SameData(){
            Tbl tbl1 = Tbl.builder().addColumns(List.of("id", "name"))
                    .addRow(List.of(1, "aaa"))
                    .addRow(List.of(2, "bbb"))
                    .addRow(List.of(3, "ccc"))
                    .build();

            Tbl tbl2 = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "bbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            assertEquals(tbl1, tbl2);
        }

        @Test
        public void testEquals_NotSameData(){
            Tbl tbl1 = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "bbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            Tbl tbl2 = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "cbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            assertNotEquals(tbl1, tbl2);
        }

        @Test
        public void testEquals_NotSameData2(){
            Tbl tbl1 = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "bbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            Tbl tbl2 = Tbl.builder()
                    .addColumns(List.of("id", "alphabet"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "bbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            assertNotEquals(tbl1, tbl2);
        }

        @Test
        public void testEquals_NotTblType(){
            Tbl tbl = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                            List.of(1, "aaa"),
                            List.of(2, "bbb"),
                            List.of(3, "ccc")
                    ))
                    .build();

            assertNotEquals(tbl, new Object());
        }
    }
}
