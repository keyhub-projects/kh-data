package keyhub.data.tbl.implement;

import keyhub.data.tbl.TblBuilder;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public abstract class TblBuilderImplement implements TblBuilder {
    protected final TblSchema schema;

    protected TblBuilderImplement(TblSchema schema) {
        this.schema = schema;
    }

    @Override
    public TblBuilder addRows(List<List<Object>> data) {
        for(List<Object> row : data) {
            addRow(row);
        }
        return this;
    }
}
