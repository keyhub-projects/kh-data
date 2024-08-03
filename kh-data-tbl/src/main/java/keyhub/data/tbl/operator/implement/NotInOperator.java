package keyhub.data.tbl.operator.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.TblOperator;
import keyhub.data.tbl.operator.TblOperatorImplement;
import keyhub.data.tbl.schema.TblSchema;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class NotInOperator extends TblOperatorImplement {
    private final TblSchema schema;
    private final List<List<Object>> rows;

    public NotInOperator(TblSchema schema, List<List<Object>> rows) {
        this.schema = schema;
        this.rows = rows;
    }

    public static TblOperator of(Tbl tbl, String column, Object value) {
        TblSchema originSchema = tbl.getSchema();
        if(!originSchema.contains(column)){
            throw new IllegalArgumentException("Column not found");
        }
        List<List<Object>> originData = tbl.getRawRows();
        List<List<Object>> filtered = originData.stream().filter(row -> {
            int index = originSchema.getColumnIndex(column);
            Optional<Object> cell = Optional.ofNullable(row.get(index));
            if(cell.isEmpty()){
                return true;
            }
            var cellValue = cell.get();
            return switch (value){
                case List<?> list -> !list.contains(cellValue);
                case Object[] array -> !Arrays.asList(array).contains(cellValue);
                default -> false;
            };
        }).toList();
        return new NotInOperator(originSchema, filtered);
    }
    
    @Override
    public List<List<Object>> rows() {
        return this.rows;
    }

    @Override
    public TblSchema schema() {
        return this.schema;
    }
}
