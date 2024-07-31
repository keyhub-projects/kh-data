package keyhub.data.tbl.operator.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.TblOperator;
import keyhub.data.tbl.operator.TblOperatorImplement;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public class NotInOperator extends TblOperatorImplement {
    public static TblOperator of(Tbl tbl, String column) {
        return null;
    }

    @Override
    public List<List<Object>> data() {
        return List.of();
    }

    @Override
    public TblSchema schema() {
        return null;
    }
}
