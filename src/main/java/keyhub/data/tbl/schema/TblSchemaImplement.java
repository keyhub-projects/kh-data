package keyhub.data.tbl.schema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TblSchemaImplement implements TblSchema{
    public static TblSchema of(List<TblColumnSchema> tblColumnSchemas){
        return new TblSchemaValue(tblColumnSchemas);
    }
    public static TblSchemaValue.TblSchemaValueBuilder builder(){
        return new TblSchemaValue.TblSchemaValueBuilder();
    }

    protected abstract List<String> columnNames();
    protected abstract Map<String, Class<?>> columnTypes();
    @Override
    public int getColumnSize(){
        return columnTypes().size();
    }
    @Override
    public List<String> getColumnNames(){
        return columnNames();
    }
    @Override
    public Map<String, Class<?>> getColumnTypes(){
        return columnTypes();
    }
    @Override
    public Optional<TblColumnSchema<?>> findColumnSchema(String columnName){
        int index = columnNames().indexOf(columnName);
        if(index == -1){
            return Optional.empty();
        }
        return Optional.ofNullable(getColumnSchema(index));
    }

    public TblColumnSchema<?> getColumnSchema(int index){
        String columnName = columnNames().get(index);
        Class<?> columnType = columnTypes().get(columnName);
        return TblColumnSchema.of(columnName, columnType);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        TblSchemaImplement that = (TblSchemaImplement) o;
        return columnNames().equals(that.columnNames()) && columnTypes().equals(that.columnTypes());
    }

}
