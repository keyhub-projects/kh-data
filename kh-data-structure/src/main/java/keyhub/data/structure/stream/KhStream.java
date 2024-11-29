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

package keyhub.data.structure.stream;

import keyhub.data.structure.column.KhColumn;
import keyhub.data.structure.function.KhCellSelector;
import keyhub.data.structure.function.KhRowPredicate;
import keyhub.data.structure.join.KhJoinable;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.schema.KhSchemaBasedStructure;
import keyhub.data.structure.stream.join.KhStreamJoin;
import keyhub.data.structure.table.KhTable;
import java.util.List;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public interface KhStream extends BaseStream<KhRow, KhStream>, KhSchemaBasedStructure, KhJoinable {
    static KhStream from(KhTable tbl){
        return KhStreamImplement.from(tbl);
    }
    static KhStream of(KhSchema schema, Stream<KhRow> rowStream) {
        return KhStreamImplement.of(schema, rowStream);
    }

    KhStream select(String... columns);
    KhStream select(KhColumn<?>... columns);
    KhStream select(KhCellSelector... selector);
    KhStream where(KhRowPredicate filter);
    KhStreamJoin leftJoin(KhTable tbl);
    KhStreamJoin innerJoin(KhTable tbl);

    Stream<KhRow> getRowStream();
    List<KhRow> toList();
    KhTable toTable();
}
