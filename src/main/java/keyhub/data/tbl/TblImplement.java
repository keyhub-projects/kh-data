package keyhub.data.tbl;

import keyhub.data.join.InnerJoinSet;
import keyhub.data.join.JoinSet;
import keyhub.data.join.LeftJoinSet;

import java.util.*;

// TableByList
public abstract class TblImplement implements Tbl {

    protected abstract List<String> columns();
    protected abstract List<List<Object>> data();
    protected abstract List<Integer> resultColumns();
    protected abstract List<Integer> resultRows();

    @Override
    public int size(){
        return data().size();
    }
    @Override
    public List<Object> getRow(int index){
        if(index < 0 || index >= resultRows().size()){
            throw new IndexOutOfBoundsException();
        }
        int rowIndex = resultRows().get(index);
        return resultColumns().stream().map(data().get(rowIndex)::get).toList();
    }
    @Override
    public List<List<Object>> getRows(){
        return resultRows().stream().map(data()::get)
                .map(row -> resultColumns().stream().map(row::get).toList())
                .toList();
    }
    @Override
    public List<String> getColumns() {
        return resultColumns().stream().map(columns()::get).toList();
    }
    @Override
    public String getColumn(int index){
        return columns().get(index);
    }
    @Override
    public int getColumnSize(){
        return columns().size();
    }
    @Override
    public Optional<Integer> findColumnIndex(String column){
        for(int i = 0; i < columns().size(); i++){
            if(columns().get(i).equals(column)){
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    @Override
    public JoinSet leftJoin(Tbl right){
        return LeftJoinSet.of(this.getComputed(), right.getComputed());
    }
    @Override
    public JoinSet innerJoin(Tbl right){
        return InnerJoinSet.of(this.getComputed(), right.getComputed());
    }

    @Override
    public Tbl select(String... columns) {
        resultColumns().clear();
        for(String column : columns){
            int index = columns().indexOf(column);
            if(index == -1){
                throw new IllegalArgumentException("Column not found");
            }
            if(!resultColumns().contains(index)){
                resultColumns().add(index);
            }
        }
        return this;
    }
    @Override
    public Tbl selectAll() {
        resultColumns().clear();
        for(int i = 0; i < columns().size(); i++){
            resultColumns().add(i);
        }
        return this;
    }

    @Override
    public Tbl where(String column, String operator, Object value) {
        List<Integer> indexes = new ArrayList<>(resultRows());
        resultRows().clear();
        indexes.stream().filter(index -> {
            int columnIndex = columns().indexOf(column);
            Object rowValue = data().get(index).get(columnIndex);
            return switch (operator) {
                case "==" -> rowValue.equals(value);
                case "!=" -> !rowValue.equals(value);
                case ">" -> (int) rowValue > (int) value;
                case "<" -> (int) rowValue < (int) value;
                case ">=" -> (int) rowValue >= (int) value;
                case "<=" -> (int) rowValue <= (int) value;
                default -> false;
            };
        }).forEach(resultRows()::add);
        return this;
    }
    @Override
    public Tbl clearWhere(){
        resultRows().clear();
        for(int i = 0; i < data().size(); i++){
            resultRows().add(i);
        }
        return this;
    }

    @Override
    public Tbl getComputed(){
        return Tbl.of(
                getColumns(),
                getRows()
        );
    }

    @Override
    public List<Map<String, Object>> toRowMapList() {
        Tbl computed = getComputed();
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
        Tbl computed = getComputed();
        return computed.getColumns().stream().collect(HashMap::new, (map, column) -> {
            int index = computed.findColumnIndex(column).orElseThrow();
            map.put(column, computed.getRows().stream().map(row -> row.get(index)).toList());
        }, HashMap::putAll);
    }
}
