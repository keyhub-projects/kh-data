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

import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.schema.KhSchema;

import java.util.List;

/**
 * Interface for building a KhTable with various methods to add rows and raw data.
 */
public interface KhTableBuilder {

    /**
     * Creates a KhTableBuilder for a given schema.
     *
     * @param schema the schema to be used by the builder
     * @return a KhTableBuilder instance
     */
    static KhTableBuilder forRowSet(KhSchema schema) {
        return KhTableBuilderImplement.forRowSet(schema);
    }

    /**
     * Adds a raw row to the table being built.
     *
     * @param row a list of objects representing the raw row data
     * @return the current KhTableBuilder instance
     */
    KhTableBuilder addRawRow(List<Object> row);

    /**
     * Adds multiple raw rows to the table being built.
     *
     * @param rows a list of lists, where each inner list represents a raw row
     * @return the current KhTableBuilder instance
     */
    KhTableBuilder addRawRows(List<List<Object>> rows);

    /**
     * Adds a KhRow to the table being built.
     *
     * @param row the KhRow to be added
     * @return the current KhTableBuilder instance
     */
    KhTableBuilder addRow(KhRow row);

    /**
     * Adds multiple KhRows to the table being built.
     *
     * @param rows a list of KhRow instances to be added
     * @return the current KhTableBuilder instance
     */
    KhTableBuilder addRows(List<KhRow> rows);

    /**
     * Builds and returns the KhTable instance.
     *
     * @return the constructed KhTable instance
     */
    KhTable build();
}