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

package keyhub.data.column;

import java.util.Objects;

public abstract class KhColumnImplement<T> implements KhColumn<T> {
    protected abstract String columnName();
    protected abstract Class<T> columnType();

    public static <T> KhColumn<T> of(String columnName, Class<T> columnType){
        return new KhColumnValue<>(columnName, columnType);
    }
    public String getColumnName(){
        return columnName();
    }
    public Class<T> getColumnType(){
        return columnType();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KhColumn other)) {
            return false;
        }
        return Objects.equals(columnName(), other.getColumnName())
                && Objects.equals(columnType(), other.getColumnType());
    }
    @Override
    public int hashCode() {
        return Objects.hash(columnName(), columnType());
    }
    @Override
    public String toString() {
        return columnName() + ": " + columnType().getSimpleName();
    }
}
