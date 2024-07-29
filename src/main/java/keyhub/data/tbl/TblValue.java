package keyhub.data.tbl;

import keyhub.data.DataValue;

import java.util.ArrayList;
import java.util.List;

public class TblValue extends TblImplement implements DataValue {
    private final List<String> columns;
    private final List<List<Object>> data;
    private final List<Integer> resultColumns;
    private final List<Integer> resultRows;

    @Override
    protected List<String> columns() {
        return this.columns;
    }
    @Override
    protected List<List<Object>> data() {
        return this.data;
    }
    @Override
    protected List<Integer> resultColumns() {
        return this.resultColumns;
    }
    @Override
    protected List<Integer> resultRows() {
        return this.resultRows;
    }

    @Override
    public List<List<Object>> adjustRows(List<List<Object>> rows) {
        return rows.stream().map(this::adjustRow).toList();
    }

    public TblValue(List<String> columns) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = new ArrayList<>();
        selectAll();
    }

    public TblValue(List<String> columns, List<List<Object>> data) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        validateRowsSize(data);
        this.data = adjustRows(data);
        clearWhere();
        selectAll();
    }

    private TblValue(TblValueBuilder builder) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = builder.columns;
        validateRowsSize(builder.data);
        this.data = adjustRows(builder.data);
        clearWhere();
        selectAll();
    }

    // builder
    public static class TblValueBuilder {
        private final List<String> columns = new ArrayList<>();
        private final List<List<Object>> data = new ArrayList<>();

        public TblValueBuilder addColumns(List<String> columns) {
            this.columns.addAll(columns.stream().toList());
            return this;
        }

        public TblValueBuilder addColumn(String column) {
            this.columns.add(column);
            return this;
        }

        public TblValueBuilder addRows(List<List<Object>> data) {
            this.data.addAll(data.stream().map(elem->elem.stream().toList()).toList());
            return this;
        }

        public TblValueBuilder addRow(List<Object> row) {
            if(row.size() != this.columns.size()) {
                throw new IllegalArgumentException("row size is not equal to columns size");
            }
            this.data.add(row.stream().toList());
            return this;
        }

        public TblValue build() {
            return new TblValue(this);
        }
    }
}
