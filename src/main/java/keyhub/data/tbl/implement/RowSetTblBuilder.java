package keyhub.data.tbl.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;

import java.util.ArrayList;
import java.util.List;

public class RowSetTblBuilder extends TblBuilderImplement implements TblBuilder{
    private final List<List<Object>> rows = new ArrayList<>();

    public RowSetTblBuilder(TblSchema schema) {
        super(schema);
    }

    @Override
    public Tbl build() {
        return null;
    }

    @Override
    public TblBuilder addRow(List<Object> row) {
        for(int i = 0; i < row.size(); i++) {
            if(!this.schema.getColumnTypes().get(i).isInstance(row.get(i))) {
                throw new IllegalArgumentException("Row value type does not match schema");
            }
        }
        return this;
    }

}
