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

package keyhub.data.table.join.inner;

import keyhub.data.table.KhTable;
import keyhub.data.schema.KhSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static keyhub.data.function.KhColumnSelector.column;

class TblInnerJoinTest {

    KhTable mockTbl1;
    KhTable mockTbl2;

    @BeforeEach
    public void before(){
        KhSchema mockSchema = KhSchema.builder()
                .addColumn("column1", String.class)
                .addColumn("column3", Integer.class)
                .addColumn("column2", LocalDateTime.class)
                .build();
        mockTbl1 = KhTable.builder(mockSchema)
                .addRawRow(List.of("value1", 1, LocalDateTime.of(2021, 1, 1, 0, 0)))
                .addRawRow(List.of("value2", 2, LocalDateTime.of(2021, 1, 2, 0, 0)))
                .addRawRow(Arrays.asList("value3", 3, LocalDateTime.of(2021, 1, 3, 0, 0)))
                .build();
        KhSchema mockSchema2 = KhSchema.builder()
                .addColumn("column3", Integer.class)
                .addColumn("column4", String.class)
                .build();
        mockTbl2 = KhTable.builder(mockSchema2)
                .addRawRow(List.of(1, "A"))
                .addRawRow(List.of(2, "B"))
                .build();
    }

    @Test
    void testInnerJoinSuccess() {
        KhTable left = mockTbl1;
        KhTable right = mockTbl2;

        KhTable result = left.innerJoin(right)
                .on("column3")
                .selectAll()
                .toTbl();

        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("column1", Arrays.asList("value1", "value2"));
        expected.put("column2", Arrays.asList(
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 2, 0, 0)
        ));
        expected.put("column3", Arrays.asList(1, 2));
        expected.put("column4", Arrays.asList("A", "B"));
        Assertions.assertEquals(expected, result.toColumnListMap());
    }

    @Test
    void testInnerJoinByTblCellSelector(){
        KhTable left = mockTbl1;
        KhTable right = mockTbl2;

        KhTable result = left.innerJoin(right)
                .on("column3")
                .selectFromLeft(
                        column("column1"),
                        column("column2")
                )
                .selectFromRight(
                        column("column3"),
                        column("column4")
                )
                .toTbl();

        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("column1", Arrays.asList("value1", "value2"));
        expected.put("column2", Arrays.asList(
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 2, 0, 0)
        ));
        expected.put("column3", Arrays.asList(1, 2));
        expected.put("column4", Arrays.asList("A", "B"));
        Assertions.assertEquals(expected, result.toColumnListMap());
    }
}