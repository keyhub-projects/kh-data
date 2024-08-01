package keyhub.data.tbl.operator.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.TblOperator;
import keyhub.data.tbl.operator.TblOperatorImplement;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;
import java.util.Optional;

public class LikeOperator extends TblOperatorImplement {
    private final TblSchema schema;
    private final List<TblRow> rows;

    public LikeOperator(TblSchema schema, List<TblRow> rows) {
        this.schema = schema;
        this.rows = rows;
    }

    public static TblOperator of(Tbl tbl, String column, Object value) {
        TblSchema originSchema = tbl.getSchema();
        if(!originSchema.contains(column)){
            throw new IllegalArgumentException("Column not found");
        }
        List<TblRow> originData = tbl.getRows();
        List<TblRow> filtered = originData.stream().filter(row -> {
            Optional<Object> cell = row.findCell(column);
            if(cell.isEmpty()){
                return false;
            }
            if(cell.get() instanceof String s && value instanceof String v){
                return s.contains(v);
            }
            return false;
        }).toList();

        return new LikeOperator(originSchema, filtered);
    }

    @Override
    public List<TblRow> rows() {
        return this.rows;
    }

    @Override
    public TblSchema schema() {
        return this.schema;
    }
}
