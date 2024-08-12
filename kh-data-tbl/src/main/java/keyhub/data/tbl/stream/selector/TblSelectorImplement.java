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

package keyhub.data.tbl.stream.selector;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.stream.filter.TblFilterType;

import java.util.*;

public abstract class TblSelectorImplement implements TblSelector {
    protected Tbl tbl;
    protected final List<String> selectColumns;
    protected final List<String> whereColumns;
    protected final Map<Integer, TblFilterType> whereOperatorMap;
    protected final Map<Integer, Object> whereValueMap;

    public TblSelectorImplement(Tbl tbl) {
        this.tbl = tbl;
        this.selectColumns = new ArrayList<>();
        this.whereColumns = new ArrayList<>();
        this.whereOperatorMap = new WeakHashMap<>();
        this.whereValueMap = new WeakHashMap<>();
    }

    static TblSelector from(Tbl tbl){
        return new SimpleTblSelector(tbl);
    }

    @Override
    public TblSelector select(String... columns) {
        this.selectColumns.addAll(Arrays.asList(columns));
        return this;
    }

    @Override
    public TblSelector select(List<String> columns) {
        this.selectColumns.addAll(columns);
        return this;
    }

    @Override
    public TblSelector selectAll() {
        this.selectColumns.addAll(this.tbl.getSchema().getColumnNames());
        return this;
    }

    @Override
    public TblSelector where(String column, TblFilterType operator) {
        this.whereColumns.add(column);
        int index = this.whereColumns.size() - 1;
        this.whereOperatorMap.put(index, operator);
        return this;
    }

    @Override
    public TblSelector where(String column, TblFilterType operator, Object value) {
        this.whereColumns.add(column);
        int index = this.whereColumns.size() - 1;
        this.whereOperatorMap.put(index, operator);
        this.whereValueMap.put(index, value);
        return this;
    }

    @Override
    public Tbl toTbl() {
        whereColumns.forEach(column->{
            int index = whereColumns.indexOf(column);
            TblFilterType operator = whereOperatorMap.get(index);
            Object value = whereValueMap.get(index);
            if(value != null) {
                computeWhere(column, operator, value);
            }else{
                computeWhere(column, operator);
            }
        });
        computeSelect();
        return this.tbl;
    }

    protected abstract void computeWhere(String column, TblFilterType operator);
    protected abstract void computeWhere(String column, TblFilterType operator, Object value);
    protected abstract void computeSelect();
}
