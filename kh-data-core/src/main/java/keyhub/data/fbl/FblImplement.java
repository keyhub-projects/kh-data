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

package keyhub.data.fbl;

import keyhub.data.row.Row;
import keyhub.data.schema.Schema;
import keyhub.data.tbl.Tbl;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FblImplement implements Fbl{
    private final Stream<Row> ROW_STREAM;
    private final Schema SCHEMA;

    private FblImplement(Schema schema, Stream<Row> rowStream) {
        this.SCHEMA = schema;
        this.ROW_STREAM = rowStream;
    }
    static Fbl from(Tbl tbl){
        Stream<Row> rowStream = tbl.stream();
        return Fbl.of(tbl.getSchema(), rowStream);
    }
    static Fbl of(Schema schema, Stream<Row> rowStream){
        return new FblImplement(schema, rowStream);
    }

    @Override
    public Schema getSchema() {
        return this.SCHEMA;
    }

    @Override
    public Stream<Row> filter(Predicate<? super Row> predicate) {
        return this.ROW_STREAM.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super Row, ? extends R> mapper) {
        return this.ROW_STREAM.map(mapper);
    }

    @Override
    public Stream<Row> limit(long maxSize) {
        return this.ROW_STREAM.limit(maxSize);
    }

    @Override
    public Iterator<Row> iterator() {
        return this.ROW_STREAM.iterator();
    }

    @Override
    public Spliterator<Row> spliterator() {
        return this.ROW_STREAM.spliterator();
    }

    @Override
    public Stream<Row> onClose(Runnable closeHandler) {
        return this.ROW_STREAM.onClose(closeHandler);
    }

    @Override
    public void close() {
        this.ROW_STREAM.close();
    }

    @Override
    public <R> R collect(Supplier<R> supplier,
                         BiConsumer<R, Row> accumulator,
                         BiConsumer<R, R> combiner){
        return ROW_STREAM.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<Row, A, R> collector){
        return ROW_STREAM.collect(collector);
    }

    @Override
    public List<Row> toList(){
        return ROW_STREAM.toList();
    }
}
