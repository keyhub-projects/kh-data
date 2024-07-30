package keyhub.data.tbl.schema;

import keyhub.data.DataObject;

public interface TblColumnSchema<T> extends DataObject {
    static TblColumnSchema<?> of(String columnName, Class<?> columnType) {
        return TblColumnSchemaImplement.of(columnName, columnType);
    }
    String getColumnName();
    Class<T> getColumnType();
    @Override
    boolean equals(Object o);
}
