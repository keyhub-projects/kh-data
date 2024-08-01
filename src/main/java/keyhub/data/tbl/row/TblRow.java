package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblSchema;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TblRow {
    static TblRow of(TblSchema schema, Object... values) {
        return TblRowImplement.of(schema, Arrays.stream(values).toList());
    }

    static TblRow of(TblSchema schema, List<Object> values) {
        return TblRowImplement.of(schema, values);
    }

    TblSchema getSchema();
    List<Object> toList();
    <T> Optional<T> findCell(String columnName);

}
