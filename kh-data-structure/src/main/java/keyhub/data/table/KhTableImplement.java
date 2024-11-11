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

import keyhub.data.column.KhColumn;
import keyhub.data.converter.KhObjectConverter;
import keyhub.data.stream.KhStream;
import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import keyhub.data.table.join.KhTableJoinFactory;
import keyhub.data.table.join.inner.KhTableInnerJoinFactory;
import keyhub.data.table.join.left.KhTableLeftJoinFactory;
import keyhub.data.table.query.KhTableQueryFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class KhTableImplement implements KhTable {
    protected final KhSchema SCHEMA;

    protected KhTableImplement(KhSchema schema) {
        this.SCHEMA = schema;
    }

    public static KhTable empty() {
        return KhTableBuilder.forRowSet(KhSchema.empty()).build();
    }

    public static KhTable empty(KhSchema schema) {
        return KhTableBuilder.forRowSet(schema).build();
    }

    public static KhTable from(List<?> objectList) {
        if(objectList == null){
            return empty();
        }
        if(objectList.isEmpty()) {
            return empty();
        }
        if(objectList.getFirst() instanceof KhRow row){
            List<KhRow> list = (List<KhRow>) objectList;
            return KhTable.builder(row.getSchema())
                    .addRows(list)
                    .build();
        }
        if(objectList.getFirst() instanceof Map) {
            return fromRowMapList((List<Map<String, Object>>) objectList);
        }
        return fromObjects(objectList);
    }

    public static KhTable from(KhStream fbl){
        List<KhRow> rows =  fbl.toList();
        return KhTable.from(rows);
    }

    public static KhTable of(KhSchema schema, List<List<Object>> rawRows) {
        KhTableBuilder builder = KhTableBuilder.forRowSet(schema);
        return builder
                .addRawRows(rawRows)
                .build();
    }

    private static KhTable fromObjects(List<?> objectList) {
        List<Map<String, Object>> mapList = KhObjectConverter.convertToMapList(objectList);
        return fromRowMapList(mapList);
    }

    private static KhTable fromRowMapList(List<Map<String, Object>> rowMapList) {
        List<KhColumn> keyMap = rowMapList.getFirst().entrySet().stream()
                .map(entry -> (KhColumn)(KhColumn.of(entry.getKey(), entry.getValue().getClass())))
                .toList();
        KhSchema schema = KhSchema.from(keyMap);
        KhTableBuilder builder = KhTableBuilder.forRowSet(schema);
        for(Map<String, Object> rowMap : rowMapList) {
            List<Object> row = keyMap.stream()
                    .map(columnSchema -> rowMap.getOrDefault(columnSchema.getColumnName(), null))
                    .toList();
            builder.addRawRow(row);
        }
        return builder.build();
    }

    @Override
    public KhColumn<?> getColumnSchema(int index) {
        return SCHEMA.getColumnSchema(index);
    }

    @Override
    public KhSchema getSchema() {
        return this.SCHEMA;
    }

    @Override
    public int getColumnSize() {
        return this.SCHEMA.getColumnSize();
    }

    @Override
    public String getColumnName(int index) {
        return SCHEMA.getColumnNames().get(index);
    }

    @Override
    public Class<?> getColumnType(int index) {
        return this.SCHEMA.getColumnTypes().get(this.SCHEMA.getColumnNames().get(index));
    }

    @Override
    public int getColumnIndex(String column) {
        return this.SCHEMA.getColumnIndex(column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KhTable other)) {
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
        return "[" + this.SCHEMA + ", " + this.getRows().toString() + "]";
    }

    @Override
    public Iterator<KhRow> iterator() {
        return this.getRows().iterator();
    }

    @Override
    public Stream<KhRow> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
    @Override
    public KhTableQueryFactory query(){
        Stream<KhRow> rowStream = StreamSupport.stream(spliterator(), false);
        return KhTableQueryFactory.from(rowStream);
    }
    @Override
    public KhTableJoinFactory leftJoin(KhTable right) {
        return KhTableLeftJoinFactory.of(this, right);
    }
    @Override
    public KhTableJoinFactory innerJoin(KhTable right) {
        return KhTableInnerJoinFactory.of(this, right);
    }

}
