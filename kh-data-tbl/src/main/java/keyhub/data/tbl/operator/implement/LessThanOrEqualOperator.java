package keyhub.data.tbl.operator.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.TblOperator;
import keyhub.data.tbl.operator.TblOperatorImplement;
import keyhub.data.tbl.schema.TblSchema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class LessThanOrEqualOperator extends TblOperatorImplement {
    private final TblSchema schema;
    private final List<List<Object>> rows;

    public LessThanOrEqualOperator(TblSchema schema, List<List<Object>> rows) {
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
            // null 미포함? 고민.. 일단 미포함
            if(cell.isEmpty()){
                return false;
            }
            var cellValue = cell.get();
            return switch (cellValue) {
                case Integer i -> {
                    if (value instanceof Integer) {
                        yield i < (int) value || i == (int) value;
                    }
                    yield false;
                }
                case Double d -> {
                    if (value instanceof Double) {
                        yield d < (double) value || d == (double) value;
                    }
                    yield false;
                }
                case Long l -> {
                    if (value instanceof Long) {
                        yield l < (long) value || l == (long) value;
                    }
                    yield false;
                }
                case Float f -> {
                    if (value instanceof Float) {
                        yield f < (float) value || f == (float) value;
                    }
                    yield false;
                }
                case String s -> {
                    if (value instanceof String) {
                        yield s.compareTo((String) value) < 0 || s.equals(value);
                    }
                    yield false;
                }
                case LocalDateTime dt -> {
                    if (value instanceof LocalDateTime) {
                        yield dt.isBefore((LocalDateTime) value) || dt.isEqual((LocalDateTime) value);
                    }
                    yield false;
                }
                case LocalDate date -> {
                    if (value instanceof LocalDate) {
                        yield date.isBefore((LocalDate) value) || date.isEqual((LocalDate) value);
                    }
                    yield false;
                }
                case LocalTime time -> {
                    if (value instanceof LocalTime) {
                        yield time.isBefore((LocalTime) value) || time.equals((LocalTime) value);
                    }
                    yield false;
                }
                default -> false;
            };
        }).toList();

        return new LessThanOrEqualOperator(originSchema, filtered);
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