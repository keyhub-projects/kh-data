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
            Tbl tbl = new TblVariable(List.of("id", "name"))
                    .addRow(List.of(1, "aaa"))
                    .addRow(List.of(2, "bbb"))
                    .addRow(List.of(3, "ccc"));

            assertEquals(tbl.getClass(), TblVariable.class);
            TblVariable tblVariable = (TblVariable) tbl;
            assertEquals(tbl.getColumns(), tblVariable.getColumns());
            assertEquals(tbl.getRows(), tblVariable.getRows());

            TblValue tblValue = tblVariable.toValue();
            assertEquals(tbl.getColumns(), tblValue.getColumns());
            assertEquals(tbl.getRows(), tblValue.getRows());
            assertEquals(tblVariable.getColumns(), tblValue.getColumns());
        }

        @Test
        public void testConvertToVariable(){
            Tbl tbl = Tbl.builder()
                    .addColumns(List.of("id", "name"))
                    .addRows(List.of(
                        List.of(1, "aaa"),
                        List.of(2, "bbb"),
                        List.of(3, "ccc")
                    ))
                    .build();

            assertEquals(tbl.getClass(), TblValue.class);
            TblValue tblValue = (TblValue) tbl;
            assertEquals(tbl.getColumns(), tblValue.getColumns());
            assertEquals(tbl.getRows(), tblValue.getRows());

            TblVariable tblVariable = tblValue.toVariable();
            assertEquals(tbl.getColumns(), tblVariable.getColumns());
            assertEquals(tbl.getRows(), tblVariable.getRows());
            assertEquals(tblValue.getColumns(), tblVariable.getColumns());
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
    }
}
