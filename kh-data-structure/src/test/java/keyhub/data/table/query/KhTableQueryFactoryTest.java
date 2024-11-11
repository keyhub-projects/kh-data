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

package keyhub.data.table.query;

import keyhub.data.column.KhColumn;
import keyhub.data.table.KhTable;
import keyhub.data.schema.KhSchema;
import org.junit.jupiter.api.Test;

import java.util.List;

import static keyhub.data.function.KhRowPredicate.is;
import static keyhub.data.function.KhCellSelector.column;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KhTableQueryFactoryTest {

    @Test
    public void testTblStream(){
        KhSchema schema = KhSchema.from(List.of(
                KhColumn.of("name", String.class),
                KhColumn.of("age", Integer.class),
                KhColumn.of("height", Double.class),
                KhColumn.of("weight", Float.class)
        ));
        KhTable tbl = KhTable.of(schema, List.of(
                List.of("Alice", 20, 160.0, 50.0f),
                List.of("Bob", 30, 170.0, 60.0f),
                List.of("Charlie", 40, 180.0, 70.0f)
        ));

        KhTable result = tbl.query()
                .select("name", "age", "height")
                .select(
                        KhColumn.of("name", String.class),
                        KhColumn.of("height", String.class),
                        KhColumn.of("age", Integer.class)
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
