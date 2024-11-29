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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Interface representing a cell in a data structure.
 *
 * @param <T> the type of the cell value
 */
public interface KhCell<T> {

    /**
     * Creates an empty cell with the specified column schema.
     *
     * @param columnSchema the column schema
     * @param <T> the type of the cell value
     * @return an empty cell
     */
    static<T> KhCell<T> empty(KhColumn<T> columnSchema) {
        return KhCellImplement.empty(columnSchema);
    }

    /**
     * Creates a cell with the specified column schema and value.
     *
     * @param columnSchema the column schema
     * @param value the cell value
     * @param <T> the type of the cell value
     * @return a cell with the specified value
     */
    static <T> KhCell<T> of(KhColumn<T> columnSchema, T value) {
        return KhCellImplement.of(columnSchema, value);
    }

    /**
     * Gets the value of the cell.
     *
     * @return the cell value
     */
    T getValue();

    /**
     * Gets the column schema of the cell.
     *
     * @return the column schema
     */
    KhColumn<T> getColumnSchema();

    /**
     * Gets the type of the cell value.
     *
     * @return the cell value type
     */
    Class<T> getColumnType();

    /**
     * Checks if the cell has a value.
     *
     * @return true if the cell has a value, otherwise false
     */
    boolean isPresent();

    /**
     * Checks if the cell is empty.
     *
     * @return true if the cell is empty, otherwise false
     */
    boolean isEmpty();

    /**
     * Performs the given action if the cell has a value.
     *
     * @param action the action to perform
     */
    void ifPresent(Consumer<? super T> action);

    /**
     * Performs the given action if the cell has a value, otherwise performs the empty action.
     *
     * @param action the action to perform if the cell has a value
     * @param emptyAction the action to perform if the cell is empty
     */
    void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction);

    /**
     * Filters the cell value using the given predicate.
     *
     * @param predicate the predicate to apply
     * @return a cell containing the value if it matches the predicate, otherwise an empty cell
     */
    KhCell<T> filter(Predicate<? super T> predicate);

    /**
     * Maps the cell value using the given function.
     *
     * @param mapper the function to apply
     * @param <U> the type of the result
     * @return a cell containing the result of applying the function
     */
    <U> KhCell<U> map(Function<? super T, ? extends U> mapper);

    /**
     * Flat maps the cell value using the given function.
     *
     * @param mapper the function to apply
     * @param <U> the type of the result
     * @return a cell containing the result of applying the function
     */
    <U> KhCell<U> flatMap(Function<? super T, ? extends KhCell<? extends U>> mapper);

    /**
     * Returns the cell if it has a value, otherwise returns the result of the supplier.
     *
     * @param supplier the supplier to use if the cell is empty
     * @return the cell or the result of the supplier
     */
    KhCell<T> or(Supplier<? extends KhCell<? extends T>> supplier);

    /**
     * Returns a stream containing the cell value if it is present, otherwise an empty stream.
     *
     * @return a stream containing the cell value or an empty stream
     */
    Stream<T> stream();

    /**
     * Returns the cell value if it is present, otherwise returns the specified value.
     *
     * @param other the value to return if the cell is empty
     * @return the cell value or the specified value
     */
    T orElse(T other);

    /**
     * Returns the cell value if it is present, otherwise returns the result of the supplier.
     *
     * @param supplier the supplier to use if the cell is empty
     * @return the cell value or the result of the supplier
     */
    T orElseGet(Supplier<? extends T> supplier);

    /**
     * Returns the cell value if it is present, otherwise throws NoSuchElementException.
     *
     * @return the cell value
     * @throws NoSuchElementException if the cell is empty
     */
    T orElseThrow();

    /**
     * Returns the cell value if it is present, otherwise throws an exception provided by the supplier.
     *
     * @param exceptionSupplier the supplier to use if the cell is empty
     * @param <X> the type of the exception
     * @return the cell value
     * @throws X if the cell is empty
     */
    <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;
}