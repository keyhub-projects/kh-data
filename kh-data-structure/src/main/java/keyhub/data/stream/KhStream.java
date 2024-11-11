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

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface KhStream {
    static KhStream from(KhTable tbl){
        return KhStreamImplement.from(tbl);
    }
    static KhStream of(KhSchema schema, Stream<KhRow> rowStream) {
        return KhStreamImplement.of(schema, rowStream);
    }
    KhSchema getSchema();

    Stream<KhRow> filter(Predicate<? super KhRow> predicate);

    <R> Stream<R> map(Function<? super KhRow, ? extends R> mapper);

    Stream<KhRow> limit(long maxSize);

    Iterator<KhRow> iterator();

    Spliterator<KhRow> spliterator();

    Stream<KhRow> onClose(Runnable closeHandler);

    void close();

    <R> R collect(Supplier<R> supplier, BiConsumer<R, KhRow> accumulator, BiConsumer<R, R> combiner);

    <R, A> R collect(Collector<KhRow, A, R> collector);

    KhStream select(String... columns);
    KhStream select(KhColumn<?>... columns);
    KhStream select(KhCellSelector... selector);
    KhStream where(KhRowPredicate filter);
    KhStream leftJoin(KhTable tbl);
    KhStream rightJoin(KhTable tbl);

    List<KhRow> toList();
}
