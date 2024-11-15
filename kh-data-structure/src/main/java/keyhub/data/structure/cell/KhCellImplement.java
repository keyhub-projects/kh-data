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

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class KhCellImplement<T> implements KhCell<T> {
    static<T> KhCell<T> empty(KhColumn<T> columnSchema) {
        return new KhCellValue<>(columnSchema);
    }
    static <T> KhCell<T> of(KhColumn<T> columnSchema, T value) {
        return new KhCellValue<>(columnSchema, value);
    }

    public abstract T value();
    public abstract Class<T> columnType();
    public abstract KhColumn<T> columnSchema();

    @Override
    public T getValue() {
        if (value() == null) {
            throw new NoSuchElementException("No value present");
        }
        return value();
    }
    @Override
    public KhColumn<T> getColumnSchema() {
        return columnSchema();
    }
    @Override
    public Class<T> getColumnType() {
        return columnType();
    }
    @Override
    public boolean isPresent() {
        return value() != null;
    }
    @Override
    public boolean isEmpty() {
        return value() == null;
    }
    @Override
    public void ifPresent(Consumer<? super T> action) {
        if (value() != null) {
            action.accept(value());
        }
    }
    @Override
    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value() != null) {
            action.accept(value());
        } else {
            emptyAction.run();
        }
    }
    @Override
    public KhCell<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (isEmpty()) {
            return this;
        } else {
            return predicate.test(value()) ? this : empty(columnSchema());
        }
    }
    @Override
    public <U> KhCell<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            @SuppressWarnings("unchecked")
            KhCell<U> empty = (KhCell<U>) empty(columnSchema());
            return empty;
        } else {
            @SuppressWarnings("unchecked")
            KhColumn<U> columnSchema = (KhColumn<U>) columnSchema();
            return KhCell.of(columnSchema, mapper.apply(value()));
        }
    }
    @Override
    public <U> KhCell<U> flatMap(Function<? super T, ? extends KhCell<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            @SuppressWarnings("unchecked")
            KhCell<U> empty = (KhCell<U>) empty(columnSchema());
            return empty;
        } else {
            @SuppressWarnings("unchecked")
            KhCell<U> r = (KhCell<U>) mapper.apply(value());
            return Objects.requireNonNull(r);
        }
    }
    @Override
    public KhCell<T> or(Supplier<? extends KhCell<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            KhCell<T> r = (KhCell<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }
    @Override
    public Stream<T> stream() {
        if (isEmpty()) {
            return Stream.empty();
        } else {
            return Stream.of(value());
        }
    }
    @Override
    public T orElse(T other) {
        return value() != null ? value() : other;
    }
    @Override
    public T orElseGet(Supplier<? extends T> supplier) {
        return value() != null ? value() : supplier.get();
    }
    @Override
    public T orElseThrow() {
        if (value() == null) {
            throw new NoSuchElementException("No value present");
        }
        return value();
    }
    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value() != null) {
            return value();
        } else {
            throw exceptionSupplier.get();
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KhCell<?> other)) {
            return false;
        }
        return value().equals(other.getValue())
                && columnType().equals(other.getColumnType());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(value());
    }
    @Override
    public String toString() {
        return value() != null && columnSchema() != null
                ? ("TblCell["+ columnSchema().getColumnName() + "(" + columnType() + ") = " + value() + "]")
                : "TblCell.empty";
    }
}
