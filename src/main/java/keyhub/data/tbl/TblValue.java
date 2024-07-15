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


    public TblValue(List<String> columns, List<List<Object>> data) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = data;
        clearWhere();
        selectAll();
    }

    public TblValue(List<String> columns) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = new ArrayList<>();
        selectAll();
    }

    private TblValue(TblValueBuilder builder) {
        this.columns = builder.columns;
        this.data = builder.data;
        this.resultColumns = builder.resultColumns;
        this.resultRows = builder.resultRows;
        clearWhere();
        selectAll();
    }

    @Override
    public TblVariable toVariable() {
        return new TblVariable(columns, data);
    }

    // builder
    public static class TblValueBuilder {
        private final List<String> columns = new ArrayList<>();
        private final List<List<Object>> data = new ArrayList<>();
        private final List<Integer> resultColumns = new ArrayList<>();
        private final List<Integer> resultRows = new ArrayList<>();

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
            this.data.add(row.stream().toList());
            return this;
        }

        public TblValueBuilder addResultColumns(List<Integer> resultColumns) {
            this.resultColumns.addAll(resultColumns.stream().toList());
            return this;
        }

        public TblValueBuilder addResultColumn(Integer resultColumn) {
            this.resultColumns.add(resultColumn);
            return this;
        }

        public TblValueBuilder resultRows(List<Integer> resultRows) {
            this.resultRows.addAll(resultRows.stream().toList());
            return this;
        }

        public TblValueBuilder addResultRow(Integer resultRow) {
            this.resultRows.add(resultRow);
            return this;
        }

        public TblValue build() {
            return new TblValue(this);
        }
    }
}
