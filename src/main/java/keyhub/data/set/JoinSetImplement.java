package keyhub.data.set;

import keyhub.data.JoinSet;
import keyhub.data.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JoinSetImplement implements JoinSet {
    protected final DataSet left;
    protected final DataSet right;
    protected final List<List<Integer>> selectColumns;
    protected final List<List<Integer>> joinColumns = new ArrayList<>();


    public JoinSetImplement(DataSet left, DataSet right) {
        this.left = left;
        this.right = right;
        this.selectColumns = new ArrayList<>();
        List<Integer> leftSelectColumns = new ArrayList<>();
        this.selectColumns.add(leftSelectColumns);
        List<Integer> rightSelectColumns = new ArrayList<>();
        this.selectColumns.add(rightSelectColumns);
    }

    @Override
    public JoinSet on(String key){
        return on(key, key);
    }

    @Override
    public JoinSet on(String leftKey, String rightKey){
        Integer leftIndex = findColumnIndexFromLeft(leftKey)
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
        Integer rightIndex = findColumnIndexFromRight(rightKey)
                .orElseThrow(() -> new IllegalArgumentException("Key not found"));
        List<Integer> joinColumn = new ArrayList<>();
        joinColumn.add(leftIndex);
        joinColumn.add(rightIndex);
        this.joinColumns.add(joinColumn);
        return this;
    }

    @Override
    public DataSet toDataSet() {
        computePreProcess();
        DataSet rawResult = computeJoinRawResult();
        List<String> columns = computeJoinColumn();
        return computeJoinData(columns, rawResult);
    }
    protected void computePreProcess(){
        // Do nothing
    }
    protected abstract DataSet computeJoinRawResult();
    protected boolean isJoinedRow(List<Object> leftRow, List<Object> rightRow){
        return this.joinColumns.stream().allMatch(pair -> {
            int leftIndex = pair.get(0);
            int rightIndex = pair.get(1);
            return leftRow.get(leftIndex)
                    .equals(rightRow.get(rightIndex));
        });
    }
    protected List<String> computeJoinColumn(){
        List<String> columns = new ArrayList<>();
        selectColumns.get(0).forEach(index -> columns.add(left.getColumn(index)));
        selectColumns.get(1).forEach(index -> columns.add(right.getColumn(index)));
        return columns;
    }
    protected DataSet computeJoinData(List<String> columns, DataSet rawResult){
        DataSet dataSet = DataSet.of(columns);
        for(List<Object> row : rawResult.getRows()){
            List<Object> newRow = new ArrayList<>();
            for(int k = 0; k < this.selectColumns.get(0).size() + this.selectColumns.get(1).size(); k++){
                newRow.add(null);
            }
            selectColumns.get(0).forEach(index -> newRow.set(index, row.get(index)));
            selectColumns.get(1).forEach(index -> newRow.set(index + this.selectColumns.get(0).size(), row.get(index + this.selectColumns.get(0).size())));
            dataSet.addRow(newRow);
        }
        return dataSet;
    }

    @Override
    public JoinSet selectAll(){
        selectAllFromLeft();
        selectAllFromRight();
        return this;
    }
    @Override
    public JoinSet selectFromLeft(String column){
        int index = findColumnIndexFromLeft(column)
                .orElseThrow(() -> new IllegalArgumentException("Left Key not found"));
        this.selectColumns.get(0).add(index);
        return this;
    }
    @Override
    public JoinSet selectFromLeft(String... columns){
        for(String column : columns){
            selectFromLeft(column);
        }
        return this;
    }
    @Override
    public JoinSet selectAllFromLeft(){
        this.selectColumns.get(0).clear();
        for(int i = 0; i < left.getColumns().size(); i++){
            this.selectColumns.get(0).add(i);
        }
        return this;
    }
    @Override
    public JoinSet selectFromRight(String column){
        int index = findColumnIndexFromRight(column)
                .orElseThrow(() -> new IllegalArgumentException("Right Key not found"));
        this.selectColumns.get(1).add(index);
        return this;
    }
    @Override
    public JoinSet selectFromRight(String... columns){
        for(String column : columns){
            selectFromRight(column);
        }
        return this;
    }
    @Override
    public JoinSet selectAllFromRight(){
        this.selectColumns.get(1).clear();
        for(int i = 0; i < right.getColumns().size(); i++){
            this.selectColumns.get(1).add(i);
        }
        return this;
    }

    @Override
    public Optional<Integer> findColumnIndexFromLeft(String column){
        return this.left.findColumnIndex(column);
    }
    @Override
    public Optional<Integer> findColumnIndexFromRight(String column){
        return this.right.findColumnIndex(column);
    }

}