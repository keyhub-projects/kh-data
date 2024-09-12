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
import keyhub.data.column.Column;
import keyhub.data.schema.Schema;
import org.junit.jupiter.api.Test;

import java.util.List;

import static keyhub.data.function.RowPredicate.is;
import static keyhub.data.function.CellSelector.column;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TblStreamTest {

    @Test
    public void testTblStream(){
        Schema schema = Schema.from(List.of(
                Column.of("name", String.class),
                Column.of("age", Integer.class),
                Column.of("height", Double.class),
                Column.of("weight", Float.class)
        ));
        Tbl tbl = Tbl.of(schema, List.of(
                List.of("Alice", 20, 160.0, 50.0f),
                List.of("Bob", 30, 170.0, 60.0f),
                List.of("Charlie", 40, 180.0, 70.0f)
        ));

        Tbl result = tbl.stream()
                .select("name", "age", "height")
                .select(
                        Column.of("name", String.class),
                        Column.of("height", String.class),
                        Column.of("age", Integer.class)
                )
                .select(
                        column("name"),
                        column("age")
                )
                .where(is("age", age -> (Integer) age.getValue() > 20))
                .toTbl();
        System.out.println(result);
        assertEquals(2, result.count());
    }
}
