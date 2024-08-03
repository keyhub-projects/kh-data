package keyhub.data.tbl.schema;

public abstract class TblColumnSchemaImplement<T> implements TblColumnSchema<T> {
    protected abstract String columnName();
    protected abstract Class<T> columnType();

    public static TblColumnSchema<?> of(String columnName, Class<?> columnType){
        return new TblColumnSchemaValue<>(columnName, columnType);
    }
    public String getColumnName(){
        return columnName();
    }
    public Class<T> getColumnType(){
        return columnType();
    }
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblColumnSchema<?> that = (TblColumnSchema<?>) o;
        return columnName().equals(that.getColumnName()) && columnType().equals(that.getColumnType());
    }
}
