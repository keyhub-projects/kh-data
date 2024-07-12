package keyhub.data.set;

import keyhub.data.InnerJoinSet;
import keyhub.data.JoinSet;
import keyhub.data.DataSet;
import keyhub.data.LeftJoinSet;

import java.util.*;

public class DataSetImplement implements DataSet {
    private final List<String> columns;
    private final List<List<Object>> data;
    private final List<Integer> resultColumns;
    private final List<Integer> resultRows;

    public DataSetImplement(List<String> columns, List<List<Object>> data){
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = data;
        clearWhere();
        selectAll();
    }
    public DataSetImplement(List<String> columns){
        this.resultColumns = new ArrayList<>();
        this.resultRows = new ArrayList<>();
        this.columns = columns;
        this.data = new ArrayList<>();
        selectAll();
    }

    @Override
    public DataSet addRow(List<Object> row){
        this.data.add(row);
        clearWhere();
        return this;
    }
    @Override
    public DataSet addAllRows(List<List<Object>> rows){
        this.data.addAll(rows);
        clearWhere();
        return this;
    }
    @Override
    public int size(){
        return this.data.size();
    }
    @Override
    public List<Object> getRow(int index){
        if(index < 0 || index >= this.resultRows.size()){
            throw new IndexOutOfBoundsException();
        }
        int rowIndex = this.resultRows.get(index);
        return this.resultColumns.stream().map(this.data.get(rowIndex)::get).toList();
    }
    @Override
    public List<List<Object>> getRows(){
        return this.resultRows.stream().map(this.data::get)
                .map(row -> this.resultColumns.stream().map(row::get).toList())
                .toList();
    }

    @Override
    public List<String> getColumns() {
        return this.resultColumns.stream().map(this.columns::get).toList();
    }
    @Override
    public String getColumn(int index){
        return this.columns.get(index);
    }
    @Override
    public int getColumnSize(){
        return this.columns.size();
    }
    @Override
    public Optional<Integer> findColumnIndex(String column){
        for(int i = 0; i < this.columns.size(); i++){
            if(this.columns.get(i).equals(column)){
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    @Override
    public JoinSet leftJoin(DataSet right){
        return LeftJoinSet.of(this.getComputed(), right.getComputed());
    }
    @Override
    public JoinSet innerJoin(DataSet right){
        return InnerJoinSet.of(this.getComputed(), right.getComputed());
    }

    @Override
    public DataSet select(String... columns) {
        resultColumns.clear();
        for(String column : columns){
            int index = this.columns.indexOf(column);
            if(index == -1){
                throw new IllegalArgumentException("Column not found");
            }
            if(!resultColumns.contains(index)){
                resultColumns.add(index);
            }
        }
        return this;
    }
    @Override
    public DataSet selectAll() {
        resultColumns.clear();
        for(int i = 0; i < this.columns.size(); i++){
            resultColumns.add(i);
        }
        return this;
    }

    @Override
    public DataSet where(String column, String operator, Object value) {
        List<Integer> indexes = new ArrayList<>(this.resultRows);
        this.resultRows.clear();
        indexes.stream().filter(index -> {
            int columnIndex = this.columns.indexOf(column);
            Object rowValue = this.data.get(index).get(columnIndex);
            return switch (operator) {
                case "==" -> rowValue.equals(value);
                case "!=" -> !rowValue.equals(value);
                case ">" -> (int) rowValue > (int) value;
                case "<" -> (int) rowValue < (int) value;
                case ">=" -> (int) rowValue >= (int) value;
                case "<=" -> (int) rowValue <= (int) value;
                default -> false;
            };
        }).forEach(this.resultRows::add);
        return this;
    }
    @Override
    public DataSet clearWhere(){
        this.resultRows.clear();
        for(int i = 0; i < this.data.size(); i++){
            this.resultRows.add(i);
        }
        return this;
    }

    @Override
    public DataSet getComputed(){
        return DataSet.of(
                getColumns(),
                getRows()
        );
    }

    @Override
    public List<Map<String, Object>> toRowMapList() {
        DataSet computed = getComputed();
        return computed.getRows().stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            for(int i = 0; i < computed.getColumnSize(); i++){
                map.put(computed.getColumn(i), row.get(i));
            }
            return map;
        }).toList();
    }
    @Override
    public Map<String, List<Object>> toColumnMapList() {
        DataSet computed = getComputed();
        return computed.getColumns().stream().collect(HashMap::new, (map, column) -> {
            int index = computed.findColumnIndex(column).orElseThrow();
            map.put(column, computed.getRows().stream().map(row -> row.get(index)).toList());
        }, HashMap::putAll);
    }
}
