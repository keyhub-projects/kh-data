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

package keyhub.data.tbl.schema;

import java.util.*;

public abstract class TblSchemaImplement implements TblSchema{
    public static TblSchema from(List<TblColumn> tblColumns){
        return new TblSchemaValue(tblColumns);
    }
    public static TblSchema from(TblSchema tblSchema) {
        return new TblSchemaValue(tblSchema);
    }
    public static TblSchemaValue.TblSchemaValueBuilder builder(){
        return new TblSchemaValue.TblSchemaValueBuilder();
    }
    public static TblSchema empty(){
        return new TblSchemaValue();
    }

    protected abstract List<String> columnNames();
    protected abstract Map<String, Class<?>> columnTypes();
    @Override
    public int getColumnSize(){
        return columnNames().size();
    }
    @Override
    public List<String> getColumnNames(){
        return columnNames();
    }
    @Override
    public Map<String, Class<?>> getColumnTypes(){
        return columnTypes();
    }
    @Override
    public Optional<TblColumn<?>> findColumnSchema(String columnName){
        int index = columnNames().indexOf(columnName);
        if(index == -1){
            return Optional.empty();
        }
        return Optional.ofNullable(getColumnSchema(index));
    }

    @Override
    public <T> TblColumn<T> getColumnSchema(int index){
        String columnName = columnNames().get(index);
        @SuppressWarnings("unchecked")
        Class<T> columnType = (Class<T>) columnTypes().get(columnName);
        return TblColumn.of(columnName, columnType);
    }
    @Override
    public List<TblColumn> getColumnSchemas(){
        List<TblColumn> columns = new ArrayList<>();
        for(int i = 0; i < getColumnSize(); i++){
            columns.add(getColumnSchema(i));
        }
        return columns;
    }

    @Override
    public int getColumnIndex(String columnName){
        return columnNames().indexOf(columnName);
    }

    @Override
    public boolean contains(String column){
        return columnNames().contains(column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TblSchema other)) {
            return false;
        }
        return columnNames().equals(other.getColumnNames()) && columnTypes().equals(other.getColumnTypes());
    }
    @Override
    public int hashCode() {
        return Objects.hash(columnNames(), columnTypes());
    }
    @Override
    public String toString() {
        return columnNames().toString();
    }
    @Override
    public Iterator<TblColumn> iterator(){
        return this.getColumnSchemas().iterator();
    }
}
