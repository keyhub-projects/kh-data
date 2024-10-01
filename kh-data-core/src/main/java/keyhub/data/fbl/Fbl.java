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

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
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

    Row reduce(Row identity, BinaryOperator<Row> accumulator);

    Optional<Row> reduce(BinaryOperator<Row> accumulator);

    Iterator<Row> iterator();

    Spliterator<Row> spliterator();

    Stream<Row> onClose(Runnable closeHandler);

    void close();
}
