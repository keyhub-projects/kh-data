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

import keyhub.data.converter.ObjectConverter;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumn;
import keyhub.data.tbl.schema.TblSchema;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class TblImplement implements Tbl {
    protected final TblSchema schema;

    protected TblImplement(TblSchema schema) {
        this.schema = schema;
    }


    public static Tbl empty() {
        return TblBuilder.forRowSet(TblSchema.empty()).build();
    }

    public static Tbl empty(TblSchema schema) {
        return TblBuilder.forRowSet(schema).build();
    }

    public static Tbl from(List<?> objectList) {
        if(objectList.isEmpty()) {
            return empty();
        }
        if(objectList.getFirst() instanceof Map) {
            return fromRowMapList((List<Map<String, Object>>) objectList);
        }
        return fromObjects(objectList);
    }

    public static Tbl fromObjects(List<?> objectList) {
        List<Map<String, Object>> mapList = ObjectConverter.convertToMapList(objectList);
        return fromRowMapList(mapList);
    }

    public static Tbl fromRowMapList(List<Map<String, Object>> rowMapList) {
        List<TblColumn> keyMap = rowMapList.getFirst().entrySet().stream()
                .map(entry -> (TblColumn)(TblColumn.of(entry.getKey(), entry.getValue().getClass())))
                .toList();
        TblSchema schema = TblSchema.from(keyMap);
        TblBuilder builder = TblBuilder.forRowSet(schema);
        for(Map<String, Object> rowMap : rowMapList) {
            List<Object> row = keyMap.stream()
                    .map(columnSchema -> rowMap.getOrDefault(columnSchema.getColumnName(), null))
                    .toList();
            builder.addRawRow(row);
        }
        return builder.build();
    }

    public static Tbl fromColumnListMap(Map<String, List<Object>> columnListMap){
        List<TblColumn> keyMap = columnListMap.entrySet().stream()
                .map(entry -> (TblColumn)(TblColumn.of(entry.getKey(), entry.getValue().getFirst().getClass())))
                .toList();
        TblSchema schema = TblSchema.from(keyMap);
        TblBuilder builder = TblBuilder.forRowSet(schema);
        for(int i = 0; i < columnListMap.get(keyMap.getFirst().getColumnName()).size(); i++) {
            int finalI = i;
            List<Object> row = keyMap.stream()
                    .map(columnSchema -> columnListMap.get(columnSchema.getColumnName()).get(finalI))
                    .toList();
            builder.addRawRow(row);
        }
        return builder.build();
    }

    public static Tbl of(TblSchema schema, List<List<Object>> data) {
        TblBuilder builder = TblBuilder.forRowSet(schema);
        return builder
                .addRawRows(data)
                .build();
    }

    @Override
    public TblColumn<?> getColumnSchema(int index) {
        return schema.getColumnSchema(index);
    }

    @Override
    public TblSchema getSchema() {
        return this.schema;
    }

    @Override
    public int getColumnSize() {
        return schema.getColumnSize();
    }

    @Override
    public String getColumn(int index) {
        return schema.getColumnNames().get(index);
    }

    @Override
    public List<String> getColumns() {
        return schema.getColumnNames();
    }

    @Override
    public Class<?> getColumnType(int index) {
        return schema.getColumnTypes().get(schema.getColumnNames().get(index));
    }

    @Override
    public Map<String, Class<?>> getColumnTypes() {
        return schema.getColumnTypes();
    }

    @Override
    public int getColumnIndex(String column) {
        return schema.getColumnIndex(column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tbl other)) {
            return false;
        }
        return this.getSchema().equals(other.getSchema())
                && this.getRows().equals(other.getRows());
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.getSchema(), this.getRows());
    }
    @Override
    public String toString() {
        return "[" + this.schema + ", " + this.getRows().toString() + "]";
    }

    @Override
    public Iterator<TblRow> iterator() {
        return this.getRows().iterator();
    }
}
