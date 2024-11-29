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

package keyhub.data.structure.stream;

import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.table.KhTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KhStreamTest {
    KhTable mockTbl1;
    KhTable mockTbl2;

    @BeforeEach
    public void before(){
        KhSchema mockSchema = KhSchema.builder()
                .addColumn("column1", String.class)
                .addColumn("column2", Integer.class)
                .addColumn("column3", LocalDateTime.class)
                .build();
        mockTbl1 = KhTable.builder(mockSchema)
                .addRawRow(List.of("value1", 1, LocalDateTime.of(2021, 1, 1, 0, 0)))
                .addRawRow(List.of("value2", 2, LocalDateTime.of(2021, 1, 2, 0, 0)))
                .addRawRow(Arrays.asList("value3", 3, LocalDateTime.of(2021, 1, 3, 0, 0)))
                .build();
        KhSchema mockSchema2 = KhSchema.builder()
                .addColumn("column2", Integer.class)
                .addColumn("column4", String.class)
                .build();
        mockTbl2 = KhTable.builder(mockSchema2)
                .addRawRow(List.of(1, "A"))
                .addRawRow(List.of(2, "B"))
                .addRawRow(List.of(2, "C"))
                .build();
    }

    @Nested
    class InnerJoinTest {
        @Test
        void innerJoin_left() {
            // The stream met the table for inner join
            try(KhStream stream = mockTbl1.toStream()){
                KhTable joined = stream.innerJoin(mockTbl2)
                        .selectAll()
                        .on("column2")
                        .toOne()
                        .toTable();
                assertNotNull(joined.getSchema());
                System.out.println(joined);
                assertEquals(5, joined.getSchema().getColumnSize());
                assertEquals(3, joined.count());
            }
        }

        @Test
        void innerJoin_right() {
            // The table met each element of stream for inner join
            try(KhStream stream = mockTbl1.toStream()) {
                KhTable joined = mockTbl2.innerJoin(stream)
                        .selectAll()
                        .on("column2")
                        .toOne()
                        .toTable();

                assertNotNull(joined.getSchema());
                System.out.println(joined);
                assertEquals(5, joined.getSchema().getColumnSize());
                assertEquals(3, joined.count());
            }
        }
    }

    @Nested
    class LeftJoinTest {
        @Test
        void leftJoin_left() {
            // The stream met the table for left join
            try(KhStream stream = mockTbl1.toStream()) {
                KhTable joined = stream.leftJoin(mockTbl2)
                        .selectAll()
                        .on("column2")
                        .toOne()
                        .toTable();
                assertNotNull(joined.getSchema());
                System.out.println(joined);
                assertEquals(5, joined.getSchema().getColumnSize());
                assertEquals(4, joined.count());
            }
        }

        @Test
        void leftJoin_right() {
            // The table met each element of stream for left join
            try(KhStream stream = mockTbl1.toStream()) {
                KhTable joined = mockTbl2.leftJoin(stream)
                        .selectAll()
                        .on("column2")
                        .toOne()
                        .toTable();
                assertNotNull(joined.getSchema());
                System.out.println(joined);
                assertEquals(5, joined.getSchema().getColumnSize());
                assertEquals(9, joined.count());
            }
        }
    }
}