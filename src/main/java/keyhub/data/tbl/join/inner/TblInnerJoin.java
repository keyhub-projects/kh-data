package keyhub.data.tbl.join.inner;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.join.TblJoin;

public interface TblInnerJoin extends TblJoin {
    static TblJoin of(Tbl left, Tbl right) {
        return new TblInnerJoinImplement(left, right);
    }
}
