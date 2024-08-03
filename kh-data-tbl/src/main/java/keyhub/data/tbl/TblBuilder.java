package keyhub.data.tbl;

import keyhub.data.tbl.implement.TblBuilderImplement;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public interface TblBuilder {
    static TblBuilder forRowSet(TblSchema schema) {
        return TblBuilderImplement.forRowSet(schema);
    }
    TblBuilder addRawRow(List<Object> row);
    TblBuilder addRawRows(List<List<Object>> rows);
    TblBuilder addRow(TblRow row);
    TblBuilder addRows(List<TblRow> rows);
    Tbl build();
}
