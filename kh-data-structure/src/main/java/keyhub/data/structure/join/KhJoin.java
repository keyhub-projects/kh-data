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

public interface KhJoin<E extends KhJoinable, T extends KhJoin<E, T>> {
    E toOne();

    KhJoin<E, T> on(String key);
    KhJoin<E, T> on(String leftKey, String rightKey);

    KhJoin<E, T> selectAll();

    KhJoin<E, T> selectFromLeft(KhColumnSelector... selectors);
    KhJoin<E, T> selectFromLeft(String column);
    KhJoin<E, T> selectFromLeft(String... columns);
    KhJoin<E, T> selectAllFromLeft();

    KhJoin<E, T> selectFromRight(KhColumnSelector... selectors);
    KhJoin<E, T> selectFromRight(String column);
    KhJoin<E, T> selectFromRight(String... columns);
    KhJoin<E, T> selectAllFromRight();

    int getColumnIndexFromLeft(String column);
    int getColumnIndexFromRight(String column);
}