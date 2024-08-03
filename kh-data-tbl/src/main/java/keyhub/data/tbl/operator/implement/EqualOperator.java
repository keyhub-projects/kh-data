package keyhub.data.tbl.operator.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.TblOperator;
import keyhub.data.tbl.operator.TblOperatorImplement;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;
import java.util.Optional;

public class EqualOperator extends TblOperatorImplement {
    private final TblSchema schema;
    private final List<List<Object>> data;

    public EqualOperator(TblSchema schema, List<List<Object>> data) {
        this.schema = schema;
        this.data = data;
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
            return cell.isPresent() && cell.get().equals(value);
        }).toList();

        return new EqualOperator(originSchema, filtered);
    }

    @Override
    public List<List<Object>> rows() {
        return this.data;
    }

    @Override
    public TblSchema schema() {
        return this.schema;
    }
}
