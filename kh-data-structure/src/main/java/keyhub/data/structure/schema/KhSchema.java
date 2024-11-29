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

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface representing a schema for a table, which consists of multiple columns.
 */
public interface KhSchema extends Iterable<KhColumn> {

    /**
     * Creates an empty schema.
     *
     * @return an empty KhSchema instance
     */
    static KhSchema empty() {
        return KhSchemaImplement.empty();
    }

    /**
     * Creates a schema from a list of columns.
     *
     * @param columns the list of columns to create the schema from
     * @return a KhSchema instance
     */
    static KhSchema from(List<KhColumn> columns) {
        return KhSchemaImplement.from(columns);
    }

    /**
     * Creates a schema from an existing schema.
     *
     * @param schema the existing schema to create the new schema from
     * @return a KhSchema instance
     */
    static KhSchema from(KhSchema schema) {
        return KhSchemaImplement.from(schema);
    }

    /**
     * Creates a builder for KhSchemaValue.
     *
     * @return a KhSchemaValueBuilder instance
     */
    static KhSchemaValue.KhSchemaValueBuilder builder() {
        return KhSchemaImplement.builder();
    }

    /**
     * Gets the number of columns in the schema.
     *
     * @return the number of columns
     */
    int getColumnSize();

    /**
     * Gets the names of all columns in the schema.
     *
     * @return a list of column names
     */
    List<String> getColumnNames();

    /**
     * Gets the types of all columns in the schema.
     *
     * @return a map of column names to their types
     */
    Map<String, Class<?>> getColumnTypes();

    /**
     * Finds a column schema by its name.
     *
     * @param columnName the name of the column to find
     * @return an Optional containing the column schema if found, or empty if not found
     */
    Optional<KhColumn<?>> findColumnSchema(String columnName);

    /**
     * Gets the schema of a column by its index.
     *
     * @param index the index of the column
     * @param <T> the type of the column
     * @return the column schema
     */
    <T> KhColumn<T> getColumnSchema(int index);

    /**
     * Checks if this schema is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the schemas are equal, false otherwise
     */
    @Override
    boolean equals(Object o);

    /**
     * Gets the schemas of all columns.
     *
     * @return a list of column schemas
     */
    List<KhColumn> getColumnSchemas();

    /**
     * Gets the index of a column by its name.
     *
     * @param columnName the name of the column
     * @return the index of the column
     */
    int getColumnIndex(String columnName);

    /**
     * Checks if the schema contains a column with the specified name.
     *
     * @param column the name of the column to check
     * @return true if the column is present, false otherwise
     */
    boolean contains(String column);

    /**
     * Selects a subset of columns from the schema.
     *
     * @param columns the names of the columns to select
     * @return a new KhSchema instance containing the selected columns
     */
    KhSchema select(String... columns);
}