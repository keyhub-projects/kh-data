package keyhub.data.tbl;

import keyhub.data.join.JoinSet;

import java.lang.reflect.Field;
import java.util.*;

public interface Tbl {
    static Tbl of(List<String> columns, List<List<Object>> data) {
        return new TblValue(columns, data);
    }

    static Tbl of(List<Map<String, Object>> rowMapList) {
        if(rowMapList.isEmpty()) {
            return new TblValue(List.of());
        }
        var columns = List.copyOf(rowMapList.getFirst().keySet());
        var rows = rowMapList.stream()
                .map(row -> columns.stream().map(row::get).toList())
                .toList();
        return new TblValue(columns, rows);
    }

    static Tbl of(Map<String, List<Object>> columnMapList) {
        var columns = List.copyOf(columnMapList.keySet());
        List<List<Object>> rows = new ArrayList<>();
        for(int i = 0; i < columnMapList.get(columns.getFirst()).size(); i++) {
            List<Object> row = new ArrayList<>();
            for(String column : columns) {
                row.add(columnMapList.get(column).get(i));
            }
            rows.add(row);
        }

        return new TblValue(columns, rows);
    }

    static <T> Tbl of(List<T> dtoList, Class<T> dtoClass) {
        var columns = Arrays.stream(dtoClass.getDeclaredFields()).map(Field::getName).toList();
        List<List<Object>> rows = dtoList.stream()
                .map(dto -> Arrays.stream(dtoClass.getDeclaredFields())
                        .map(field -> {
                            try {
                                field.setAccessible(true);
                                return field.get(dto);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }finally {
                                field.setAccessible(false);
                            }
                        })
                        .toList())
                .toList();
        return new TblValue(columns, rows);
    }

    static TblValue.TblValueBuilder builder() {
        return new TblValue.TblValueBuilder();
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

    JoinSet leftJoin(Tbl right);
    JoinSet innerJoin(Tbl right);

    Tbl select(String... columns);
    Tbl selectAll();
    Tbl where(String column, String operator, Object value);
    Tbl clearWhere();

    Tbl getComputed();

    List<Map<String, Object>> toRowMapList();
    Map<String, List<Object>> toColumnListMap();
}
