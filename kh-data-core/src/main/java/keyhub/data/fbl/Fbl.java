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

import keyhub.data.column.Column;
import keyhub.data.function.CellSelector;
import keyhub.data.function.RowPredicate;
import keyhub.data.row.Row;
import keyhub.data.schema.Schema;
import keyhub.data.tbl.Tbl;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface Fbl {
    static Fbl from(Tbl tbl){
        return FblImplement.from(tbl);
    }
    static Fbl of(Schema schema, Stream<Row> rowStream) {
        return FblImplement.of(schema, rowStream);
    }
    Schema getSchema();

    Stream<Row> filter(Predicate<? super Row> predicate);

    <R> Stream<R> map(Function<? super Row, ? extends R> mapper);

    Stream<Row> limit(long maxSize);

    Iterator<Row> iterator();

    Spliterator<Row> spliterator();

    Stream<Row> onClose(Runnable closeHandler);

    void close();

    <R> R collect(Supplier<R> supplier, BiConsumer<R, Row> accumulator, BiConsumer<R, R> combiner);

    <R, A> R collect(Collector<Row, A, R> collector);

    Fbl select(String... columns);
    Fbl select(Column<?>... columns);
    Fbl select(CellSelector... selector);
    Fbl where(RowPredicate filter);
    Fbl leftJoin(Tbl tbl);
    Fbl rightJoin(Tbl tbl);

    List<Row> toList();
}
