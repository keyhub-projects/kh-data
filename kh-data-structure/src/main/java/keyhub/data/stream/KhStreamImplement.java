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

package keyhub.data.stream;

import keyhub.data.column.KhColumn;
import keyhub.data.function.KhCellSelector;
import keyhub.data.function.KhRowPredicate;
import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import keyhub.data.table.KhTable;

import java.util.*;
import java.util.function.*;
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
    public KhSchema getSchema() {
        return this.SCHEMA;
    }

    @Override
    public Stream<KhRow> filter(Predicate<? super KhRow> predicate) {
        return this.ROW_STREAM.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super KhRow, ? extends R> mapper) {
        return this.ROW_STREAM.map(mapper);
    }

    @Override
    public Stream<KhRow> limit(long maxSize) {
        return this.ROW_STREAM.limit(maxSize);
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
    public Stream<KhRow> onClose(Runnable closeHandler) {
        return this.ROW_STREAM.onClose(closeHandler);
    }

    @Override
    public void close() {
        this.ROW_STREAM.close();
    }

    @Override
    public <R> R collect(Supplier<R> supplier,
                         BiConsumer<R, KhRow> accumulator,
                         BiConsumer<R, R> combiner){
        return ROW_STREAM.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<KhRow, A, R> collector){
        return ROW_STREAM.collect(collector);
    }


    /// Implement KhStream
    @Override
    public KhStream select(String... columns) {
        // todo
        return null;
    }

    @Override
    public KhStream select(KhColumn<?>... columns) {
        // todo
        return null;
    }

    @Override
    public KhStream select(KhCellSelector... selector) {
        // todo
        return null;
    }

    @Override
    public KhStream where(KhRowPredicate filter) {
        // todo
        return null;
    }

    @Override
    public KhStream leftJoin(KhTable tbl) {
        // todo
        return null;
    }

    @Override
    public KhStream rightJoin(KhTable tbl) {
        // todo
        return null;
    }

    @Override
    public List<KhRow> toList(){
        return ROW_STREAM.toList();
    }
}
