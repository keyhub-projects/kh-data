package keyhub.data.tbl.schema;

import keyhub.data.DataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TblSchemaValue extends TblSchemaImplement implements DataValue {
    private final List<String> columnNames;
    private final Map<String, Class<?>> columnTypes;

    public TblSchemaValue(List<TblColumnSchema> schemas){
        this.columnNames = schemas.stream().map(TblColumnSchema::getColumnName).toList();
        this.columnTypes = new HashMap<>();
        schemas.forEach(schema -> this.columnTypes.put(schema.getColumnName(), schema.getColumnType()));
    }
    public TblSchemaValue(TblSchemaValueBuilder builder) {
        this.columnNames = builder.columnNames;
        this.columnTypes = new HashMap<>();
        this.columnNames.forEach(columnName ->
                this.columnTypes.put(columnName, builder.columnTypes.get(columnName))
        );
    }

    @Override
    protected List<String> columnNames() {
        return this.columnNames;
    }
    @Override
    protected Map<String, Class<?>> columnTypes() {
        return this.columnTypes;
    }

    public static class TblSchemaValueBuilder {
        private List<String> columnNames = new ArrayList<>();
        private Map<String, Class<?>> columnTypes = new HashMap<>();

        public TblSchemaValueBuilder addColumn(String columnName, Class<?> columnType) {
            this.columnNames.add(columnName);
            this.columnTypes.put(columnName, columnType);
            return this;
        }
        public TblSchemaValueBuilder addColumn(TblColumnSchema<?> tblColumnSchema) {
            this.columnNames.add(tblColumnSchema.getColumnName());
            this.columnTypes.put(tblColumnSchema.getColumnName(), tblColumnSchema.getColumnType());
            return this;
        }
        public TblSchemaValueBuilder addColumns(List<TblColumnSchema<?>> tblColumnSchemas) {
            tblColumnSchemas.forEach(this::addColumn);
            return this;
        }
        public TblSchemaValue build() {
            return new TblSchemaValue(this);
        }
    }
}
