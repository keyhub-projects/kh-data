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

package keyhub.data.table;

import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;

import java.util.List;

public abstract class KhTableBuilderImplement implements KhTableBuilder {
    protected final KhSchema SCHEMA;

    protected KhTableBuilderImplement(KhSchema schema) {
        this.SCHEMA = schema;
    }

    public static KhTableBuilder forRowSet(KhSchema schema){
        return new RowSetTableBuilder(schema);
    }

    @Override
    public KhTableBuilder addRawRows(List<List<Object>> rows) {
        for(List<Object> row : rows) {
            addRawRow(row);
        }
        return this;
    }

    @Override
    public KhTableBuilder addRows(List<KhRow> rows) {
        for(KhRow row : rows) {
            addRow(row);
        }
        return this;
    }
}
