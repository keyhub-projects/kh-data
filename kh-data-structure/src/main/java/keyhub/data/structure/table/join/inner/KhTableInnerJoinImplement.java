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

package keyhub.data.structure.table.join.inner;


import keyhub.data.structure.table.KhTable;
import keyhub.data.structure.table.join.KhTableJoinImplement;

import java.util.ArrayList;
import java.util.List;

public class KhTableInnerJoinImplement extends KhTableJoinImplement implements KhTableInnerJoin {

    public KhTableInnerJoinImplement(KhTable left, KhTable right) {
        super(left, right);
    }

    @Override
    public List<List<Object>> computeJoinRawResult(){
        List<List<Object>> rows = new ArrayList<>();
        for(int i = 0; i < this.left.count(); i++){
            for(int j = 0; j < right.count(); j++){
                boolean isJoined = isJoinedRow(this.left.getRow(i), this.right.getRow(j));
                if(isJoined){
                    List<Object> row = new ArrayList<>();
                    row.addAll(this.left.getRawRow(i));
                    row.addAll(this.right.getRawRow(j));
                    rows.add(row);
                }
            }
        }
        return rows;
    }
}