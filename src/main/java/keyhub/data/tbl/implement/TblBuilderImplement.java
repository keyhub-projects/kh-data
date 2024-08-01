package keyhub.data.tbl.implement;

import keyhub.data.tbl.TblBuilder;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public abstract class TblBuilderImplement implements TblBuilder {
    protected final TblSchema schema;

    protected TblBuilderImplement(TblSchema schema) {
        this.schema = schema;
    }

    @Override
    public TblBuilder addRawRows(List<List<Object>> rows) {
        for(List<Object> row : rows) {
            addRawRow(row);
        }
        return this;
    }

    @Override
    public TblBuilder addRows(List<TblRow> rows) {
        for(TblRow row : rows) {
            addRow(row);
        }
        return this;
    }
}
