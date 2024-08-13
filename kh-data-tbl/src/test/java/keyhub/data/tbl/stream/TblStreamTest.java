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

package keyhub.data.tbl.stream;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumn;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TblStreamTest {

    @Test
    public void testTblStream(){
        TblSchema schema = TblSchema.from(List.of(
                TblColumn.of("name", String.class),
                TblColumn.of("age", Integer.class),
                TblColumn.of("height", Double.class),
                TblColumn.of("weight", Float.class)
        ));
        Tbl tbl = Tbl.of(schema, List.of(
                List.of("Alice", 20, 160.0, 50.0f),
                List.of("Bob", 30, 170.0, 60.0f),
                List.of("Charlie", 40, 180.0, 70.0f)
        ));

        Tbl result = tbl.stream()
                .select("name", "age", "height")
                .select(
                        TblColumn.of("name", String.class),
                        TblColumn.of("height", String.class),
                        TblColumn.of("age", Integer.class)
                )
                .select(
                        tblRow -> TblRow.of(
                                tblRow.findCell("name").orElseThrow(),
                                tblRow.findCell("age").orElseThrow()
                        )
                )
                .toTbl();
        System.out.println(result);
        assertEquals(3, result.count());
    }
}
