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

package keyhub.data.structure.stream.join.inner;

import keyhub.data.structure.stream.KhStream;
import keyhub.data.structure.stream.join.KhStreamJoin;
import keyhub.data.structure.table.KhTable;

/**
 * Interface representing an inner join operation between a KhStream and a KhTable.
 */
public interface KhStreamInnerJoin extends KhStreamJoin {

    /**
     * Creates an inner join between a KhStream and a KhTable.
     *
     * @param left the left stream in the join operation
     * @param right the right table in the join operation
     * @return a KhStreamJoin instance representing the inner join result
     */
    static KhStreamJoin of(KhStream left, KhTable right) {
        return new KhLeftStreamInnerJoinImplement(left, right);
    }

    /**
     * Creates an inner join between a KhTable and a KhStream.
     *
     * @param left the left table in the join operation
     * @param right the right stream in the join operation
     * @return a KhStreamJoin instance representing the inner join result
     */
    static KhStreamJoin of(KhTable left, KhStream right){
        return new KhRightStreamInnerJoinImplement(left, right);
    }
}