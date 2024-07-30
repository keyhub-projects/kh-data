package keyhub.data.tbl;

import keyhub.data.DataObject;
import keyhub.data.tbl.implement.RowSetTblImplement;
import keyhub.data.tbl.implement.TblBuilder;
import keyhub.data.tbl.implement.TblImplement;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;

import java.util.*;

public interface Tbl extends DataObject {

    static Tbl of(TblSchema schema, List<List<Object>> data) {
        return new RowSetTblImplement(schema, data);
    }

    static Tbl of(List<Map<String, Object>> rowMapList) {
        return TblImplement.of(rowMapList);
    }

    static Tbl of(Map<String, List<Object>> columnListMap) {
        return TblImplement.of(columnListMap);
    }

    static TblBuilder builder(TblSchema schema){
        return TblBuilder.forRowSet(schema);
    }

    int count();
    TblRow getRow(int index);
    List<TblRow> getRows();
    TblColumnSchema<?> getColumnSchema(int index);
    TblSchema getSchema();
    int getColumnSize();
    String getColumn(int index);
    List<String> getColumns();
    Class<?> getColumnType(int index);
    Map<String, Class<?>> getColumnTypes();

    TblJoin leftJoin(Tbl right);
    TblJoin innerJoin(Tbl right);

    Tbl select(String... columns);
    Tbl selectAll();
    Tbl where(String column, String operator, Object value);

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();
}
