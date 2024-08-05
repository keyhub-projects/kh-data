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

package keyhub.data.tbl.join.left;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TblLeftJoinTest {

    Tbl mockTbl1;
    Tbl mockTbl2;

    @BeforeEach
    public void before(){
        TblSchema mockSchema = TblSchema.builder()
                .addColumn("column1", String.class)
                .addColumn("column3", Integer.class)
                .addColumn("column2", LocalDateTime.class)
                .build();
        mockTbl1 = Tbl.builder(mockSchema)
                .addRawRow(List.of("value1", 1, LocalDateTime.of(2021, 1, 1, 0, 0)))
                .addRawRow(List.of("value2", 2, LocalDateTime.of(2021, 1, 2, 0, 0)))
                .addRawRow(Arrays.asList("value3", 3, LocalDateTime.of(2021, 1, 3, 0, 0)))
                .build();
        TblSchema mockSchema2 = TblSchema.builder()
                .addColumn("column3", Integer.class)
                .addColumn("column4", String.class)
                .build();
        mockTbl2 = Tbl.builder(mockSchema2)
                .addRawRow(List.of(1, "A"))
                .addRawRow(List.of(2, "B"))
                .build();
    }

    @Test
    void testLeftJoinSuccess() {
        Tbl left = mockTbl1;
        Tbl right = mockTbl2;

        Tbl result = left.leftJoin(right)
                .on("column3")
                .selectAll()
                .toTbl();

        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("column1", Arrays.asList("value1", "value2", "value3"));
        expected.put("column2", Arrays.asList(
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 2, 0, 0),
                LocalDateTime.of(2021, 1, 3, 0, 0))
        );
        expected.put("column3", Arrays.asList(1, 2, null));
        Assertions.assertEquals(expected, result.toColumnListMap());
    }

}