package keyhub.data.tbl;

import keyhub.data.tbl.join.InnerTblJoin;
import keyhub.data.tbl.join.TblJoin;
import keyhub.data.tbl.join.LeftTblJoin;

import java.lang.reflect.Field;
import java.util.*;

// TableByList
public abstract class TblImplement implements Tbl {

    protected abstract List<String> columns();
    protected abstract List<List<Object>> data();
    protected abstract List<Integer> resultColumns();
    protected abstract List<Integer> resultRows();

    static Tbl of(List<String> columns, List<List<Object>> data) {
        return new TblValue(columns, data);
    }

    static Tbl of(List<Map<String, Object>> rowMapList) {
        if(rowMapList.isEmpty()) {
            return new TblValue(List.of());
        }
        var columns = List.copyOf(rowMapList.getFirst().keySet());
        var rows = rowMapList.stream()
                .map(row -> columns.stream().map(row::get).toList())
                .toList();
        return new TblValue(columns, rows);
    }

    static Tbl of(Map<String, List<Object>> columnMapList) {
        var columns = List.copyOf(columnMapList.keySet());
        List<List<Object>> rows = new ArrayList<>();
        for(int i = 0; i < columnMapList.get(columns.getFirst()).size(); i++) {
            List<Object> row = new ArrayList<>();
            for(String column : columns) {
                row.add(columnMapList.get(column).get(i));
            }
            rows.add(row);
        }

        return new TblValue(columns, rows);
    }

    static <T> Tbl of(List<T> dtoList, Class<T> dtoClass) {
        var columns = Arrays.stream(dtoClass.getDeclaredFields()).map(Field::getName).toList();
        List<List<Object>> rows = dtoList.stream()
                .map(dto -> Arrays.stream(dtoClass.getDeclaredFields())
                        .map(field -> {
                            try {
                                field.setAccessible(true);
                                return field.get(dto);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }finally {
                                field.setAccessible(false);
                            }
                        })
                        .toList())
                .toList();
        return new TblValue(columns, rows);
    }

    static TblValue.TblValueBuilder builder() {
        return new TblValue.TblValueBuilder();
    }

    @Override
    public List<Object> adjustRow(List<Object> row){
        List<Object> result = new ArrayList<>(row);
        while(result.size() < columns().size()){
            result.add(null);
        }
        return result;
    }

    @Override
    public boolean validateRowsSize(List<List<Object>> row){
        return row.stream().allMatch(this::validateRowSize);
    }
    @Override
    public boolean validateRowSize(List<Object> row){
        return row.size() <= columns().size();
    }

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
    public TblJoin leftJoin(Tbl right){
        return LeftTblJoin.of(this.getComputed(), right.getComputed());
    }
    @Override
    public TblJoin innerJoin(Tbl right){
        return InnerTblJoin.of(this.getComputed(), right.getComputed());
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
    public Map<String, List<Object>> toColumnListMap() {
        Tbl computed = getComputed();
        return computed.getColumns().stream().collect(HashMap::new, (map, column) -> {
            int index = computed.findColumnIndex(column).orElseThrow();
            map.put(column, computed.getRows().stream().map(row -> row.get(index)).toList());
        }, HashMap::putAll);
    }


    public boolean equalsSchema(Tbl tbl){
        for(int i = 0; i < tbl.getColumns().size(); i++){
            if(tbl.getColumns().stream().anyMatch(column -> !this.getColumns().contains(column))){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }

        if(!(obj instanceof Tbl tbl)){
            return false;
        }

        if(
            tbl.getColumns().size() != this.getColumns().size()
            || tbl.getRows().size() != this.getRows().size()
        ){
            return false;
        }

        if(!this.equalsSchema(tbl)){
            return false;
        }

        for(int i = 0; i < tbl.getRows().size(); i++){
            for(int j = 0; j < tbl.getColumns().size(); j++){
                var thisColumn = this.getColumns().get(j);
                var thatColumnIndex = tbl.findColumnIndex(thisColumn).orElseThrow();
                if(!this.getRows().get(i).get(j).equals(tbl.getRows().get(i).get(thatColumnIndex))){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[")
            .append(getColumns())
            .append(getRows())
            .append("]");
        return sb.toString();
    }
}
