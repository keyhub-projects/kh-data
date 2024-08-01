package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public abstract class TblOperatorImplement implements TblOperator{

    public abstract List<TblRow> rows();
    public abstract TblSchema schema();

    @Override
    public Tbl getResult() {
        return Tbl.builder(schema())
                .addRows(rows())
                .build();
    }
}
