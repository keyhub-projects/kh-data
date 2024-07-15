package keyhub.data.tbl;

import keyhub.data.DataValue;
import keyhub.data.DataVariable;

import java.util.ArrayList;
import java.util.List;

public class TblVariable extends TblImplement implements DataVariable {
    private List<String> columns;
    private List<List<Object>> data;
    private List<Integer> resultColumns;
    private List<Integer> resultRows;

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

    public TblVariable(List<String> columns, List<List<Object>> data) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = data;
        clearWhere();
        selectAll();
    }

    public TblVariable(List<String> columns) {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = new ArrayList<>();
        selectAll();
    }

    @Override
    public DataValue toValue() {
        return new TblValue(this.columns, this.data);
    }

    public TblVariable addRow(List<Object> row){
        this.data.add(row);
        clearWhere();
        return this;
    }

    public TblVariable addAllRows(List<List<Object>> rows){
        this.data.addAll(rows);
        clearWhere();
        return this;
    }

    public TblVariable addColumn(String column){
        this.columns.add(column);
        return this;
    }

    public TblVariable addColumns(List<String> columns){
        this.columns.addAll(columns);
        return this;
    }
}
