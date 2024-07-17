package keyhub.data.tbl;

import keyhub.data.DataVariable;

import java.util.ArrayList;
import java.util.List;

public class TblVariable extends TblImplement implements DataVariable {
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

    public TblVariable() {
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = new ArrayList<>();
        this.data = new ArrayList<>();
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

    public void clearSelect(){
        this.resultColumns.clear();
    }

    public void resetSelect(){
        clearSelect();
        selectAll();
    }

    public void adjustAllRows(){
        this.data().forEach(row -> {
            while(row.size() < columns().size()){
                row.add(null);
            }
        });
    }

    @Override
    public List<List<Object>> adjustRows(List<List<Object>> rows) {
        return rows.stream().map(this::adjustRow).toList();
    }

    @Override
    public TblValue toValue() {
        return new TblValue(this.columns, this.data);
    }

    public TblVariable addRow(List<Object> row){
        if(!validateRowSize(row)){
            throw new IllegalArgumentException("row size is bigger than columns size");
        }
        row = adjustRow(row);
        this.data.add(row);
        clearWhere();
        return this;
    }

    public TblVariable addRows(List<List<Object>> rows){
        if(!validateRowsSize(rows)){
            throw new IllegalArgumentException("row size is bigger than columns size");
        }
        rows = rows.stream().map(this::adjustRow).toList();
        this.data.addAll(rows);
        clearWhere();
        return this;
    }

    public TblVariable addColumn(String column){
        this.columns.add(column);
        adjustAllRows();
        resetSelect();
        return this;
    }

    public TblVariable addColumns(List<String> columns){
        this.columns.addAll(columns);
        adjustAllRows();
        resetSelect();
        return this;
    }
}
