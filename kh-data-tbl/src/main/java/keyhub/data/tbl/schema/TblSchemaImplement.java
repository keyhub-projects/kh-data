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

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TblSchemaImplement implements TblSchema{
    public static TblSchema of(List<TblColumnSchema> tblColumnSchemas){
        return new TblSchemaValue(tblColumnSchemas);
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
    public Optional<TblColumnSchema> findColumnSchema(String columnName){
        int index = columnNames().indexOf(columnName);
        if(index == -1){
            return Optional.empty();
        }
        return Optional.ofNullable(getColumnSchema(index));
    }

    @Override
    public TblColumnSchema<?> getColumnSchema(int index){
        String columnName = columnNames().get(index);
        Class<?> columnType = columnTypes().get(columnName);
        return TblColumnSchema.of(columnName, columnType);
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
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        TblSchemaImplement that = (TblSchemaImplement) o;
        return columnNames().equals(that.columnNames()) && columnTypes().equals(that.columnTypes());
    }

}
