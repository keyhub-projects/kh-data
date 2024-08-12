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
import keyhub.data.tbl.stream.TblStream;
import keyhub.data.tbl.stream.filter.TblFilterType;

import java.util.Iterator;
import java.util.Spliterator;

public class SimpleTblSelector extends TblSelectorImplement {
    public SimpleTblSelector(Tbl tbl) {
        super(tbl);
    }

    @Override
    protected void computeWhere(String column, TblFilterType operator, Object value) {
        this.tbl = this.tbl.where(column, operator, value);
    }
    @Override
    protected void computeWhere(String column, TblFilterType operator) {
        this.tbl = this.tbl.where(column, operator);
    }
    @Override
    protected void computeSelect() {
        this.tbl = this.tbl.select(selectColumns);
    }

    @Override
    public Iterator<Tbl> iterator() {
        return null;
    }

    @Override
    public Spliterator<Tbl> spliterator() {
        return null;
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public TblStream sequential() {
        return null;
    }

    @Override
    public TblStream parallel() {
        return null;
    }

    @Override
    public TblStream unordered() {
        return null;
    }

    @Override
    public TblStream onClose(Runnable closeHandler) {
        return null;
    }

    @Override
    public void close() {

    }
}
