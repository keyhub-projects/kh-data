package keyhub.data.join;

import keyhub.data.tbl.Tbl;

import java.util.Optional;

public interface JoinSet {
    default boolean checkType(Class<?> clazz){
        return clazz.equals(JoinSet.class);
    }

    Tbl toDataSet();
    JoinSet on(String sameKey);
    JoinSet on(String leftKey, String rightKey);

    JoinSet selectAll();
    JoinSet selectFromLeft(String column);
    JoinSet selectFromLeft(String... columns);
    JoinSet selectAllFromLeft();
    JoinSet selectFromRight(String column);
    JoinSet selectFromRight(String... columns);
    JoinSet selectAllFromRight();

    Optional<Integer> findColumnIndexFromLeft(String column);
    Optional<Integer> findColumnIndexFromRight(String column);
}