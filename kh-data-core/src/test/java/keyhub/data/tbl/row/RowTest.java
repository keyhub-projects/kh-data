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

package keyhub.data.tbl.row;

import keyhub.data.row.Row;
import keyhub.data.schema.Schema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class RowTest {

    @Nested
    class ConstructorTest{

        @Test
        public void testOfWithSchemaAndVarargsValues() {
            Schema schema = Schema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();

            Row row = Row.of(schema, 1, "2", 3);

            Assertions.assertEquals(schema, row.getSchema());
        }

        @Test
        public void testOfWithSchemaAndListValues() {
            Schema schema = Schema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();
            List<Object> values = Arrays.asList(1, "2", 3);

            Row row = Row.of(schema, values);

            Assertions.assertEquals(schema, row.getSchema());
            values.forEach(value -> Assertions.assertTrue(row.toList().contains(value)));
        }

        @Test
        void testOfWithSchemaAndListValues_invalidValues() {
            Schema schema = Schema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();
            List<Object> values = Arrays.asList(1, "2", "3");

            Assertions.assertThrows(IllegalArgumentException.class, () -> Row.of(schema, values));
        }
    }
}