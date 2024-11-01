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

import keyhub.data.converter.KhObjectConverter;
import keyhub.data.fbl.Fbl;
import keyhub.data.row.Row;
import keyhub.data.column.Column;
import keyhub.data.schema.Schema;
import keyhub.data.tbl.join.TblJoinFactory;
import keyhub.data.tbl.join.inner.TblInnerJoinFactory;
import keyhub.data.tbl.join.left.TblLeftJoinFactory;
import keyhub.data.tbl.query.TblQueryFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class TblImplement implements Tbl {
    protected final Schema SCHEMA;

    protected TblImplement(Schema schema) {
        this.SCHEMA = schema;
    }

    public static Tbl empty() {
        return TblBuilder.forRowSet(Schema.empty()).build();
    }

    public static Tbl empty(Schema schema) {
        return TblBuilder.forRowSet(schema).build();
    }

    public static Tbl from(List<?> objectList) {
        if(objectList == null){
            return empty();
        }
        if(objectList.isEmpty()) {
            return empty();
        }
        if(objectList.getFirst() instanceof Row row){
            List<Row> list = (List<Row>) objectList;
            return Tbl.builder(row.getSchema())
                    .addRows(list)
                    .build();
        }
        if(objectList.getFirst() instanceof Map) {
            return fromRowMapList((List<Map<String, Object>>) objectList);
        }
        return fromObjects(objectList);
    }

    public static Tbl from(Fbl fbl){
        List<Row> rows =  fbl.toList();
        return Tbl.from(rows);
    }

    public static Tbl of(Schema schema, List<List<Object>> rawRows) {
        TblBuilder builder = TblBuilder.forRowSet(schema);
        return builder
                .addRawRows(rawRows)
                .build();
    }

    private static Tbl fromObjects(List<?> objectList) {
        List<Map<String, Object>> mapList = KhObjectConverter.convertToMapList(objectList);
        return fromRowMapList(mapList);
    }

    private static Tbl fromRowMapList(List<Map<String, Object>> rowMapList) {
        List<Column> keyMap = rowMapList.getFirst().entrySet().stream()
                .map(entry -> (Column)(Column.of(entry.getKey(), entry.getValue().getClass())))
                .toList();
        Schema schema = Schema.from(keyMap);
        TblBuilder builder = TblBuilder.forRowSet(schema);
        for(Map<String, Object> rowMap : rowMapList) {
            List<Object> row = keyMap.stream()
                    .map(columnSchema -> rowMap.getOrDefault(columnSchema.getColumnName(), null))
                    .toList();
            builder.addRawRow(row);
        }
        return builder.build();
    }

    @Override
    public Column<?> getColumnSchema(int index) {
        return SCHEMA.getColumnSchema(index);
    }

    @Override
    public Schema getSchema() {
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
        return "[" + this.SCHEMA + ", " + this.getRows().toString() + "]";
    }

    @Override
    public Iterator<Row> iterator() {
        return this.getRows().iterator();
    }

    @Override
    public Stream<Row> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
    @Override
    public TblQueryFactory query(){
        Stream<Row> rowStream = StreamSupport.stream(spliterator(), false);
        return TblQueryFactory.from(rowStream);
    }
    @Override
    public TblJoinFactory leftJoin(Tbl right) {
        return TblLeftJoinFactory.of(this, right);
    }
    @Override
    public TblJoinFactory innerJoin(Tbl right) {
        return TblInnerJoinFactory.of(this, right);
    }

}
