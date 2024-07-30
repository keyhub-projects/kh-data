package keyhub.data.tbl.schema;

import keyhub.data.DataObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TblSchema extends DataObject {
    static TblSchema of(List<TblColumnSchema> tblColumnSchemas) {
        return TblSchemaImplement.of(tblColumnSchemas);
    }
    static TblSchemaValue.TblSchemaValueBuilder builder() {
        return TblSchemaImplement.builder();
    }
    int getColumnSize();
    List<String> getColumnNames();
    Map<String, Class<?>> getColumnTypes();

    Optional<TblColumnSchema> findColumnSchema(String columnName);

    TblColumnSchema<?> getColumnSchema(int index);

    @Override
    boolean equals(Object o);

    int getColumnIndex(String columnName);
}
