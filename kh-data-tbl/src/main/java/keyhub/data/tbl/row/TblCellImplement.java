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

package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblColumnSchema;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class TblCellImplement<T> implements TblCell<T>{
    static<T> TblCell<T> empty(TblColumnSchema<T> columnSchema) {
        return new TblCellValue<>(columnSchema);
    }
    static <T> TblCell<T> of(TblColumnSchema<T> columnSchema, T value) {
        return new TblCellValue<>(columnSchema, value);
    }

    public abstract T value();
    public abstract Class<T> columnType();
    public abstract TblColumnSchema<T> columnSchema();

    @Override
    public T getValue() {
        if (value() == null) {
            throw new NoSuchElementException("No value present");
        }
        return value();
    }
    @Override
    public TblColumnSchema<T> getColumnSchema() {
        return columnSchema();
    }
    @Override
    public Class<T> getColumnType() {
        return columnType();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TblCell<?> other)) {
            return false;
        }
        return value().equals(other.getValue())
                && columnSchema().equals(other.getColumnSchema());
    }

    public boolean isPresent() {
        return value() != null;
    }
    public boolean isEmpty() {
        return value() == null;
    }
    public void ifPresent(Consumer<? super T> action) {
        if (value() != null) {
            action.accept(value());
        }
    }
    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value() != null) {
            action.accept(value());
        } else {
            emptyAction.run();
        }
    }
    public TblCell<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (isEmpty()) {
            return this;
        } else {
            return predicate.test(value()) ? this : empty(columnSchema());
        }
    }
    public <U> TblCell<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            @SuppressWarnings("unchecked")
            TblCell<U> empty = (TblCell<U>) empty(columnSchema());
            return empty;
        } else {
            @SuppressWarnings("unchecked")
            TblColumnSchema<U> columnSchema = (TblColumnSchema<U>) columnSchema();
            return TblCell.of(columnSchema, mapper.apply(value()));
        }
    }
    public <U> TblCell<U> flatMap(Function<? super T, ? extends TblCell<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            @SuppressWarnings("unchecked")
            TblCell<U> empty = (TblCell<U>) empty(columnSchema());
            return empty;
        } else {
            @SuppressWarnings("unchecked")
            TblCell<U> r = (TblCell<U>) mapper.apply(value());
            return Objects.requireNonNull(r);
        }
    }
    public TblCell<T> or(Supplier<? extends TblCell<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            TblCell<T> r = (TblCell<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }
    public Stream<T> stream() {
        if (isEmpty()) {
            return Stream.empty();
        } else {
            return Stream.of(value());
        }
    }
    public T orElse(T other) {
        return value() != null ? value() : other;
    }
    public T orElseGet(Supplier<? extends T> supplier) {
        return value() != null ? value() : supplier.get();
    }
    public T orElseThrow() {
        if (value() == null) {
            throw new NoSuchElementException("No value present");
        }
        return value();
    }
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value() != null) {
            return value();
        } else {
            throw exceptionSupplier.get();
        }
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
