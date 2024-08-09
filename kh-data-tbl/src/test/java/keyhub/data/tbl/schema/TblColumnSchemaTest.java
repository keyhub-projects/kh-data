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

package keyhub.data.tbl.schema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TblColumnSchemaTest {
    @Nested
    class CreateColumnSchemaTest {
        @Test
        void testConstructor() {
            String expectedColumnName = "testGetColumnName";
            Class<String> expectedColumnType = String.class;

            TblColumnSchema<?> tblColumnSchema = TblColumnSchema.of(expectedColumnName, expectedColumnType);

            assertEquals("testGetColumnName", tblColumnSchema.getColumnName());
            assertEquals(String.class, tblColumnSchema.getColumnType());
        }
    }

    @Nested
    class EqualsTest {
        @Test
        void testEquals_sameObject() {
            TblColumnSchema<?> schema1 = TblColumnSchema.of("id", Integer.class);
            TblColumnSchema<?> schema2 = schema1;

            assertEquals(schema1, schema2);
        }

        @Test
        void testEquals_equalObjects() {
            TblColumnSchema<?> schema1 = TblColumnSchema.of("id", Integer.class);
            TblColumnSchema<?> schema2 = TblColumnSchema.of("id", Integer.class);

            assertEquals(schema1, schema2);
        }

        @Test
        void testEquals_diffColumnName() {
            TblColumnSchema<?> schema1 = TblColumnSchema.of("id", Integer.class);
            TblColumnSchema<?> schema2 = TblColumnSchema.of("name", Integer.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        void testEquals_diffColumnType() {
            TblColumnSchema<?> schema1 = TblColumnSchema.of("id", Integer.class);
            TblColumnSchema<?> schema2 = TblColumnSchema.of("id", String.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        void testEquals_compareToNull() {
            TblColumnSchema<?> schema1 = TblColumnSchema.of("id", Integer.class);

            Assertions.assertNotEquals(schema1, null);
        }
    }
}