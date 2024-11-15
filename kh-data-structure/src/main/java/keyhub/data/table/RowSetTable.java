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

import keyhub.data.cell.KhCell;
import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import keyhub.data.column.KhColumn;
import keyhub.data.stream.KhStream;

import java.util.*;

public class RowSetTable extends KhTableImplement {
    private final List<List<Object>> data;

    public RowSetTable(KhSchema schema, List<List<Object>> data) {
        super(schema);
        this.data = data;
    }

    @Override
    public int count() {
        return data.size();
    }

    @Override
    public KhRow getRow(int index) {
        if(index < 0 || index >= this.data.size()){
            throw new IllegalArgumentException("KhRow index out of bounds");
        }
        return KhRow.of(this.SCHEMA, data.get(index));
    }

    @Override
    public Optional<KhCell<?>> findCell(String columnName, int rowIndex){
        int columnIndex = this.SCHEMA.getColumnIndex(columnName);
        if(columnIndex == -1){
            return Optional.empty();
        }
        return Optional.ofNullable(getCell(columnIndex, rowIndex));
    }

    @Override
    public KhCell<?> getCell(int columnIndex, int rowIndex){
        if(columnIndex < 0 || columnIndex >= this.SCHEMA.getColumnSize()){
            throw new IllegalArgumentException("KhColumn index out of bounds");
        }
        if(rowIndex < 0 || rowIndex >= this.data.size()){
            throw new IllegalArgumentException("KhRow index out of bounds");
        }
        var value = this.data.get(rowIndex).get(columnIndex);
        return KhCell.of(this.SCHEMA.getColumnSchema(columnIndex), value);
    }

    @Override
    public List<KhRow> getRows() {
        return data.stream()
                .map(row -> KhRow.of(this.SCHEMA, row))
                .toList();
    }

    @Override
    public List<Object> getRawRow(int index) {
        if(index < 0 || index >= this.data.size()){
            throw new IllegalArgumentException("KhRow index out of bounds");
        }
        return this.data.get(index);
    }

    @Override
    public List<Map<String, Object>> toRowMapList() {
        return this.data.stream()
                .map(row -> {
                    Map<String, Object> rowMap = new HashMap<>();
                    for(int i = 0; i < this.SCHEMA.getColumnSize(); i++){
                        KhColumn<?> columnSchema = this.SCHEMA.getColumnSchema(i);
                        rowMap.put(columnSchema.getColumnName(), row.get(i));
                    }
                    return rowMap;
                })
                .toList();
    }

    @Override
    public Map<String, List<Object>> toColumnListMap() {
        Map<String, List<Object>> columnListMap = new HashMap<>();
        for(int i = 0; i < this.SCHEMA.getColumnSize(); i++){
            KhColumn<?> columnSchema = this.SCHEMA.getColumnSchema(i);
            List<Object> columnList = new ArrayList<>();
            for(List<Object> row : this.data){
                columnList.add(row.get(i));
            }
            columnListMap.put(columnSchema.getColumnName(), columnList);
        }
        return columnListMap;
    }
}
