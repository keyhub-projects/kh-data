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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TblSchemaTest {

    @Nested
    class CreateSchemaTest {
        @Test
        void testConstructor() {
            TblColumnSchema<?> firstColumn = TblColumnSchema.of("testConstructor", String.class);
            List<TblColumnSchema> schemas = List.of(firstColumn);

            TblSchema tblSchema = TblSchema.of(schemas);

            assertEquals(1, tblSchema.getColumnSize());
            TblColumnSchema<?> testColumnSchema = tblSchema.getColumnSchema(0);
            assertEquals("testConstructor", testColumnSchema.getColumnName());
            assertEquals(String.class, testColumnSchema.getColumnType());
        }
    }

    @Nested
    class findColumnSchemaTest {
        @Test
        void testFindColumnSchema() {
            TblColumnSchema<?> firstColumn = TblColumnSchema.of("testFindColumnSchema", String.class);
            List<TblColumnSchema> schemas = List.of(firstColumn);

            TblSchema tblSchema = TblSchema.of(schemas);

            Optional<TblColumnSchema> result = tblSchema.findColumnSchema("testFindColumnSchema");
            assertNotNull(result.orElse(null));}
    }


    @Nested
    class EqualsTest{
        @Test
        void testEquals() {
            TblColumnSchema<?> firstColumn = TblColumnSchema.of("testEquals1", String.class);
            TblColumnSchema<?> secondColumn = TblColumnSchema.of("testEquals2", String.class);
            List<TblColumnSchema> schemas = List.of(firstColumn, secondColumn);
            TblSchema tblSchema1 = TblSchema.of(schemas);
            TblSchema tblSchema2 = TblSchema.of(schemas);

            assertEquals(tblSchema1, tblSchema2);
        }

        @Test
        void testNotEquals() {
            TblColumnSchema<?> firstColumn = TblColumnSchema.of("testEquals1", String.class);
            TblColumnSchema<?> secondColumn = TblColumnSchema.of("testEquals2", String.class);
            List<TblColumnSchema> schemas1 = List.of(firstColumn, secondColumn);
            TblSchema tblSchema1 = TblSchema.of(schemas1);

            List<TblColumnSchema> schemas2 = List.of(firstColumn);
            TblSchema tblSchema2 = TblSchema.of(schemas2);

            assertNotEquals(tblSchema1, tblSchema2);
        }
    }
}