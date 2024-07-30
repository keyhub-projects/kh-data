package keyhub.data.tbl.join;

import keyhub.data.tbl.Tbl;

public interface TblInnerJoin extends TblJoin {
    static TblJoin of(Tbl left, Tbl right) {
        return new TblInnerJoinImplement(left, right);
    }
}
