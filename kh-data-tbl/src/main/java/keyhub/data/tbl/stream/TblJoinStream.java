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

package keyhub.data.tbl.stream;

import keyhub.data.tbl.function.TblJoinColumnSelector;
import keyhub.data.tbl.function.TblJoinSchemaPredicate;

public interface TblJoinStream {
    // 스트림을 위해선 순서가 중요해.. sorted join 이 기본 골자이기 때문
    // 소티드에 따라, right는 데이터그램으로 처리된다.
    // 1. 순서대로 on 조건 처리
    // left는 on 조건으로 right를 다 확인 => 소티드일 경우, 스코프만 확인

    static TblJoinStream of(TblStream leftStream, TblStream rightStream, TblJoinSchemaPredicate[] joinFilters) {
        return TblJoinStreamImplement.of(leftStream, rightStream, joinFilters);
    }

    TblJoinStream select(TblJoinColumnSelector... selectors);

    TblStream toJoinedStream();
}
