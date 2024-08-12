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

import keyhub.data.tbl.stream.TblStream;
import keyhub.data.tbl.stream.join.TblJoin;
import keyhub.data.tbl.stream.join.inner.TblInnerJoin;
import keyhub.data.tbl.stream.join.left.TblLeftJoin;
import keyhub.data.tbl.stream.filter.TblFilter;
import keyhub.data.tbl.stream.filter.TblFilterType;
import keyhub.data.tbl.schema.TblSchema;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.stream.selector.TblSelector;

import java.util.*;

public class RowSetTbl extends TblImplement {
    private final List<List<Object>> data;

    public RowSetTbl(TblSchema schema, List<List<Object>> data) {
        super(schema);
        this.data = data;
    }

    @Override
    public int count() {
        return data.size();
    }

    @Override
    public TblRow getRow(int index) {
        if(index < 0 || index >= this.data.size()){
            throw new IllegalArgumentException("Row index out of bounds");
        }
        return TblRow.of(super.schema, data.get(index));
    }

    @Override
    public Optional<Object> findCell(String columnName, int rowIndex){
        int columnIndex = this.schema.getColumnIndex(columnName);
        if(columnIndex == -1){
            return Optional.empty();
        }
        return Optional.ofNullable(getCell(columnIndex, rowIndex));
    }

    @Override
    public Object getCell(int columnIndex, int rowIndex){
        if(columnIndex < 0 || columnIndex >= this.schema.getColumnSize()){
            throw new IllegalArgumentException("Column index out of bounds");
        }
        if(rowIndex < 0 || rowIndex >= this.data.size()){
            throw new IllegalArgumentException("Row index out of bounds");
        }
        return data.get(rowIndex).get(columnIndex);
    }


    @Override
    public List<TblRow> getRows() {
        return data.stream()
                .map(row -> TblRow.of(super.schema, row))
                .toList();
    }

    @Override
    public List<Object> getRawRow(int index) {
        if(index < 0 || index >= this.data.size()){
            throw new IllegalArgumentException("Row index out of bounds");
        }
        return this.data.get(index);
    }

    @Override
    public List<List<Object>> getRawRows() {
        return this.data;
    }

    @Override
    public TblStream stream() {
        // todo
        return null;
    }

    @Override
    public Tbl select(String... columns) {
        return select(List.of(columns));
    }

    @Override
    public Tbl select(List<String> columns) {
        List<TblColumnSchema> columnSchemas = new ArrayList<>();
        for (String column : columns){
            Optional<TblColumnSchema> schema = this.schema.findColumnSchema(column);
            if(schema.isEmpty()){
                throw new IllegalArgumentException("Column not found in schema");
            }
            columnSchemas.add(schema.get());
        }
        List<List<Object>> newData = new ArrayList<>();
        for(List<Object> row : this.data){
            List<Object> newRow = new ArrayList<>();
            for(TblColumnSchema<?> columnSchema : columnSchemas){
                int index = this.schema.getColumnIndex(columnSchema.getColumnName());
                newRow.add(row.get(index));
            }
            newData.add(newRow);
        }
        return new RowSetTbl(TblSchema.from(columnSchemas), newData);
    }

    @Override
    public Tbl where(String column, TblFilterType operator, Object value) {
        return TblFilter.builder()
                .operator(operator)
                .tbl(this)
                .column(column)
                .value(value)
                .build();
    }
    @Override
    public Tbl where(String column, TblFilterType operator) {
        return TblFilter.builder()
                .operator(operator)
                .tbl(this)
                .column(column)
                .build();
    }

    @Override
    public TblSelector selector() {
        return TblSelector.from(this);
    }

    @Override
    public TblJoin leftJoin(Tbl right) {
        return TblLeftJoin.of(this, right);
    }

    @Override
    public TblJoin innerJoin(Tbl right) {
        return TblInnerJoin.of(this, right);
    }

    @Override
    public List<Map<String, Object>> toRowMapList() {
        return this.data.stream()
                .map(row -> {
                    Map<String, Object> rowMap = new HashMap<>();
                    for(int i = 0; i < this.schema.getColumnSize(); i++){
                        TblColumnSchema<?> columnSchema = this.schema.getColumnSchema(i);
                        rowMap.put(columnSchema.getColumnName(), row.get(i));
                    }
                    return rowMap;
                })
                .toList();
    }

    @Override
    public Map<String, List<Object>> toColumnListMap() {
        Map<String, List<Object>> columnListMap = new HashMap<>();
        for(int i = 0; i < this.schema.getColumnSize(); i++){
            TblColumnSchema<?> columnSchema = this.schema.getColumnSchema(i);
            List<Object> columnList = new ArrayList<>();
            for(List<Object> row : this.data){
                columnList.add(row.get(i));
            }
            columnListMap.put(columnSchema.getColumnName(), columnList);
        }
        return columnListMap;
    }
}
