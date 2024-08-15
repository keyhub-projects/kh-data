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

import keyhub.data.tbl.schema.TblColumn;
import keyhub.data.tbl.schema.TblSchema;

@FunctionalInterface
public interface TblJoinSchemaPredicate {
    boolean test(TblSchema left, TblSchema right);

    static TblJoinSchemaPredicate on(String leftColumnName, String rightColumnName, TblJoinColumnPredicate predicate){
        return (left, right) -> {
            TblColumn<?> leftColumn = left.findColumnSchema(leftColumnName).orElse(null);
            TblColumn<?> rightColumn = right.findColumnSchema(rightColumnName).orElse(null);
            if(leftColumn == null || rightColumn == null) {
                return false;
            }
            return predicate.test(leftColumn, rightColumn);
        };
    }

    static TblJoinSchemaPredicate on(String columnName, TblJoinColumnPredicate predicate) {
        return on(columnName, columnName, predicate);
    }

    static TblJoinSchemaPredicate and(String columnName, TblJoinColumnPredicate predicate) {
        return on(columnName, columnName, predicate);
    }

    static TblJoinSchemaPredicate and(String leftColumnName, String rightColumnName, TblJoinColumnPredicate predicate){
        return on(leftColumnName, rightColumnName, predicate);
    }
}

