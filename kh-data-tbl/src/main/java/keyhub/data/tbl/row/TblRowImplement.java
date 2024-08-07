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

package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;
import java.util.Optional;

public abstract class TblRowImplement implements TblRow {
    public static TblRow of(TblSchema schema, List<Object> values) {
        if(schema.getColumnTypes().size() != values.size()){
            throw new IllegalArgumentException("The number of columns and values must be the same");
        }
        for(int i = 0; i < values.size(); i++) {
            String columnName = schema.getColumnNames().get(i);
            if(values.get(i) != null && !schema.getColumnTypes().get(columnName).isInstance(values.get(i))) {
                throw new IllegalArgumentException("Row value type does not match schema");
            }
        }
        return new TblRowValue(schema, values);
    }

    protected abstract TblSchema schema();
    protected abstract List<Object> values();

    @Override
    public TblSchema getSchema() {
        return schema();
    }
    @Override
    public List<Object> toList() {
        return values();
    }
    @Override
    public <T> Optional<T> findCell(String columnName) {
        Optional<TblColumnSchema> schema = schema().findColumnSchema(columnName);
        if (schema.isEmpty()) {
            return Optional.empty();
        }
        int index = schema().getColumnNames().indexOf(columnName);
        Class<T> columnType = (Class<T>) schema.get().getColumnType();
        return Optional.ofNullable(columnType.cast(values().get(index)));
    }
    @Override
    public <T> T getCell(int columnIndex) {
        if(columnIndex < 0 || columnIndex >= schema().getColumnSize()){
            throw new IllegalArgumentException("Column index out of bounds");
        }
        return (T) values().get(columnIndex);
    }

}
