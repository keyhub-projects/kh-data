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

package keyhub.data.structure.stream;

import keyhub.data.structure.cell.KhCell;
import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.function.KhCellSelector;
import keyhub.data.structure.function.KhRowPredicate;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.stream.join.KhStreamJoin;
import keyhub.data.structure.stream.join.inner.KhStreamInnerJoin;
import keyhub.data.structure.stream.join.left.KhStreamLeftJoin;
import keyhub.data.structure.table.KhTable;

import java.util.*;
import java.util.stream.*;

public class KhStreamImplement implements KhStream {
    private final Stream<KhRow> ROW_STREAM;
    private final KhSchema SCHEMA;

    private KhStreamImplement(KhSchema schema, Stream<KhRow> rowStream) {
        this.SCHEMA = schema;
        this.ROW_STREAM = rowStream;
    }
    static KhStream from(KhTable tbl){
        Stream<KhRow> rowStream = tbl.stream();
        return KhStream.of(tbl.getSchema(), rowStream);
    }
    static KhStream of(KhSchema schema, Stream<KhRow> rowStream){
        return new KhStreamImplement(schema, rowStream);
    }

    @Override
    public KhColumn<?> getColumnSchema(int index) {
        return this.SCHEMA.getColumnSchema(index);
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
    public Stream<KhRow> getRowStream() {
        return this.ROW_STREAM;
    }

    @Override
    public Iterator<KhRow> iterator() {
        return this.ROW_STREAM.iterator();
    }
    @Override
    public Spliterator<KhRow> spliterator() {
        return this.ROW_STREAM.spliterator();
    }
    @Override
    public boolean isParallel() {
        return this.ROW_STREAM.isParallel();
    }
    @Override
    public KhStream sequential() {
        return KhStream.of(this.SCHEMA, this.ROW_STREAM.sequential());
    }
    @Override
    public KhStream parallel() {
        return KhStream.of(this.SCHEMA, this.ROW_STREAM.parallel());
    }
    @Override
    public KhStream unordered() {
        return KhStream.of(this.SCHEMA, this.ROW_STREAM.unordered());
    }
    @Override
    public KhStream onClose( Runnable closeHandler) {
        return KhStream.of(this.SCHEMA, this.ROW_STREAM.onClose(closeHandler));
    }
    @Override
    public void close() {
        this.ROW_STREAM.close();
    }

    @Override
    public KhStream select(String... columns) {
        KhSchema schema = this.SCHEMA.select(columns);
        Stream<KhRow> stream = this.ROW_STREAM.map(row -> row.select(columns));
        return KhStream.of(schema, stream);
    }
    @Override
    public KhStream select(KhColumn<?>... columns) {
        KhSchema schema = KhSchema.from(List.of(columns));
        Stream<KhRow> stream = this.ROW_STREAM.map(row -> row.select(columns));
        return KhStream.of(schema, stream);
    }
    @Override
    public KhStream select(KhCellSelector... selector) {
        Stream<KhRow> rowStream = ROW_STREAM.map(row -> {
            List<KhCell> cells = Stream.of(selector)
                    .map(s -> s.apply(row))
                    .collect(Collectors.toList());
            return KhRow.of(cells);
        });
        return KhStream.of(this.SCHEMA, rowStream);
    }

    @Override
    public KhStream where(KhRowPredicate filter) {
        Stream<KhRow> stream = this.ROW_STREAM.filter(filter);
        return KhStream.of(this.SCHEMA, stream);
    }

    @Override
    public KhStreamJoin leftJoin(KhTable tbl) {
        return KhStreamLeftJoin.of(this, tbl);
    }
    @Override
    public KhStreamJoin innerJoin(KhTable tbl) {
        return KhStreamInnerJoin.of(this, tbl);
    }

    @Override
    public List<KhRow> toList(){
        try{
            return this.ROW_STREAM.toList();
        }finally {
            close();
        }
    }
    @Override
    public KhTable toTable() {
        try{
            return KhTable.from(this);
        }finally {
            close();
        }
    }
}
