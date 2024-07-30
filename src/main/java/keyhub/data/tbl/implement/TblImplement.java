package keyhub.data.tbl.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;
import java.util.Map;

public abstract class TblImplement implements Tbl {
    protected final TblSchema schema;

    protected TblImplement(TblSchema schema) {
        this.schema = schema;
    }

    public static Tbl of(List<Map<String, Object>> rowMapList) {
        Map<String, Object> firstRowMap = rowMapList.getFirst();
        List<TblColumnSchema> keyMap = firstRowMap.entrySet().stream()
                .map(entry -> (TblColumnSchema)(TblColumnSchema.of(entry.getKey(), entry.getValue().getClass())))
                .toList();
        TblSchema schema = TblSchema.of(keyMap);
        TblBuilder builder = TblBuilder.forRowSet(schema);
        for(Map<String, Object> rowMap : rowMapList) {
            List<Object> row = keyMap.stream()
                    .map(columnSchema -> rowMap.getOrDefault(columnSchema.getColumnName(), null))
                    .toList();
            builder.addRow(row);
        }
        return builder.build();
    }

    public static Tbl of(Map<String, List<Object>> columnListMap){
        List<TblColumnSchema> keyMap = columnListMap.entrySet().stream()
                .map(entry -> (TblColumnSchema)(TblColumnSchema.of(entry.getKey(), entry.getValue().getFirst().getClass())))
                .toList();
        TblSchema schema = TblSchema.of(keyMap);
        TblBuilder builder = TblBuilder.forRowSet(schema);
        for(int i = 0; i < columnListMap.get(keyMap.getFirst().getColumnName()).size(); i++) {
            int finalI = i;
            List<Object> row = keyMap.stream()
                    .map(columnSchema -> columnListMap.get(columnSchema.getColumnName()).get(finalI))
                    .toList();
            builder.addRow(row);
        }
        return builder.build();
    }

    @Override
    public TblColumnSchema<?> getColumnSchema(int index) {
        return schema.getColumnSchema(index);
    }

    @Override
    public TblSchema getSchema() {
        return this.schema;
    }

    @Override
    public int getColumnSize() {
        return schema.getColumnSize();
    }

    @Override
    public String getColumn(int index) {
        return schema.getColumnNames().get(index);
    }

    @Override
    public List<String> getColumns() {
        return schema.getColumnNames();
    }

    @Override
    public Class<?> getColumnType(int index) {
        return schema.getColumnTypes().get(schema.getColumnNames().get(index));
    }

    @Override
    public Map<String, Class<?>> getColumnTypes() {
        return schema.getColumnTypes();
    }

}
