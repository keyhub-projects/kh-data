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

package keyhub.data.structure.stream.join;

import keyhub.data.structure.function.KhColumnSelector;
import keyhub.data.structure.join.KhJoin;
import keyhub.data.structure.stream.KhStream;

/**
 * Interface representing a join operation between two KhStream instances.
 */
public interface KhStreamJoin extends KhJoin<KhStream, KhStreamJoin> {

    /**
     * Specifies the key column for the join operation, assuming the same key column name in both streams.
     *
     * @param key the name of the key column in both streams
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin on(String key);

    /**
     * Specifies the key columns for the join operation, with different column names in the left and right streams.
     *
     * @param leftKey the name of the key column in the left stream
     * @param rightKey the name of the key column in the right stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin on(String leftKey, String rightKey);

    /**
     * Selects specific columns from the left stream for the join result using column selectors.
     *
     * @param selectors the column selectors for the left stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectFromLeft(KhColumnSelector... selectors);

    /**
     * Selects a specific column from the left stream for the join result.
     *
     * @param column the name of the column in the left stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectFromLeft(String column);

    /**
     * Selects specific columns from the left stream for the join result.
     *
     * @param columns the names of the columns in the left stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectFromLeft(String... columns);

    /**
     * Selects all columns from the left stream for the join result.
     *
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectAllFromLeft();

    /**
     * Selects specific columns from the right stream for the join result using column selectors.
     *
     * @param selectors the column selectors for the right stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectFromRight(KhColumnSelector... selectors);

    /**
     * Selects a specific column from the right stream for the join result.
     *
     * @param column the name of the column in the right stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectFromRight(String column);

    /**
     * Selects specific columns from the right stream for the join result.
     *
     * @param columns the names of the columns in the right stream
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectFromRight(String... columns);

    /**
     * Selects all columns from the right stream for the join result.
     *
     * @return the current KhStreamJoin instance
     */
    KhStreamJoin selectAllFromRight();
}