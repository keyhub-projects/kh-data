package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;
import java.util.Optional;

public abstract class TblRowImplement implements TblRow {
    public static TblRow of(TblSchema schema, List<Object> values) {
        if(schema.getColumnTypes().size() != values.size()){
            throw new IllegalArgumentException("The number of columns and values must be the same");
        }
        for(int i = 0; i < values.size(); i++) {
            String columnName = schema.getColumnNames().get(i);
            if(!schema.getColumnTypes().get(columnName).isInstance(values.get(i))) {
                throw new IllegalArgumentException("Row value type does not match schema");
            }
        }
        return new TblRowValue(schema, values);
    }

    protected abstract TblSchema schema();
    protected abstract List<Object> values();

    @Override
    public TblSchema getSchema() {
        return schema();
    }
    @Override
    public List<Object> toList() {
        return values();
    }
    @Override
    public <T> Optional<T> findValue(String columnName) {
        Optional<TblColumnSchema<?>> schema = schema().findColumnSchema(columnName);
        if (schema.isEmpty()) {
            return Optional.empty();
        }
        int index = schema().getColumnNames().indexOf(columnName);
        Class<T> columnType = (Class<T>) schema.get().getColumnType();
        return Optional.of(columnType.cast(values().get(index)));
    }

}
