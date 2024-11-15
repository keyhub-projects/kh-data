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

package keyhub.data.table.join;

import keyhub.data.join.KhJoin;
import keyhub.data.table.KhTable;
import keyhub.data.function.KhColumnSelector;

public interface KhTableJoin extends KhJoin<KhTable, KhTableJoin> {

    KhTableJoin on(String sameKey);
    KhTableJoin on(String leftKey, String rightKey);

    KhTableJoin selectAll();

    KhTableJoin selectFromLeft(KhColumnSelector... selectors);
    KhTableJoin selectFromLeft(String column);
    KhTableJoin selectFromLeft(String... columns);
    KhTableJoin selectAllFromLeft();

    KhTableJoin selectFromRight(KhColumnSelector... selectors);
    KhTableJoin selectFromRight(String column);
    KhTableJoin selectFromRight(String... columns);
    KhTableJoin selectAllFromRight();

    int getColumnIndexFromLeft(String column);
    int getColumnIndexFromRight(String column);
}