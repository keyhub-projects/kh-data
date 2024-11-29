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

package keyhub.data.structure.table;

import keyhub.data.structure.cell.KhCell;
import keyhub.data.structure.join.KhJoinable;
import keyhub.data.structure.schema.KhSchemaBasedStructure;
import keyhub.data.structure.stream.KhStream;
import keyhub.data.structure.row.KhRow;
import keyhub.data.structure.stream.join.KhStreamJoin;
import keyhub.data.structure.table.join.KhTableJoin;
import keyhub.data.structure.schema.KhSchema;
import keyhub.data.structure.table.query.KhTableQuery;

import java.util.*;
import java.util.stream.Stream;

public interface KhTable extends Iterable<KhRow>, KhSchemaBasedStructure, KhJoinable {

    static KhTable empty() {
        return KhTableImplement.empty();
    }
    static KhTable empty(KhSchema schema) {
        return KhTableImplement.empty(schema);
    }
    static KhTable from(List<?> list) {
        return KhTableImplement.from(list);
    }
    static KhTable from(KhStream stream){
        return KhTableImplement.from(stream);
    }
    static KhTable of(KhSchema schema, List<List<Object>> rawRows){
        return KhTableImplement.of(schema, rawRows);
    }

    static KhTableBuilder builder(KhSchema schema){
        return KhTableBuilder.forRowSet(schema);
    }

    int count();
    KhRow getRow(int index);
    List<KhRow> getRows();
    List<Object> getRawRow(int index);
    KhCell<?> getCell(int columnIndex, int rowIndex);
    Optional<KhCell<?>> findCell(String columnName, int rowIndex);

    Stream<KhRow> stream();
    KhTableQuery query();
    KhTableJoin leftJoin(KhTable right);
    KhTableJoin innerJoin(KhTable right);
    KhStreamJoin leftJoin(KhStream right);
    KhStreamJoin innerJoin(KhStream right);

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();
    KhStream toStream();
}
