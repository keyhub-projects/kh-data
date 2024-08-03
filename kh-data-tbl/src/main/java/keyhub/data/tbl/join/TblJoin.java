package keyhub.data.tbl.join;

import keyhub.data.tbl.Tbl;

public interface TblJoin {
    Tbl toTbl();
    TblJoin on(String sameKey);
    TblJoin on(String leftKey, String rightKey);

    TblJoin selectAll();
    TblJoin selectFromLeft(String column);
    TblJoin selectFromLeft(String... columns);
    TblJoin selectAllFromLeft();
    TblJoin selectFromRight(String column);
    TblJoin selectFromRight(String... columns);
    TblJoin selectAllFromRight();

    int getColumnIndexFromLeft(String column);
    int getColumnIndexFromRight(String column);

}