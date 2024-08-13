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

package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;
import keyhub.data.tbl.filter.TblFilterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TblFilterTest {

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
            Tbl result = mockTbl.where("column1", TblFilterType.IS_NULL);
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class IsNotNullTest{
        @Test
        public void testBuildOperatorIsNotNull() {
            Tbl result = mockTbl.where("column1", TblFilterType.IS_NOT_NULL);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class EqualTest{
        @Test
        public void testBuildOperatorEqual() {
            Tbl result = mockTbl.where("column1", TblFilterType.EQUAL, "value1");
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class NotEqualTest{
        @Test
        public void testBuildOperatorNotEqual() {
            Tbl result = mockTbl.where("column1", TblFilterType.NOT_EQUAL, "value1");
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class GreaterThanTest{
        @Test
        public void testBuildOperatorGreaterThan() {
            Tbl result = mockTbl.where("column2", TblFilterType.GREATER_THAN, 1);
            assertEquals(2, result.getRows().size());
        }

        @Test
        public void testBuildOperatorGreaterThanWithDateTime() {
            Tbl result = mockTbl.where("column3", TblFilterType.GREATER_THAN, LocalDateTime.of(2021, 1, 2, 0, 0));
            assertEquals(1, result.getRows().size());
        }
    }

    @Nested
    class GreaterThanOrEqualTest{
        @Test
        public void testBuildOperatorGreaterThanOrEqual() {
            Tbl result = mockTbl.where("column2", TblFilterType.GREATER_THAN_OR_EQUAL, 2);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LessThanTest{
        @Test
        public void testBuildOperatorLessThan() {
            Tbl result = mockTbl.where("column2", TblFilterType.LESS_THAN, 3);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LessThanOrEqualTest{
        @Test
        public void testBuildOperatorLessThanOrEqual() {
            Tbl result = mockTbl.where("column2", TblFilterType.LESS_THAN_OR_EQUAL, 2);
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class LikeTest{
        @Test
        public void testBuildOperatorLike() {
            Tbl result = mockTbl.where("column1", TblFilterType.LIKE, "value");
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class InTest{
        @Test
        public void testBuildOperatorIn() {
            Tbl result = mockTbl.where("column1", TblFilterType.IN, List.of("value1", "value2"));
            assertEquals(2, result.getRows().size());
        }
    }

    @Nested
    class NotInTest{
        @Test
        public void testBuildOperatorNotIn() {
            Tbl result = mockTbl.where("column1", TblFilterType.NOT_IN, List.of("value1"));
            assertEquals(2, result.getRows().size());
        }
    }

}