package keyhub.data.join;

import keyhub.data.tbl.Tbl;

public interface InnerJoinSet extends JoinSet{
    static JoinSet of(Tbl left, Tbl right) {
        return new InnerJoinSetImplement(left, right);
    }
}
