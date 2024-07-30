package keyhub.data.tbl.implement;

import keyhub.data.DataValue;
import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;
import java.util.Map;

public class RowSetTblImplement extends TblImplement implements DataValue {
    private final List<List<Object>> data;

    public RowSetTblImplement(TblSchema schema, List<List<Object>> data) {
        super(schema);
        this.data = data;
    }

    @Override
    public int count() {
        return data.size();
    }

    @Override
    public TblRow getRow(int index) {
        return null;
    }

    @Override
    public List<TblRow> getRows() {
        return List.of();
    }

    @Override
    public TblJoin leftJoin(Tbl right) {
        return null;
    }

    @Override
    public TblJoin innerJoin(Tbl right) {
        return null;
    }

    @Override
    public Tbl select(String... columns) {
        return null;
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
}
