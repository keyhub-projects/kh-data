package keyhub.data.simpleimplement;

import keyhub.data.join.InnerJoinSet;
import keyhub.data.join.JoinSet;
import keyhub.data.join.LeftJoinSet;
import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.TblValue;
import keyhub.data.tbl.TblVariable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TblImplementTest {

    @Nested
    public class ConvertTest{
        @Test
        public void TestConvertToValue(){
            TblVariable tbl = new TblVariable(List.of("id", "name"))
                    .addRow(List.of(1, "aaa"))
                    .addRow(List.of(2, "bbb"))
                    .addRow(List.of(3, "ccc"));

            assertEquals(tbl.getClass(), TblVariable.class);
            assertEquals(tbl.getColumns(), tbl.getColumns());
            assertEquals(tbl.getRows(), tbl.getRows());

            TblValue tblValue = tbl.toValue();
            assertEquals(tbl.getColumns(), tblValue.getColumns());
            assertEquals(tbl.getRows(), tblValue.getRows());
            assertEquals(tbl.getColumns(), tblValue.getColumns());
        }

        @Test
        public void testConvertToVariable(){
            TblValue tbl = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                        List.of(1, "aaa"),
                        List.of(2, "bbb"),
                        List.of(3, "ccc")
                    ))
                    .build();

            assertEquals(tbl.getClass(), TblValue.class);
            assertEquals(tbl.getColumns(), tbl.getColumns());
            assertEquals(tbl.getRows(), tbl.getRows());

            TblVariable tblVariable = tbl.toVariable();
            assertEquals(tbl.getColumns(), tblVariable.getColumns());
            assertEquals(tbl.getRows(), tblVariable.getRows());
            assertEquals(tbl.getColumns(), tblVariable.getColumns());
        }
    }


    @Nested
    class InnerJoinTest {
        @Test
        @DisplayName("Test Inner Join Success")
        public void testInnerJoin(){
            Tbl left = new TblVariable(List.of("id", "name"), List.of(
                    List.of(1, "Alice"),
                    List.of(2, "Bob"),
                    List.of(3, "Charlie")
            ));

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

            JoinSet joinSet = InnerJoinSet.of(left, right)
                    .on("id")
                    .selectAllFromLeft()
                    .selectFromRight("age");

            Tbl result = joinSet.toTbl();

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

            JoinSet joinSet = InnerJoinSet.of(left, right)
                    .on("id")
                    .selectFromLeft("name", "id")
                    .selectFromRight("age", "id");

            Tbl result = joinSet.toTbl();

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
            Tbl left = new TblVariable(List.of("id", "name"), List.of(
                    List.of(1, "Alice"),
                    List.of(2, "Bob"),
                    List.of(3, "Charlie")
            ));

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

            JoinSet joinSet = LeftJoinSet.of(left, right)
                    .on("id")
                    .selectAllFromLeft()
                    .selectFromRight("age");

            Tbl result = joinSet.toTbl();

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

            JoinSet joinSet = LeftJoinSet.of(left, right)
                    .on("id")
                    .selectFromLeft("name", "id")
                    .selectFromRight("age", "id");

            Tbl result = joinSet.toTbl();

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
}
