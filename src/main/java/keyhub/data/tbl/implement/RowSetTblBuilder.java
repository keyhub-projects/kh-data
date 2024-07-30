package keyhub.data.tbl.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblSchema;

import java.util.ArrayList;
import java.util.List;

public class RowSetTblBuilder extends TblBuilderImplement{
    private final List<List<Object>> rows = new ArrayList<>();

    public RowSetTblBuilder(TblSchema schema) {
        super(schema);
    }

    @Override
    public Tbl build() {
        return new RowSetTblImplement(this.schema, this.rows);
    }

    @Override
    public TblBuilder addRow(List<Object> row) {
        for(int i = 0; i < row.size(); i++) {
            String columnName = this.schema.getColumnNames().get(i);
            if(!this.schema.getColumnTypes().get(columnName).isInstance(row.get(i))) {
                throw new IllegalArgumentException("Row value type does not match schema");
            }
        }
        return this;
    }

}
