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

package keyhub.data.tbl;

import keyhub.data.cell.Cell;
import keyhub.data.fbl.Fbl;
import keyhub.data.tbl.join.TblJoinFactory;
import keyhub.data.row.Row;
import keyhub.data.column.Column;
import keyhub.data.schema.Schema;
import keyhub.data.tbl.query.TblQueryFactory;

import java.util.*;
import java.util.stream.Stream;

public interface Tbl extends Iterable<Row> {

    static Tbl empty() {
        return TblImplement.empty();
    }
    static Tbl empty(Schema schema) {
        return TblImplement.empty(schema);
    }
    static Tbl from(List<?> list) {
        return TblImplement.from(list);
    }
    static Tbl from(Fbl fbl){
        return TblImplement.from(fbl);
    }
    static Tbl of(Schema schema, List<List<Object>> rawRows){
        return TblImplement.of(schema, rawRows);
    }

    static TblBuilder builder(Schema schema){
        return TblBuilder.forRowSet(schema);
    }

    int count();
    Row getRow(int index);
    List<Row> getRows();
    List<Object> getRawRow(int index);
    Column<?> getColumnSchema(int index);
    Schema getSchema();
    int getColumnSize();
    String getColumnName(int index);
    Class<?> getColumnType(int index);
    int getColumnIndex(String column);
    Cell<?> getCell(int columnIndex, int rowIndex);
    Optional<Cell<?>> findCell(String columnName, int rowIndex);

    Stream<Row> stream();
    TblQueryFactory query();
    TblJoinFactory leftJoin(Tbl right);
    TblJoinFactory innerJoin(Tbl right);

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();

}
