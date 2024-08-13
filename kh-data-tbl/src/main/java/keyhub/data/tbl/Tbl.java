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

import keyhub.data.DataObject;
import keyhub.data.tbl.stream.TblStream;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.filter.TblFilterType;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;

import java.util.*;

public interface Tbl extends DataObject, Iterable<TblRow> {

    static Tbl empty() {
        return TblImplement.empty();
    }

    static Tbl empty(TblSchema schema) {
        return TblImplement.empty(schema);
    }

    static Tbl from(List<?> objectList) {
        return TblImplement.from(objectList);
    }

    static Tbl fromObjects(List<?> objectList) {
        return TblImplement.fromObjects(objectList);
    }

    static Tbl of(TblSchema schema, List<List<Object>> data) {
        return TblImplement.of(schema, data);
    }

    static Tbl fromRowMapList(List<Map<String, Object>> rowMapList) {
        return TblImplement.fromRowMapList(rowMapList);
    }

    static Tbl fromColumnListMap(Map<String, List<Object>> columnListMap) {
        return TblImplement.fromColumnListMap(columnListMap);
    }

    static TblBuilder builder(TblSchema schema){
        return TblBuilder.forRowSet(schema);
    }

    int count();
    TblRow getRow(int index);
    List<TblRow> getRows();
    List<Object> getRawRow(int index);
    List<List<Object>> getRawRows();
    TblColumnSchema<?> getColumnSchema(int index);
    TblSchema getSchema();
    int getColumnSize();
    String getColumn(int index);
    List<String> getColumns();
    Class<?> getColumnType(int index);
    Map<String, Class<?>> getColumnTypes();
    int getColumnIndex(String column);

    TblStream stream();

    Tbl select(String... columns);
    Tbl select(List<String> columns);

    Tbl where(String column, TblFilterType operator, Object value);
    Tbl where(String column, TblFilterType operator);

    TblJoin leftJoin(Tbl right);
    TblJoin innerJoin(Tbl right);

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();

    Object findCell(String columnName, int rowIndex);

    Object getCell(int columnIndex, int rowIndex);
}
