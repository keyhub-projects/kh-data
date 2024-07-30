package keyhub.data.tbl.join;

import keyhub.data.tbl.Tbl;

public interface TblLeftJoin extends TblJoin {
    static TblJoin of(Tbl left, Tbl right) {
        return new TblLeftJoinImplement(left, right);
    }
}
