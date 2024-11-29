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

package keyhub.data.structure.join;

import keyhub.data.structure.function.KhColumnSelector;

/**
 * Interface representing a join operation between two data structures.
 *
 * @param <E> the type of the joinable entity
 * @param <T> the type of the join operation
 */
public interface KhJoin<E extends KhJoinable, T extends KhJoin<E, T>> {

    /**
     * Executes the join operation and returns the result as a single entity.
     *
     * @return the result of the join operation
     */
    E toOne();

    /**
     * Specifies the key to join on.
     *
     * @param key the key to join on
     * @return the join operation
     */
    KhJoin<E, T> on(String key);

    /**
     * Specifies the keys to join on.
     *
     * @param leftKey the key from the left data structure
     * @param rightKey the key from the right data structure
     * @return the join operation
     */
    KhJoin<E, T> on(String leftKey, String rightKey);

    /**
     * Selects all columns from both data structures.
     *
     * @return the join operation
     */
    KhJoin<E, T> selectAll();

    /**
     * Selects specific columns from the left data structure.
     *
     * @param selectors the column selectors
     * @return the join operation
     */
    KhJoin<E, T> selectFromLeft(KhColumnSelector... selectors);

    /**
     * Selects a specific column from the left data structure.
     *
     * @param column the column name
     * @return the join operation
     */
    KhJoin<E, T> selectFromLeft(String column);

    /**
     * Selects specific columns from the left data structure.
     *
     * @param columns the column names
     * @return the join operation
     */
    KhJoin<E, T> selectFromLeft(String... columns);

    /**
     * Selects all columns from the left data structure.
     *
     * @return the join operation
     */
    KhJoin<E, T> selectAllFromLeft();

    /**
     * Selects specific columns from the right data structure.
     *
     * @param selectors the column selectors
     * @return the join operation
     */
    KhJoin<E, T> selectFromRight(KhColumnSelector... selectors);

    /**
     * Selects a specific column from the right data structure.
     *
     * @param column the column name
     * @return the join operation
     */
    KhJoin<E, T> selectFromRight(String column);

    /**
     * Selects specific columns from the right data structure.
     *
     * @param columns the column names
     * @return the join operation
     */
    KhJoin<E, T> selectFromRight(String... columns);

    /**
     * Selects all columns from the right data structure.
     *
     * @return the join operation
     */
    KhJoin<E, T> selectAllFromRight();

    /**
     * Gets the index of a column from the left data structure.
     *
     * @param column the column name
     * @return the column index
     */
    int getColumnIndexFromLeft(String column);

    /**
     * Gets the index of a column from the right data structure.
     *
     * @param column the column name
     * @return the column index
     */
    int getColumnIndexFromRight(String column);
}