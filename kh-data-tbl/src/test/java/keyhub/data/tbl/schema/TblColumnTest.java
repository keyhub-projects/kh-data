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

class TblColumnTest {
    @Nested
    class CreateColumnSchemaTest {
        @Test
        void testConstructor() {
            String expectedColumnName = "testGetColumnName";
            Class<String> expectedColumnType = String.class;

            TblColumn<?> tblColumn = TblColumn.of(expectedColumnName, expectedColumnType);

            assertEquals("testGetColumnName", tblColumn.getColumnName());
            assertEquals(String.class, tblColumn.getColumnType());
        }
    }

    @Nested
    class EqualsTest {
        @Test
        void testEquals_sameObject() {
            TblColumn<?> schema1 = TblColumn.of("id", Integer.class);
            TblColumn<?> schema2 = schema1;

            assertEquals(schema1, schema2);
        }

        @Test
        void testEquals_equalObjects() {
            TblColumn<?> schema1 = TblColumn.of("id", Integer.class);
            TblColumn<?> schema2 = TblColumn.of("id", Integer.class);

            assertEquals(schema1, schema2);
        }

        @Test
        void testEquals_diffColumnName() {
            TblColumn<?> schema1 = TblColumn.of("id", Integer.class);
            TblColumn<?> schema2 = TblColumn.of("name", Integer.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        void testEquals_diffColumnType() {
            TblColumn<?> schema1 = TblColumn.of("id", Integer.class);
            TblColumn<?> schema2 = TblColumn.of("id", String.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        void testEquals_compareToNull() {
            TblColumn<?> schema1 = TblColumn.of("id", Integer.class);

            Assertions.assertNotEquals(schema1, null);
        }
    }
}