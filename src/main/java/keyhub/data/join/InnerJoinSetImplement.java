package keyhub.data.join;


import keyhub.data.tbl.Tbl;

import java.util.ArrayList;
import java.util.List;

public class InnerJoinSetImplement extends JoinSetImplement implements InnerJoinSet {

    public InnerJoinSetImplement(Tbl left, Tbl right) {
        super(left, right);
    }

    @Override
    public List<List<Object>> computeJoinRawResult(){
        List<List<Object>> rows = new ArrayList<>();
        for(int i = 0; i < left.size(); i++){
            for(int j = 0; j < right.size(); j++){
                boolean isJoined = isJoinedRow(this.left.getRow(i), this.right.getRow(j));
                if(isJoined){
                    List<Object> row = new ArrayList<>();
                    row.addAll(left.getRow(i));
                    row.addAll(right.getRow(j));
                    rows.add(row);
                }
            }
        }
        return rows;
    }
}