package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public class TblRowValue extends TblRowImplement {
    private final TblSchema schema;
    private final List<Object> values;

    public TblRowValue(TblSchema schema, List<Object> values) {
        this.schema = schema;
        this.values = values;
    }
    @Override
    protected TblSchema schema() {
        return this.schema;
    }
    @Override
    protected List<Object> values() {
        return this.values;
    }
}
