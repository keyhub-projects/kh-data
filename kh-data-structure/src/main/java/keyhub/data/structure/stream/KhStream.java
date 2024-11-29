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

package keyhub.data.structure.stream;

import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.function.KhCellSelector;
import keyhub.data.structure.function.KhRowPredicate;
import keyhub.data.structure.join.KhJoinable;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.schema.KhSchemaBasedStructure;
import keyhub.data.structure.stream.join.KhStreamJoin;
import keyhub.data.structure.table.KhTable;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

/**
 * Interface representing a stream of KhRow objects, providing methods for selection, filtering, joining, and conversion.
 */
public interface KhStream extends BaseStream<KhRow, KhStream>, KhSchemaBasedStructure, KhJoinable {

    /**
     * Creates a KhStream from a KhTable.
     *
     * @param tbl the KhTable to create the stream from
     * @return a KhStream instance
     */
    static KhStream from(KhTable tbl) {
        return KhStreamImplement.from(tbl);
    }

    /**
     * Creates a KhStream from a schema and a stream of KhRow.
     *
     * @param schema the schema to be used by the stream
     * @param rowStream the stream of KhRow to be used
     * @return a KhStream instance
     */
    static KhStream of(KhSchema schema, Stream<KhRow> rowStream) {
        return KhStreamImplement.of(schema, rowStream);
    }

    /**
     * Selects columns by their names.
     *
     * @param columns the names of the columns to select
     * @return the current KhStream instance
     */
    KhStream select(String... columns);

    /**
     * Selects columns by their KhColumn instances.
     *
     * @param columns the KhColumn instances to select
     * @return the current KhStream instance
     */
    KhStream select(KhColumn<?>... columns);

    /**
     * Selects columns using KhCellSelector instances.
     *
     * @param selector the KhCellSelector instances to use for selection
     * @return the current KhStream instance
     */
    KhStream select(KhCellSelector... selector);

    /**
     * Filters rows based on a KhRowPredicate.
     *
     * @param filter the KhRowPredicate to use for filtering rows
     * @return the current KhStream instance
     */
    KhStream where(KhRowPredicate filter);

    /**
     * Performs a left join with another KhTable.
     *
     * @param tbl the KhTable to join with
     * @return a KhStreamJoin instance representing the left join result
     */
    KhStreamJoin leftJoin(KhTable tbl);

    /**
     * Performs an inner join with another KhTable.
     *
     * @param tbl the KhTable to join with
     * @return a KhStreamJoin instance representing the inner join result
     */
    KhStreamJoin innerJoin(KhTable tbl);

    /**
     * Returns the underlying stream of KhRow.
     *
     * @return the stream of KhRow
     */
    Stream<KhRow> getRowStream();

    /**
     * Converts the stream to a list of KhRow.
     *
     * @return a list of KhRow
     */
    List<KhRow> toList();

    /**
     * Converts the stream to a KhTable.
     *
     * @return a KhTable instance
     */
    KhTable toTable();
}