package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            Tbl result = mockTbl.where("column1", TblOperatorType.IS_NULL);
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class IsNotNullTest{
        @Test
        public void testBuildOperatorIsNotNull() {
            Tbl result = mockTbl.where("column1", TblOperatorType.IS_NOT_NULL);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class EqualTest{
        @Test
        public void testBuildOperatorEqual() {
            Tbl result = mockTbl.where("column1", TblOperatorType.EQUAL, "value1");
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class NotEqualTest{
        @Test
        public void testBuildOperatorNotEqual() {
            Tbl result = mockTbl.where("column1", TblOperatorType.NOT_EQUAL, "value1");
            // null 포함
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class GreaterThanTest{
        @Test
        public void testBuildOperatorGreaterThan() {
            Tbl result = mockTbl.where("column2", TblOperatorType.GREATER_THAN, 1);
            assertEquals(2, result.getRows().size());
        }

        @Test
        public void testBuildOperatorGreaterThanWithDateTime() {
            Tbl result = mockTbl.where("column3", TblOperatorType.GREATER_THAN, LocalDateTime.of(2021, 1, 2, 0, 0));
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class GreaterThanOrEqualTest{
        @Test
        public void testBuildOperatorGreaterThanOrEqual() {
            Tbl result = mockTbl.where("column2", TblOperatorType.GREATER_THAN_OR_EQUAL, 2);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LessThanTest{
        @Test
        public void testBuildOperatorLessThan() {
            Tbl result = mockTbl.where("column2", TblOperatorType.LESS_THAN, 3);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LessThanOrEqualTest{
        @Test
        public void testBuildOperatorLessThanOrEqual() {
            Tbl result = mockTbl.where("column2", TblOperatorType.LESS_THAN_OR_EQUAL, 2);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LikeTest{
        @Test
        public void testBuildOperatorLike() {
            Tbl result = mockTbl.where("column1", TblOperatorType.LIKE, "value");
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class InTest{
        @Test
        public void testBuildOperatorIn() {
            SimpleTblOperatorBuilder builder = new SimpleTblOperatorBuilder();
            Tbl result = mockTbl.where("column1", TblOperatorType.IN, List.of("value1", "value2"));
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class NotInTest{
        @Test
        public void testBuildOperatorNotIn() {
            Tbl result = mockTbl.where("column1", TblOperatorType.NOT_IN, List.of("value1"));
            assertEquals(2, result.getRows().size());
        }
    }

}