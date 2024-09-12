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

package keyhub.data.tbl;

import keyhub.data.row.Row;
import keyhub.data.schema.Schema;

import java.util.List;

public abstract class TblBuilderImplement implements TblBuilder {
    protected final Schema schema;

    protected TblBuilderImplement(Schema schema) {
        this.schema = schema;
    }

    public static TblBuilder forRowSet(Schema schema){
        return new RowSetTblBuilder(schema);
    }

    @Override
    public TblBuilder addRawRows(List<List<Object>> rows) {
        for(List<Object> row : rows) {
            addRawRow(row);
        }
        return this;
    }

    @Override
    public TblBuilder addRows(List<Row> rows) {
        for(Row row : rows) {
            addRow(row);
        }
        return this;
    }
}
