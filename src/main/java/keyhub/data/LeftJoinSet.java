package keyhub.data;

import keyhub.data.set.LeftJoinSetImplement;

public interface LeftJoinSet extends JoinSet {
    static JoinSet of(DataSet left, DataSet right) {
        return new LeftJoinSetImplement(left, right);
    }
}
