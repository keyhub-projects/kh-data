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

package keyhub.data.cell;

import keyhub.data.column.Column;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Cell<T> {

    static<T> Cell<T> empty(Column<T> columnSchema) {
        return CellImplement.empty(columnSchema);
    }
    static <T> Cell<T> of(Column<T> columnSchema, T value) {
        return CellImplement.of(columnSchema, value);
    }

    T getValue();
    Column<T> getColumnSchema();
    Class<T> getColumnType();
    boolean isPresent();
    boolean isEmpty();
    void ifPresent(Consumer<? super T> action);
    void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction);
    Cell<T> filter(Predicate<? super T> predicate);
    <U> Cell<U> map(Function<? super T, ? extends U> mapper);
    <U> Cell<U> flatMap(Function<? super T, ? extends Cell<? extends U>> mapper);
    Cell<T> or(Supplier<? extends Cell<? extends T>> supplier);
    Stream<T> stream();
    T orElse(T other);
    T orElseGet(Supplier<? extends T> supplier);
    T orElseThrow();
    <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;
}
