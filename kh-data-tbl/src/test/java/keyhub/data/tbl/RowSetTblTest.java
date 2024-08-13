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

package keyhub.data.tbl;

import keyhub.data.tbl.filter.TblFilterType;
import keyhub.data.tbl.row.TblCell;
import keyhub.data.tbl.schema.TblColumn;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RowSetTblTest {

    @Nested
    class SelectTest {
        @Test
        void testSelectMethod() {
            List<TblColumn> schemas = List.of(
                    TblColumn.of("column1", String.class),
                    TblColumn.of("column2", String.class),
                    TblColumn.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.from(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            // Select specific columns
            Tbl resultTbl = tblInstance.select("column1", "column2");

            // Assertions
            assertEquals(2, resultTbl.getSchema().getColumnSize());
            assertEquals(3, resultTbl.count());

            List<String> expectedColumns = Arrays.asList("column1", "column2");
            assertEquals(expectedColumns, resultTbl.getColumns());

            TblCell<String> expectedFirstRowFirstColumn = TblCell.of(TblColumn.of("column1", String.class), "A");
            assertEquals(expectedFirstRowFirstColumn, resultTbl.getRow(0).findCell("column1").orElseThrow());

            TblCell<String> expectedFirstRowSecondColumn = TblCell.of(TblColumn.of("column2", String.class), "B");
            assertEquals(expectedFirstRowSecondColumn, resultTbl.getRow(0).findCell("column2").orElseThrow());
        }
    }

    @Nested
    class SelectorTest {
        @Test
        void testSelectorMethod() {
            List<TblColumn> schemas = List.of(
                    TblColumn.of("column1", String.class),
                    TblColumn.of("column2", String.class),
                    TblColumn.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            TblSchema schema = TblSchema.from(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            // Use selector to select specific columns
            Tbl resultTbl = tblInstance
                    .select("column1", "column2")
                    .where("column1", TblFilterType.GREATER_THAN_OR_EQUAL, "A");

            // Assertions
            assertEquals(2, resultTbl.getSchema().getColumnSize());
            assertEquals(3, resultTbl.count());

            List<String> expectedColumns = Arrays.asList("column1", "column2");
            assertEquals(expectedColumns, resultTbl.getColumns());

            TblCell<String> expectedFirstRowFirstColumn = TblCell.of(TblColumn.of("column1", String.class), "A");
            assertEquals(expectedFirstRowFirstColumn, resultTbl.getRow(0).findCell("column1").orElseThrow());

            TblCell<String> expectedFirstRowSecondColumn = TblCell.of(TblColumn.of("column2", String.class), "B");
            assertEquals(expectedFirstRowSecondColumn, resultTbl.getRow(0).findCell("column2").orElseThrow());
        }
    }
}
