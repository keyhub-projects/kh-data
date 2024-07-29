package keyhub.data.tbl.join;

import keyhub.data.tbl.Tbl;

public interface LeftTblJoin extends TblJoin {
    static TblJoin of(Tbl left, Tbl right) {
        return new LeftTblJoinImplement(left, right);
    }
}
