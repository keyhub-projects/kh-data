package keyhub.data.tbl.implement;

import keyhub.data.DataValue;
import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.join.InnerTblJoin;
import keyhub.data.tbl.join.LeftTblJoin;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColumnSetTblImplement extends TblImplement implements DataValue, ColumnSet {
    private final List<List<Object>> data;

    public ColumnSetTblImplement(TblSchema schema, List<List<Object>> data) {
        super(schema);
        this.data = data;
    }

    @Override
    public int count() {
        if(data.isEmpty())
            return 0;
        return data.getFirst().size();
    }

    @Override
    public TblRow getRow(int index) {
        List<Object> row = data.stream()
                .map(column -> column.get(index))
                .toList();
        return TblRow.of(super.schema, row);
    }

    @Override
    public List<TblRow> getRows() {
        List<TblRow> rows = new ArrayList<>();
        for(int i = 0; i < count(); i++) {
            rows.add(getRow(i));
        }
        return rows;
    }

    @Override
    public TblJoin leftJoin(Tbl right) {
        return LeftTblJoin.of(this, right);
    }

    @Override
    public TblJoin innerJoin(Tbl right) {
        return InnerTblJoin.of(this, right);
    }

    @Override
    public Tbl select(String... columns) {
        List<TblColumnSchema> columnSchemas = new ArrayList<>();
        for(String column : columns) {
            var schema = super.schema.findColumnSchema(column).orElseThrow();
            columnSchemas.add(schema);
        }
        TblSchema schema = TblSchema.of(columnSchemas);
        List<List<Object>> data = new ArrayList<>();
        for(int i = 0; i < count(); i++) {
            List<Object> originRow = this.getRow(i).toList();
            List<Object> newRow = new ArrayList<>();
            for(String column : columns) {
                int index = super.schema.getColumnNames().indexOf(column);
                newRow.add(originRow.get(index));
            }
            data.add(newRow);
        }
        return Tbl.of(schema, data);
    }

    @Override
    public Tbl selectAll() {
        return null;
    }

    @Override
    public Tbl where(String column, String operator, Object value) {
        return null;
    }

    @Override
    public List<Map<String, Object>> toRowMapList() {
        return List.of();
    }

    @Override
    public Map<String, List<Object>> toColumnListMap() {
        return Map.of();
    }

    @Override
    public RowSet toRowSet() {
        return null;
    }
}
