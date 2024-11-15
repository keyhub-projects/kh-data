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

package keyhub.data.stream.join.inner;

import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import keyhub.data.schema.KhSchemaBasedStructure;
import keyhub.data.stream.KhStream;
import keyhub.data.stream.join.KhStreamJoinImplement;
import keyhub.data.table.KhTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class KhRightStreamInnerJoinImplement extends KhStreamJoinImplement implements KhStreamInnerJoin {

    private final KhTable left;
    private final KhStream right;

    public KhRightStreamInnerJoinImplement(KhTable left, KhStream right) {
        this.left = left;
        this.right = right;
    }

    @Override
    protected Stream<KhRow> computeJoinData(KhSchema joinedSchema) {
        Stream<KhRow> origin = right.getRowStream();
        Stream<KhRow> filtered = filterJoinedRow(origin);
        return mapJoinedRow(joinedSchema, filtered);
    }
    private Stream<KhRow> filterJoinedRow(Stream<KhRow> origin){
        return origin.filter(streamRow -> left.stream()
                .anyMatch(tableRow -> isJoinedRow(tableRow, streamRow))
        );
    }
    private Stream<KhRow> mapJoinedRow(KhSchema joinedSchema, Stream<KhRow> filtered){
        return filtered.flatMap(streamRow -> {
            List<KhRow> result = new ArrayList<>();
            for(int i = 0; i < left.count(); i++){
                List<Object> row = new ArrayList<>();
                if(isJoinedRow(left.getRow(i), streamRow)){
                    row.addAll(left.getRawRow(i));
                    row.addAll(streamRow.toList());
                    result.add(KhRow.of(joinedSchema, row));
                }
            }
            return result.stream();
        });
    }

    @Override
    protected KhSchemaBasedStructure left() {
        return left;
    }
    @Override
    protected KhSchemaBasedStructure right() {
        return right;
    }
}
