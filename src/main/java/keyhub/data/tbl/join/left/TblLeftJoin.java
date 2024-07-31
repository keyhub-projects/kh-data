package keyhub.data.tbl.join.left;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.join.TblJoin;

public interface TblLeftJoin extends TblJoin {
    static TblJoin of(Tbl left, Tbl right) {
        return new TblLeftJoinImplement(left, right);
    }
}
