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

package keyhub.data.structure.row;

import keyhub.data.structure.cell.KhCell;
import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.schema.KhSchema;

import java.util.*;

public abstract class KhRowImplement implements KhRow {
    public static KhRow of(KhSchema schema, List<Object> values) {
        if(schema.getColumnSize() != values.size()){
            throw new IllegalArgumentException("In the KhRow, the number of columns and values must be the same");
        }
        for(int i = 0; i < values.size(); i++) {
            String columnName = schema.getColumnNames().get(i);
            if(values.get(i) != null && !schema.getColumnTypes().get(columnName).isInstance(values.get(i))) {
                throw new IllegalArgumentException("KhRow value type does not match schema");
            }
        }
        return new KhRowValue(schema, values);
    }
    static KhRow of(KhCell... cells) {
        return new KhRowValue(cells);
    }
    static KhRow of(List<KhCell> cells) {
        return new KhRowValue(cells);
    }

    protected abstract KhSchema schema();
    protected abstract List<Object> values();

    @Override
    public KhSchema getSchema() {
        return schema();
    }
    @Override
    public List<Object> toList() {
        return values();
    }
    @Override
    public <T> Optional<KhCell<T>> findCell(String columnName) {
        Optional<KhColumn<?>> opSchema = schema().findColumnSchema(columnName);
        if (opSchema.isEmpty()) {
            return Optional.empty();
        }
        int index = schema().getColumnNames().indexOf(columnName);
        @SuppressWarnings("unchecked")
        KhColumn<T> columnSchema = (KhColumn<T>) opSchema.get();
        Class<T> columnType = columnSchema.getColumnType();
        T value = columnType.cast(values().get(index));
        return Optional.of(KhCell.of(columnSchema, value));
    }
    @Override
    public <T> KhCell<T> getCell(int columnIndex) {
        if(columnIndex < 0 || columnIndex >= schema().getColumnSize()){
            throw new IllegalArgumentException("KhColumn index out of bounds");
        }
        KhColumn<T> columnSchema = schema().getColumnSchema(columnIndex);
        @SuppressWarnings("unchecked")
        T value = (T) values().get(columnIndex);
        return KhCell.of(columnSchema, value);
    }
    @Override
    public List<KhCell> getCells(){
        List<KhCell> celss = new ArrayList<>();
        for(int i = 0; i < schema().getColumnSize(); i++){
            celss.add(getCell(i));
        }
        return celss;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KhRow other)) {
            return false;
        }
        return values().equals(other.toList())
                && schema().equals(other.getSchema());
    }
    @Override
    public int hashCode() {
        return Objects.hash(values(), schema());
    }
    @Override
    public String toString() {
        return values().toString();
    }
    @Override
    public Iterator<KhCell> iterator() {
        return this.getCells().iterator();
    }
    @Override
    public KhRow select(String... columns){
        List<Object> newValues = new ArrayList<>();
        for(String column : columns){
            Optional<KhCell<Object>> opCell = findCell(column);
            if(opCell.isEmpty()){
                throw new IllegalArgumentException("Column not found");
            }
            newValues.add(opCell.get().getValue());
        }
        return KhRow.of(schema().select(columns), newValues);
    }
    @Override
    public KhRow select(KhColumn[] columns){
        List<Object> newValues = new ArrayList<>();
        for(KhColumn column : columns){
            Optional<KhCell<Object>> opCell = findCell(column.getColumnName());
            if(opCell.isEmpty()){
                throw new IllegalArgumentException("Column not found");
            }
            newValues.add(opCell.get().getValue());
        }
        return KhRow.of(KhSchema.from(List.of(columns)), newValues);
    }
}
