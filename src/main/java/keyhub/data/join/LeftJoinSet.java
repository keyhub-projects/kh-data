package keyhub.data.join;

import keyhub.data.tbl.Tbl;

public interface LeftJoinSet extends JoinSet {
    static JoinSet of(Tbl left, Tbl right) {
        return new LeftJoinSetImplement(left, right);
    }
}
