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

package keyhub.data.tbl.implement.rowset;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RowSetTblImplementTest {

    @Nested
    class SelectTest {
        @Test
        @DisplayName("컬럼을 Select시, 해당 컬럼들로 이루어진 Tbl 객체를 반환한다.")
        void testSelectMethod() {
            List<TblColumnSchema> schemas = List.of(
                    TblColumnSchema.of("column1", String.class),
                    TblColumnSchema.of("column2", String.class),
                    TblColumnSchema.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.of(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            // Select specific columns
            Tbl resultTbl = tblInstance.select("column1", "column2");

            // Assertions
            assertEquals(2, resultTbl.getSchema().getColumnSize());
            assertEquals(3, resultTbl.count());

            List<String> expectedColumns = Arrays.asList("column1", "column2");
            assertEquals(expectedColumns, resultTbl.getColumns());

            String expectedFirstRowFirstColumn = "A";
            assertEquals(expectedFirstRowFirstColumn, resultTbl.getRow(0).findCell("column1").orElseThrow());

            String expectedFirstRowSecondColumn = "B";
            assertEquals(expectedFirstRowSecondColumn, resultTbl.getRow(0).findCell("column2").orElseThrow());
        }
    }
}