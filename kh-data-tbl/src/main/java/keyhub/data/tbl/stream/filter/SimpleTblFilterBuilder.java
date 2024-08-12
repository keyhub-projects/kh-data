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

package keyhub.data.tbl.stream.filter;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.stream.filter.implement.*;

public class SimpleTblFilterBuilder extends TblFilterBuilderImplement {
    @Override
    public Tbl build(){
        if(this.operator == null){
            throw new IllegalArgumentException("operator is null");
        }else if(this.tbl == null) {
            throw new IllegalArgumentException("tbl is null");
        }else if(this.column == null){
            throw new IllegalArgumentException("column is null");
        }

        TblFilter operator = switch (this.operator) {
            case IS_NULL -> IsNullFilter.of(this.tbl, this.column);
            case IS_NOT_NULL -> IsNotNullFilter.of(this.tbl, this.column);
            case EQUAL -> EqualFilter.of(this.tbl, this.column, this.value);
            case NOT_EQUAL -> NotEqualFilter.of(this.tbl, this.column, this.value);
            case GREATER_THAN -> GreaterThanFilter.of(this.tbl, this.column, this.value);
            case LESS_THAN -> LessThanFilter.of(this.tbl, this.column, this.value);
            case GREATER_THAN_OR_EQUAL -> GreaterThanOrEqualFilter.of(this.tbl, this.column, this.value);
            case LESS_THAN_OR_EQUAL -> LessThanOrEqualFilter.of(this.tbl, this.column, this.value);
            case IN -> InFilter.of(this.tbl, this.column, this.value);
            case NOT_IN -> NotInFilter.of(this.tbl, this.column, this.value);
            case LIKE -> LikeFilter.of(this.tbl, this.column, this.value);
            default -> throw new IllegalArgumentException("operator is not supported");
        };

        return operator.toTbl();
    }
}
