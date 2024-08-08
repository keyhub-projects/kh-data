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

package keyhub.data.tbl.implement.rowset;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.TblBuilder;
import keyhub.data.tbl.implement.TblBuilderImplement;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RowSetTblBuilder extends TblBuilderImplement {
    private final WeakReference<List<List<Object>>> rows = new WeakReference<>(new ArrayList<>());

    public RowSetTblBuilder(TblSchema schema) {
        super(schema);
    }

    @Override
    public Tbl build() {
        return new RowSetTblImplement(this.schema, this.rows.get());
    }

    @Override
    public TblBuilder addRawRow(List<Object> row) {
        for(int i = 0; i < row.size(); i++) {
            String columnName = this.schema.getColumnNames().get(i);
            if(row.get(i) != null && !this.schema.getColumnTypes().get(columnName).isInstance(row.get(i))) {
                throw new IllegalArgumentException("Row value type does not match schema");
            }
        }
        Objects.requireNonNull(rows.get()).add(row);
        return this;
    }

    @Override
    public TblBuilder addRow(TblRow row) {
        List<Object> rawRow = row.toList();
        Objects.requireNonNull(this.rows.get()).add(rawRow);
        return this;
    }

}
