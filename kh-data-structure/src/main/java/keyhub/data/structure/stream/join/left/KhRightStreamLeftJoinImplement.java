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

package keyhub.data.structure.stream.join.left;

import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.schema.KhSchemaBasedStructure;
import keyhub.data.structure.stream.KhStream;
import keyhub.data.structure.stream.join.KhStreamJoinImplement;
import keyhub.data.structure.table.KhTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class KhRightStreamLeftJoinImplement extends KhStreamJoinImplement implements KhStreamLeftJoin  {
    private final KhTable left;
    private final KhStream right;

    public KhRightStreamLeftJoinImplement(KhTable left, KhStream right) {
        this.left = left;
        this.right = right;
    }

    // left, right -> map with isJoinedRow ? left + right : left + right as null -> KhRow
    @Override
    protected Stream<KhRow> computeJoinData(KhSchema schema) {
        Stream<KhRow> origin = right.getRowStream();
        return origin.flatMap(streamRow -> {
            List<KhRow> result = new ArrayList<>();
            for(int i = 0; i < left.count(); i++){
                List<Object> row = new ArrayList<>();
                row.addAll(left.getRawRow(i));
                if(isJoinedRow(left.getRow(i), streamRow)){
                    row.addAll(streamRow.toList());
                    result.add(KhRow.of(schema, row));
                }else{
                    for(int j = 0; j < right.getSchema().getColumnSize(); j++){
                        row.add(null);
                    }
                    result.add(KhRow.of(schema, row));
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
