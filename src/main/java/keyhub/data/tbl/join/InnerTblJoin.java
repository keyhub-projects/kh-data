package keyhub.data.tbl.join;

import keyhub.data.tbl.Tbl;

public interface InnerTblJoin extends TblJoin {
    static TblJoin of(Tbl left, Tbl right) {
        return new InnerTblJoinImplement(left, right);
    }
}
