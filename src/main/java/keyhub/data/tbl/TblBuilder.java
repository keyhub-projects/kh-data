package keyhub.data.tbl;

import keyhub.data.tbl.implement.rowset.RowSetTblBuilder;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public interface TblBuilder {
    static TblBuilder forRowSet(TblSchema schema) {
        return new RowSetTblBuilder(schema);
    }
    TblBuilder addRow(List<Object> row);
    TblBuilder addRow(TblRow row);
    TblBuilder addRows(List<TblRow> rows);
    Tbl build();
}
