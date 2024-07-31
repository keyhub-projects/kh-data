package keyhub.data.tbl;

import keyhub.data.tbl.implement.rowset.RowSetTblBuilder;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public interface TblBuilder {
    static TblBuilder forRowSet(TblSchema schema) {
        return new RowSetTblBuilder(schema);
    }
    TblBuilder addRow(List<Object> row);
    TblBuilder addRows(List<List<Object>> data);
    Tbl build();
}
