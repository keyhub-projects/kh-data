package keyhub.data.tbl.join;

import keyhub.data.tbl.Tbl;

import java.util.ArrayList;
import java.util.List;

public class LeftTblJoinImplement extends TblJoinImplement implements LeftTblJoin {


    public LeftTblJoinImplement(Tbl left, Tbl right) {
        super(left, right);
    }

    @Override
    public List<List<Object>> computeJoinRawResult(){

        List<List<Object>> rawResult = new ArrayList<>();
        for(int i = 0; i < left.size(); i++){
            boolean anyMatched = false;
            for(int j = 0; j < right.size(); j++){
                boolean isJoined = isJoinedRow(this.left.getRow(i), this.right.getRow(j));
                if(isJoined){
                    List<Object> row = new ArrayList<>();
                    row.addAll(left.getRow(i));
                    row.addAll(right.getRow(j));
                    anyMatched = true;
                    rawResult.add(row);
                }
            }
            if(!anyMatched){
                List<Object> emptyRow = new ArrayList<>();
                emptyRow.addAll(left.getRow(i));
                for(int k = 0; k < right.getColumnSize(); k++){
                    emptyRow.add(null);
                }
                rawResult.add(emptyRow);
            }
        }
        return rawResult;
    }
}
