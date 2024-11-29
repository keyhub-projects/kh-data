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

/**
 * Interface representing a structure based on a schema, providing methods to access column schemas and metadata.
 */
public interface KhSchemaBasedStructure {

    /**
     * Gets the schema of a column by its index.
     *
     * @param index the index of the column
     * @return the column schema
     */
    KhColumn<?> getColumnSchema(int index);

    /**
     * Gets the schema of the structure.
     *
     * @return the schema
     */
    KhSchema getSchema();

    /**
     * Gets the number of columns in the structure.
     *
     * @return the number of columns
     */
    int getColumnSize();

    /**
     * Gets the name of a column by its index.
     *
     * @param index the index of the column
     * @return the name of the column
     */
    String getColumnName(int index);

    /**
     * Gets the type of a column by its index.
     *
     * @param index the index of the column
     * @return the type of the column
     */
    Class<?> getColumnType(int index);

    /**
     * Gets the index of a column by its name.
     *
     * @param column the name of the column
     * @return the index of the column
     */
    int getColumnIndex(String column);
}