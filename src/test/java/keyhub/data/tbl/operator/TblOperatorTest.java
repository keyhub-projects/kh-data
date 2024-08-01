package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TblOperatorTest {

    Tbl mockTbl;

    @BeforeEach
    public void before(){
        TblSchema mockSchema = TblSchema.builder()
                .addColumn("column1", String.class)
                .addColumn("column2", Integer.class)
                .addColumn("column3", LocalDateTime.class)
                .build();
        mockTbl = Tbl.builder(mockSchema)
                .addRawRow(List.of("value1", 1, LocalDateTime.of(2021, 1, 1, 0, 0)))
                .addRawRow(List.of("value2", 2, LocalDateTime.of(2021, 1, 2, 0, 0)))
                .addRawRow(Arrays.asList(null, 3, LocalDateTime.of(2021, 1, 3, 0, 0)))
                .build();
    }

    @Nested
    class IsNullTest{
        @Test
        public void testBuildOperatorIsNull() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.IS_NULL)
                    .tbl(mockTbl)
                    .column("column1")
                    .build();
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class IsNotNullTest{
        @Test
        public void testBuildOperatorIsNotNull() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.IS_NOT_NULL)
                    .tbl(mockTbl)
                    .column("column1")
                    .build();
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class EqualTest{
        @Test
        public void testBuildOperatorEqual() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.EQUAL)
                    .tbl(mockTbl)
                    .column("column1")
                    .value("value1")
                    .build();
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class NotEqualTest{
        @Test
        public void testBuildOperatorNotEqual() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.NOT_EQUAL)
                    .tbl(mockTbl)
                    .column("column1")
                    .value("value1")
                    .build();
            // null 포함
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class GreaterThanTest{
        @Test
        public void testBuildOperatorGreaterThan() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.GREATER_THAN)
                    .tbl(mockTbl)
                    .column("column2")
                    .value(1)
                    .build();
            assertEquals(2, result.getRows().size());
        }

        @Test
        public void testBuildOperatorGreaterThanWithDateTime() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.GREATER_THAN)
                    .tbl(mockTbl)
                    .column("column3")
                    .value(LocalDateTime.of(2021, 1, 2, 0, 0))
                    .build();
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class GreaterThanOrEqualTest{
        @Test
        public void testBuildOperatorGreaterThanOrEqual() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.GREATER_THAN_OR_EQUAL)
                    .tbl(mockTbl)
                    .column("column2")
                    .value(1)
                    .build();
            assertEquals(3, result.getRows().size());
        }
    }

    @Nested
    class LessThanTest{
        @Test
        public void testBuildOperatorLessThan() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.LESS_THAN)
                    .tbl(mockTbl)
                    .column("column2")
                    .value(3)
                    .build();
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LessThanOrEqualTest{
        @Test
        public void testBuildOperatorLessThanOrEqual() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.LESS_THAN_OR_EQUAL)
                    .tbl(mockTbl)
                    .column("column2")
                    .value(3)
                    .build();
            assertEquals(3, result.getRows().size());
        }
    }

    @Nested
    class LikeTest{
        @Test
        public void testBuildOperatorLike() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.LIKE)
                    .tbl(mockTbl)
                    .column("column1")
                    .value("value")
                    .build();
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class InTest{
        @Test
        public void testBuildOperatorIn() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.IN)
                    .tbl(mockTbl)
                    .column("column1")
                    .value(List.of("value1", "value2"))
                    .build();
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class NotInTest{
        @Test
        public void testBuildOperatorNotIn() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = builder.operator(TblOperatorType.NOT_IN)
                    .tbl(mockTbl)
                    .column("column1")
                    .value(List.of("value1"))
                    .build();
            assertEquals(2, result.getRows().size());
        }
    }

}