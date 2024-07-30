package keyhub.data.tbl.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;

import java.util.ArrayList;
import java.util.List;

public class ColumnSetTblBuilder extends TblBuilderImplement implements TblBuilder {
    private final List<List<Object>> columns = new ArrayList<>();

    public ColumnSetTblBuilder(TblSchema schema) {
        super(schema);
    }


    @Override
    public TblBuilder addRow(List<Object> row) {
        return null;
    }

    @Override
    public Tbl build() {
        return null;
    }
}
