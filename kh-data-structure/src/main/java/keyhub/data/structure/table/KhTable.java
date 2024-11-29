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

package keyhub.data.structure.table;

import keyhub.data.structure.cell.KhCell;
import keyhub.data.structure.join.KhJoinable;
import keyhub.data.structure.schema.KhSchemaBasedStructure;
import keyhub.data.structure.stream.KhStream;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.stream.join.KhStreamJoin;
import keyhub.data.structure.table.join.KhTableJoin;
import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.table.query.KhTableQuery;

import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a table structure backed by a schema, capable of performing various
 * operations such as querying, streaming, and joining with other table or stream-based
 * structures.
 *
 * This interface extends {@code Iterable<KhRow>}, {@code KhSchemaBasedStructure}, and
 * {@code KhJoinable}, offering methods to manipulate and retrieve data stored within
 * the table.
 */
public interface KhTable extends Iterable<KhRow>, KhSchemaBasedStructure, KhJoinable {

    /**
     * Returns an empty instance of {@code KhTable}.
     *
     * @return an empty {@code KhTable} instance
     */
    static KhTable empty() {
        return KhTableImplement.empty();
    }

    /**
     * Returns an empty instance of {@code KhTable} with the specified schema.
     *
     * @param schema the schema to be used by the returned empty table
     * @return an empty {@code KhTable} instance based on the provided schema
     */
    static KhTable empty(KhSchema schema) {
        return KhTableImplement.empty(schema);
    }

    /**
     * Creates a {@code KhTable} instance from a given list. The list can contain
     * elements of different types, including {@code KhRow} and {@code Map<String, Object>}.
     * Based on the type of the first element, the method will either create a table
     * with rows directly from the list or convert the list of maps into a table.
     *
     * @param list the list of objects from which the {@code KhTable} will be created.
     *             The list can be null or empty, which will result in an empty table.
     * @return a {@code KhTable} instance created from the provided list.
     */
    static KhTable from(List<?> list) {
        return KhTableImplement.from(list);
    }

    /**
     * Creates a {@code KhTable} instance from the given {@code KhStream}.
     * The method extracts all rows from the stream and constructs a table
     * using those rows.
     *
     * @param stream the {@code KhStream} from which the {@code KhTable} will be created.
     *               Must not be null.
     * @return a {@code KhTable} instance containing rows extracted from the provided {@code KhStream}.
     */
    static KhTable from(KhStream stream){
        return KhTableImplement.from(stream);
    }

    /**
     * Creates a new instance of {@code KhTable} using the specified schema and raw row data.
     *
     * @param schema the {@code KhSchema} defining the structure and columns of the table.
     * @param rawRows a list of raw data rows, where each row is represented as a list of objects.
     *                The data in each row should align with the columns defined in the schema.
     * @return a {@code KhTable} instance populated with the supplied raw row data and structured
     *         according to the specified schema.
     */
    static KhTable of(KhSchema schema, List<List<Object>> rawRows){
        return KhTableImplement.of(schema, rawRows);
    }

    /**
     * Creates a builder for constructing a {@code KhTable} with the specified schema.
     *
     * @param schema the schema to be used by the builder
     * @return a {@code KhTableBuilder} instance for constructing a table
     */
    static KhTableBuilder builder(KhSchema schema){
        return KhTableBuilder.forRowSet(schema);
    }

    /**
     * Returns the number of rows in the table.
     *
     * @return the row count
     */
    int count();

    /**
     * Retrieves the row at the specified index.
     *
     * @param index the index of the row to retrieve
     * @return the {@code KhRow} at the specified index
     */
    KhRow getRow(int index);

    /**
     * Returns a list of all rows in the table.
     *
     * @return a list of {@code KhRow} instances
     */
    List<KhRow> getRows();

    /**
     * Retrieves the raw data of the row at the specified index.
     *
     * @param index the index of the row to retrieve
     * @return a list of objects representing the raw data of the row
     */
    List<Object> getRawRow(int index);

    /**
     * Retrieves the cell at the specified column and row indices.
     *
     * @param columnIndex the index of the column
     * @param rowIndex the index of the row
     * @return the {@code KhCell} at the specified column and row indices
     */
    KhCell<?> getCell(int columnIndex, int rowIndex);

    /**
     * Finds the cell at the specified column name and row index.
     *
     * @param columnName the name of the column
     * @param rowIndex the index of the row
     * @return an {@code Optional} containing the {@code KhCell} if found, or empty if not found
     */
    Optional<KhCell<?>> findCell(String columnName, int rowIndex);

    /**
     * Returns a stream of rows in the table.
     *
     * @return a {@code Stream} of {@code KhRow} instances
     */
    Stream<KhRow> stream();

    /**
     * Returns a query object for querying the table.
     *
     * @return a {@code KhTableQuery} instance
     */
    KhTableQuery query();

    /**
     * Performs a left join with the specified table.
     *
     * @param right the table to join with
     * @return a {@code KhTableJoin} representing the result of the left join
     */
    KhTableJoin leftJoin(KhTable right);

    /**
     * Performs an inner join with the specified table.
     *
     * @param right the table to join with
     * @return a {@code KhTableJoin} representing the result of the inner join
     */
    KhTableJoin innerJoin(KhTable right);

    /**
     * Performs a left join with the specified stream.
     *
     * @param right the stream to join with
     * @return a {@code KhStreamJoin} representing the result of the left join
     */
    KhStreamJoin leftJoin(KhStream right);

    /**
     * Performs an inner join with the specified stream.
     *
     * @param right the stream to join with
     * @return a {@code KhStreamJoin} representing the result of the inner join
     */
    KhStreamJoin innerJoin(KhStream right);

    /**
     * Converts the table to a list of maps, where each map represents a row.
     *
     * @return a list of maps representing the rows of the table
     */
    List<Map<String, Object>> toRowMapList();

    /**
     * Converts the table to a map of column lists, where each list contains the values of a column.
     *
     * @return a map of column lists
     */
    Map<String, List<Object>> toColumnListMap();

    /**
     * Converts the table to a stream.
     *
     * @return a {@code KhStream} representing the table
     */
    KhStream toStream();
}