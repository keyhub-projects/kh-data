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

package keyhub.data.structure.row;

import keyhub.data.structure.cell.KhCell;
import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.schema.KhSchema;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing a row in a table, which consists of multiple cells.
 */
public interface KhRow extends Iterable<KhCell> {

    /**
     * Creates a row from a schema and a variable number of values.
     *
     * @param schema the schema of the row
     * @param values the values of the row
     * @return a KhRow instance
     */
    static KhRow of(KhSchema schema, Object... values) {
        return KhRowImplement.of(schema, Arrays.stream(values).toList());
    }

    /**
     * Creates a row from a variable number of cells.
     *
     * @param cells the cells of the row
     * @return a KhRow instance
     */
    static KhRow of(KhCell... cells) {
        return KhRowImplement.of(cells);
    }

    /**
     * Creates a row from a list of cells.
     *
     * @param cells the cells of the row
     * @return a KhRow instance
     */
    static KhRow of(List<KhCell> cells) {
        return KhRowImplement.of(cells);
    }

    /**
     * Creates a row from a schema and a list of values.
     *
     * @param schema the schema of the row
     * @param values the values of the row
     * @return a KhRow instance
     */
    static KhRow of(KhSchema schema, List<Object> values) {
        return KhRowImplement.of(schema, values);
    }

    /**
     * Gets the schema of the row.
     *
     * @return the schema
     */
    KhSchema getSchema();

    /**
     * Converts the row to a list of values.
     *
     * @return a list of values
     */
    List<Object> toList();

    /**
     * Finds a cell by its column name.
     *
     * @param columnName the name of the column
     * @param <T> the type of the cell
     * @return an Optional containing the cell if found, or empty if not found
     */
    <T> Optional<KhCell<T>> findCell(String columnName);

    /**
     * Gets a cell by its column index.
     *
     * @param columnIndex the index of the column
     * @param <T> the type of the cell
     * @return the cell
     */
    <T> KhCell<T> getCell(int columnIndex);

    /**
     * Gets all cells in the row.
     *
     * @return a list of cells
     */
    List<KhCell> getCells();

    /**
     * Selects a subset of columns from the row.
     *
     * @param columns the names of the columns to select
     * @return a new KhRow instance containing the selected columns
     */
    KhRow select(String[] columns);

    /**
     * Selects a subset of columns from the row.
     *
     * @param columns the columns to select
     * @return a new KhRow instance containing the selected columns
     */
    KhRow select(KhColumn[] columns);
}