package keyhub.data.tbl.schema;

public class TblColumnSchemaValue<T> extends TblColumnSchemaImplement<T> {
    private final String columnName;
    private final Class<T> columnType;

    @Override
    protected String columnName() {
        return this.columnName;
    }

    @Override
    protected Class<T> columnType() {
        return this.columnType;
    }

    public TblColumnSchemaValue(String columnName, Class<T> columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }
}
