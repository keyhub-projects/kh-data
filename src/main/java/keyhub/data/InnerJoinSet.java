package keyhub.data;

import keyhub.data.set.InnerJoinSetImplement;

public interface InnerJoinSet extends JoinSet{
    static JoinSet of(DataSet left, DataSet right) {
        return new InnerJoinSetImplement(left, right);
    }
}
