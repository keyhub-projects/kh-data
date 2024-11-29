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

package keyhub.data.structure.schema;

import keyhub.data.structure.column.KhColumn;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class KhSchemaTest {

    @Nested
    class CreateKhSchemaTest {
        @Test
        void testConstructor() {
            KhColumn<?> firstColumn = KhColumn.of("testConstructor", String.class);
            List<KhColumn> schemas = List.of(firstColumn);

            KhSchema schema = KhSchema.from(schemas);

            assertEquals(1, schema.getColumnSize());
            KhColumn<?> testColumnSchema = schema.getColumnSchema(0);
            assertEquals("testConstructor", testColumnSchema.getColumnName());
            assertEquals(String.class, testColumnSchema.getColumnType());
        }
    }

    @Nested
    class findKhColumnKhSchemaTest {
        @Test
        void testFindColumnSchema() {
            KhColumn<?> firstColumn = KhColumn.of("testFindColumnSchema", String.class);
            List<KhColumn> schemas = List.of(firstColumn);

            KhSchema schema = KhSchema.from(schemas);

            Optional<KhColumn<?>> result = schema.findColumnSchema("testFindColumnSchema");
            assertNotNull(result.orElse(null));}
    }


    @Nested
    class EqualsTest{
        @Test
        void testEquals() {
            KhColumn<?> firstColumn = KhColumn.of("testEquals1", String.class);
            KhColumn<?> secondColumn = KhColumn.of("testEquals2", String.class);
            List<KhColumn> schemas = List.of(firstColumn, secondColumn);
            KhSchema schema1 = KhSchema.from(schemas);
            KhSchema schema2 = KhSchema.from(schemas);

            assertEquals(schema1, schema2);
        }

        @Test
        void testNotEquals() {
            KhColumn<?> firstColumn = KhColumn.of("testEquals1", String.class);
            KhColumn<?> secondColumn = KhColumn.of("testEquals2", String.class);
            List<KhColumn> schemas1 = List.of(firstColumn, secondColumn);
            KhSchema schema1 = KhSchema.from(schemas1);

            List<KhColumn> schemas2 = List.of(firstColumn);
            KhSchema schema2 = KhSchema.from(schemas2);

            assertNotEquals(schema1, schema2);
        }
    }
}