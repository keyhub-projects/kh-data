package keyhub.data.tbl;

import keyhub.data.join.JoinSet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Tbl {
    static Tbl of(List<String> columns, List<List<Object>> data) {
        return new TblValue(columns, data);
    }

    static Tbl of(List<String> columns) {
        return new TblValue(columns);
    }

    static TblValue.TblValueBuilder builder() {
        return new TblValue.TblValueBuilder();
    }

    int size();
    List<Object> getRow(int index);
    List<List<Object>> getRows();
    int getColumnSize();
    String getColumn(int index);
    List<String> getColumns();
    Optional<Integer> findColumnIndex(String column);

    JoinSet leftJoin(Tbl right);
    JoinSet innerJoin(Tbl right);

    Tbl select(String... columns);
    Tbl selectAll();
    Tbl where(String column, String operator, Object value);
    Tbl clearWhere();

    Tbl getComputed();

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnMapList();
}
