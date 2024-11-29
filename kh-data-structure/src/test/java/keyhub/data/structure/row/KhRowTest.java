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

package keyhub.data.structure.row;

import keyhub.data.structure.schema.KhSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class KhRowTest {

    @Nested
    class ConstructorTest{

        @Test
        public void testOfWithSchemaAndVarargsValues() {
            KhSchema schema = KhSchema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();

            KhRow row = KhRow.of(schema, 1, "2", 3);

            Assertions.assertEquals(schema, row.getSchema());
        }

        @Test
        public void testOfWithSchemaAndListValues() {
            KhSchema schema = KhSchema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();
            List<Object> values = Arrays.asList(1, "2", 3);

            KhRow row = KhRow.of(schema, values);

            Assertions.assertEquals(schema, row.getSchema());
            values.forEach(value -> Assertions.assertTrue(row.toList().contains(value)));
        }

        @Test
        void testOfWithSchemaAndListValues_invalidValues() {
            KhSchema schema = KhSchema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();
            List<Object> values = Arrays.asList(1, "2", "3");

            Assertions.assertThrows(IllegalArgumentException.class, () -> KhRow.of(schema, values));
        }
    }
}