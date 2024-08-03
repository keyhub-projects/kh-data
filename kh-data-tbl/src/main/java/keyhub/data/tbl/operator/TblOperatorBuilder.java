package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;

public interface TblOperatorBuilder {

    static TblOperatorBuilder of() {
        return SimpleTblOperatorBuilder.of();
    }

    TblOperatorBuilder tbl(Tbl tbl);

    TblOperatorBuilder operator(TblOperatorType operator);

    TblOperatorBuilder column(String column);

    TblOperatorBuilder value(Object value);
    Tbl build();
}
