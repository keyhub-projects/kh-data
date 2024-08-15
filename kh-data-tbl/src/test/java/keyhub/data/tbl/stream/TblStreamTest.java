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
import keyhub.data.tbl.schema.TblColumn;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static keyhub.data.tbl.function.TblJoinSchemaPredicate.on;
import static keyhub.data.tbl.function.TblRowPredicate.is;
import static keyhub.data.tbl.function.TblColumnSelector.column;
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
                        column("name"),
                        column("age")
                )
                .where(is("age", age -> (Integer) age.getValue() > 20))
                .toTbl();
        System.out.println(result);
        assertEquals(2, result.count());
    }

    @Nested
    public class JoinTest{
        @Test
        public void testInnerJoin(){
            TblSchema schema1 = TblSchema.from(List.of(
                    TblColumn.of("name", String.class),
                    TblColumn.of("age", Integer.class)
            ));
            Tbl tbl1 = Tbl.of(schema1, List.of(
                    List.of("Alice", 20),
                    List.of("Bob", 30),
                    List.of("Charlie", 40)
            ));

            TblSchema schema2 = TblSchema.from(List.of(
                    TblColumn.of("name", String.class),
                    TblColumn.of("height", Double.class)
            ));
            Tbl tbl2 = Tbl.of(schema2, List.of(
                    List.of("Alice", 160.0),
                    List.of("Bob", 170.0),
                    List.of("David", 180.0)
            ));

            TblJoinStream joinStream = tbl1.stream()
                    .innerJoin(tbl2.stream(), on("name", TblColumn::equals));

        }
    }
}
