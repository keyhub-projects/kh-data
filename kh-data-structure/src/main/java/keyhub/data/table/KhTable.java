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

package keyhub.data.table;

import keyhub.data.cell.KhCell;
import keyhub.data.stream.KhStream;
import keyhub.data.row.KhRow;
import keyhub.data.table.join.KhTableJoinFactory;
import keyhub.data.column.KhColumn;
import keyhub.data.schema.KhSchema;
import keyhub.data.table.query.KhTableQueryFactory;

import java.util.*;
import java.util.stream.Stream;

public interface KhTable extends Iterable<KhRow> {

    static KhTable empty() {
        return KhTableImplement.empty();
    }
    static KhTable empty(KhSchema schema) {
        return KhTableImplement.empty(schema);
    }
    static KhTable from(List<?> list) {
        return KhTableImplement.from(list);
    }
    static KhTable from(KhStream fbl){
        return KhTableImplement.from(fbl);
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
    KhColumn<?> getColumnSchema(int index);
    KhSchema getSchema();
    int getColumnSize();
    String getColumnName(int index);
    Class<?> getColumnType(int index);
    int getColumnIndex(String column);
    KhCell<?> getCell(int columnIndex, int rowIndex);
    Optional<KhCell<?>> findCell(String columnName, int rowIndex);

    Stream<KhRow> stream();
    KhTableQueryFactory query();
    KhTableJoinFactory leftJoin(KhTable right);
    KhTableJoinFactory innerJoin(KhTable right);

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();

}
