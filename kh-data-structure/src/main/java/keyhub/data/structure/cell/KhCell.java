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

package keyhub.data.structure.cell;

import keyhub.data.structure.column.KhColumn;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface KhCell<T> {

    static<T> KhCell<T> empty(KhColumn<T> columnSchema) {
        return KhCellImplement.empty(columnSchema);
    }
    static <T> KhCell<T> of(KhColumn<T> columnSchema, T value) {
        return KhCellImplement.of(columnSchema, value);
    }

    T getValue();
    KhColumn<T> getColumnSchema();
    Class<T> getColumnType();
    boolean isPresent();
    boolean isEmpty();
    void ifPresent(Consumer<? super T> action);
    void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction);
    KhCell<T> filter(Predicate<? super T> predicate);
    <U> KhCell<U> map(Function<? super T, ? extends U> mapper);
    <U> KhCell<U> flatMap(Function<? super T, ? extends KhCell<? extends U>> mapper);
    KhCell<T> or(Supplier<? extends KhCell<? extends T>> supplier);
    Stream<T> stream();
    T orElse(T other);
    T orElseGet(Supplier<? extends T> supplier);
    T orElseThrow();
    <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;
}
