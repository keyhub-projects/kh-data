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

package keyhub.data.structure.column;

/**
 * Interface representing a column in a data structure.
 *
 * @param <T> the type of the column
 */
public interface KhColumn<T> {

    /**
     * Creates a new column with the specified name and type.
     *
     * @param columnName the name of the column
     * @param columnType the type of the column
     * @param <T> the type of the column
     * @return a new column
     */
    static <T> KhColumn<T> of(String columnName, Class<T> columnType) {
        return KhColumnImplement.of(columnName, columnType);
    }

    /**
     * Gets the name of the column.
     *
     * @return the column name
     */
    String getColumnName();

    /**
     * Gets the type of the column.
     *
     * @return the column type
     */
    Class<T> getColumnType();

    /**
     * Checks if this column is equal to another object.
     *
     * @param o the object to compare with
     * @return true if this column is equal to the other object, otherwise false
     */
    @Override
    boolean equals(Object o);
}