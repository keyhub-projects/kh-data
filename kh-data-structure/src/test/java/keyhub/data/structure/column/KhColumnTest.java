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

package keyhub.data.structure.column;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KhColumnTest {
    @Nested
    class CreateKhColumnKhSchemaTest {
        @Test
        void testConstructor() {
            String expectedColumnName = "testGetColumnName";
            Class<String> expectedColumnType = String.class;

            KhColumn<?> column = KhColumn.of(expectedColumnName, expectedColumnType);

            assertEquals("testGetColumnName", column.getColumnName());
            assertEquals(String.class, column.getColumnType());
        }
    }

    @Nested
    class EqualsTest {
        @Test
        void testEquals_sameObject() {
            KhColumn<?> schema1 = KhColumn.of("id", Integer.class);
            KhColumn<?> schema2 = schema1;

            assertEquals(schema1, schema2);
        }

        @Test
        void testEquals_equalObjects() {
            KhColumn<?> schema1 = KhColumn.of("id", Integer.class);
            KhColumn<?> schema2 = KhColumn.of("id", Integer.class);

            assertEquals(schema1, schema2);
        }

        @Test
        void testEquals_diffColumnName() {
            KhColumn<?> schema1 = KhColumn.of("id", Integer.class);
            KhColumn<?> schema2 = KhColumn.of("name", Integer.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        void testEquals_diffColumnType() {
            KhColumn<?> schema1 = KhColumn.of("id", Integer.class);
            KhColumn<?> schema2 = KhColumn.of("id", String.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        void testEquals_compareToNull() {
            KhColumn<?> schema1 = KhColumn.of("id", Integer.class);

            Assertions.assertNotEquals(schema1, null);
        }
    }
}