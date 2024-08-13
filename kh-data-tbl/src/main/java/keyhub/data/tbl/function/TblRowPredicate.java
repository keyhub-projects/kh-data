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

package keyhub.data.tbl.function;

import keyhub.data.tbl.row.TblRow;

import java.util.function.Predicate;

@FunctionalInterface
public interface TblRowPredicate extends Predicate<TblRow> {

    @Override
    boolean test(TblRow tblRow);

    static <T> TblRowPredicate is(String columnName, TblCellPredicate<T> predicate) {
        return tblRow -> tblRow.findCell(columnName)
                .map(predicate::test)
                .orElse(false);
    }
    static <T> TblRowPredicate is(int columnIndex, TblCellPredicate<T> predicate) {
        return tblRow -> predicate.test(tblRow.getCell(columnIndex));
    }

    static TblRowPredicate in(String columnName, Object ... value){
        return tblRow -> tblRow.findCell(columnName)
                .map(cell -> {
                    for (Object v : value) {
                        if (cell.getValue().equals(v)) {
                            return true;
                        }
                    }
                    return false;
                })
                .orElse(false);
    }
}