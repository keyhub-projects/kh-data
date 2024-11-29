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

package keyhub.data.structure.function;

import keyhub.data.structure.row.KhRow;

import java.util.function.Predicate;

/**
 * Functional interface representing a predicate (boolean-valued function) of a KhRow.
 */
@FunctionalInterface
public interface KhRowPredicate extends Predicate<KhRow> {

    /**
     * Evaluates this predicate on the given row.
     *
     * @param row the input row
     * @return true if the input row matches the predicate, otherwise false
     */
    @Override
    boolean test(KhRow row);

    /**
     * Creates a row predicate that evaluates a cell in the specified column using the given cell predicate.
     *
     * @param columnName the name of the column
     * @param predicate the cell predicate
     * @return a row predicate that evaluates the specified column
     */
    static KhRowPredicate is(String columnName, KhCellPredicate predicate) {
        return tblRow -> tblRow.findCell(columnName)
                .map(predicate::test)
                .orElse(false);
    }

    /**
     * Creates a row predicate that evaluates a cell at the specified column index using the given cell predicate.
     *
     * @param columnIndex the index of the column
     * @param predicate the cell predicate
     * @return a row predicate that evaluates the specified column index
     */
    static KhRowPredicate is(int columnIndex, KhCellPredicate predicate) {
        return tblRow -> predicate.test(tblRow.getCell(columnIndex));
    }
}