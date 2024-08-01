package keyhub.data.tbl.join.left;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.join.TblJoinImplement;

import java.util.ArrayList;
import java.util.List;

public class TblLeftJoinImplement extends TblJoinImplement implements TblLeftJoin {


    public TblLeftJoinImplement(Tbl left, Tbl right) {
        super(left, right);
    }

    @Override
    public List<List<Object>> computeJoinRawResult(){

        List<List<Object>> rawResult = new ArrayList<>();
        for(int i = 0; i < this.left.count(); i++){
            boolean anyMatched = false;
            for(int j = 0; j < this.right.count(); j++){
                boolean isJoined = isJoinedRow(this.left.getRow(i), this.right.getRow(j));
                if(isJoined){
                    List<Object> row = new ArrayList<>();
                    row.addAll(left.getRawRow(i));
                    row.addAll(right.getRawRow(j));
                    anyMatched = true;
                    rawResult.add(row);
                }
            }
            if(!anyMatched){
                List<Object> emptyRow = new ArrayList<>();
                emptyRow.addAll(left.getRawRow(i));
                for(int k = 0; k < right.getColumnSize(); k++){
                    emptyRow.add(null);
                }
                rawResult.add(emptyRow);
            }
        }
        return rawResult;
    }
}
