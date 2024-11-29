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

package keyhub.data.structure.table.query;

import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.table.KhTable;
import keyhub.data.structure.function.KhRowPredicate;
import keyhub.data.structure.function.KhCellSelector;
import keyhub.data.structure.schema.KhSchema;

import java.util.stream.Stream;

/**
 * Interface for querying a KhTable, providing methods to select columns, filter rows, and convert the query result to a table.
 */
public interface KhTableQuery {

    /**
     * Creates a KhTableQuery from a stream of KhRow.
     *
     * @param rowStream the stream of KhRow to query
     * @return a KhTableQuery instance
     */
    static KhTableQuery from(Stream<KhRow> rowStream) {
        return KhTableQueryImplement.from(rowStream);
    }

    /**
     * Returns the schema of the table being queried.
     *
     * @return the KhSchema of the table
     */
    KhSchema getSchema();

    /**
     * Converts the query result to a KhTable.
     *
     * @return a KhTable instance containing the query result
     */
    KhTable toTbl();

    /**
     * Selects columns by their names.
     *
     * @param columns the names of the columns to select
     * @return the current KhTableQuery instance
     */
    KhTableQuery select(String... columns);

    /**
     * Selects columns by their KhColumn instances.
     *
     * @param columns the KhColumn instances to select
     * @return the current KhTableQuery instance
     */
    KhTableQuery select(KhColumn<?>... columns);

    /**
     * Selects columns using KhCellSelector instances.
     *
     * @param selector the KhCellSelector instances to use for selection
     * @return the current KhTableQuery instance
     */
    KhTableQuery select(KhCellSelector... selector);

    /**
     * Filters rows based on a KhRowPredicate.
     *
     * @param filter the KhRowPredicate to use for filtering rows
     * @return the current KhTableQuery instance
     */
    KhTableQuery where(KhRowPredicate filter);
}