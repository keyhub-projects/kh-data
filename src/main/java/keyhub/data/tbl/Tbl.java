package keyhub.data.tbl;

import keyhub.data.tbl.join.TblJoin;

import java.util.*;

public interface Tbl {

    static Tbl of(List<String> columns, List<List<Object>> data) {
        return TblImplement.of(columns, data);
    }

    static Tbl of(List<Map<String, Object>> rowMapList) {
        return TblImplement.of(rowMapList);
    }

    static Tbl of(Map<String, List<Object>> columnMapList) {
        return TblImplement.of(columnMapList);
    }

    static <T> Tbl of(List<T> dtoList, Class<T> dtoClass) {
        return TblImplement.of(dtoList, dtoClass);
    }

    static TblValue.TblValueBuilder builder() {
        return TblImplement.builder();
    }

    List<List<Object>> adjustRows(List<List<Object>> rows);
    List<Object> adjustRow(List<Object> row);

    boolean validateRowsSize(List<List<Object>> row);

    boolean validateRowSize(List<Object> row);

    int size();
    List<Object> getRow(int index);
    List<List<Object>> getRows();
    int getColumnSize();
    String getColumn(int index);
    List<String> getColumns();
    Optional<Integer> findColumnIndex(String column);

    TblJoin leftJoin(Tbl right);
    TblJoin innerJoin(Tbl right);

    Tbl select(String... columns);
    Tbl selectAll();
    Tbl where(String column, String operator, Object value);
    Tbl clearWhere();

    Tbl getComputed();

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();
}
