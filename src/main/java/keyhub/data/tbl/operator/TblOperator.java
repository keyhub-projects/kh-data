package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;

public interface TblOperator {
    static TblOperatorBuilder builder() {
        return TblOperatorBuilder.of();
    }

    Tbl getResult();
}
