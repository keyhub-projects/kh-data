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

package keyhub.data.cell;

import keyhub.data.column.Column;

public class CellValue<T> extends CellImplement<T> {
    private final Column<T> columnSchema;
    private final T value;

    public CellValue(Column<T> columnSchema) {
        this.columnSchema = columnSchema;
        this.value = null;
    }

    public CellValue(Column<T> columnSchema, T value) {
        this.columnSchema = columnSchema;
        this.value = value;
    }
    public Column<T> getColumnSchema() {
        return columnSchema;
    }

    @Override
    public T value() {
        return value;
    }
    @Override
    public Column<T> columnSchema() {
        return columnSchema;
    }
    @Override
    public Class<T> columnType() {
        return columnSchema.getColumnType();
    }
}
