/*
 * MIT License
 *
 * Copyright (c) 2024 KH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package keyhub.data.tbl.stream.join;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;
import keyhub.data.tbl.schema.TblSchemaValue;

import java.util.ArrayList;
import java.util.List;

public abstract class TblJoinImplement implements TblJoin {
    protected final Tbl left;
    protected final Tbl right;
    protected final List<List<Integer>> selectColumns;
    protected final List<List<Integer>> joinColumns;


    public TblJoinImplement(Tbl left, Tbl right) {
        this.left = left;
        this.right = right;
        this.selectColumns = new ArrayList<>();
        List<Integer> leftSelectColumns = new ArrayList<>();
        this.selectColumns.add(leftSelectColumns);
        List<Integer> rightSelectColumns = new ArrayList<>();
        this.selectColumns.add(rightSelectColumns);
        this.joinColumns = new ArrayList<>();
    }

    @Override
    public TblJoin on(String key){
        return on(key, key);
    }

    @Override
    public TblJoin on(String leftKey, String rightKey){
        int leftIndex = getColumnIndexFromLeft(leftKey);
        if(leftIndex == -1){
            throw new IllegalArgumentException("Key not found");
        }
        int rightIndex = getColumnIndexFromRight(rightKey);
        if(rightIndex == -1){
            throw new IllegalArgumentException("Key not found");
        }
        List<Integer> joinColumn = List.of(leftIndex, rightIndex);
        this.joinColumns.add(joinColumn);
        return this;
    }

    @Override
    public Tbl toTbl() {
        computePreProcess();
        List<List<Object>> rawRows = computeJoinRawResult();
        TblSchema schema = computeJoinSchema();
        List<List<Object>> rows = computeJoinData(rawRows);
        return Tbl.of(schema, rows);
    }
    protected void computePreProcess(){
        // Do nothing
    }
    protected abstract List<List<Object>> computeJoinRawResult();
    protected boolean isJoinedRow(TblRow leftRow, TblRow rightRow){
        return this.joinColumns.stream().allMatch(pair -> {
            int leftIndex = pair.get(0);
            int rightIndex = pair.get(1);
            return leftRow.getCell(leftIndex)
                    .equals(rightRow.getCell(rightIndex));
        });
    }
    protected TblSchema computeJoinSchema(){
        TblSchemaValue.TblSchemaValueBuilder builder = TblSchemaValue.builder();
        selectColumns.get(0).forEach(index -> {
            TblColumnSchema<?> columnSchema = left.getColumnSchema(index);
            builder.addColumn(columnSchema);
        });
        selectColumns.get(1).forEach(index -> {
            TblColumnSchema<?> columnSchema = right.getColumnSchema(index);
            builder.addColumn(columnSchema);
        });
        return builder.build();
    }
    protected List<List<Object>> computeJoinData(List<List<Object>> rawResult){
        List<List<Object>> rows = new ArrayList<>();
        for(List<Object> row : rawResult){
            List<Object> newRow = new ArrayList<>();
            for(int k = 0; k < this.selectColumns.get(0).size() + this.selectColumns.get(1).size(); k++){
                newRow.add(null);
            }
            selectColumns.get(0).forEach(index -> newRow.set(index, row.get(index)));
            for(int j = 0; j < selectColumns.get(1).size(); j++){
                newRow.set(selectColumns.get(0).size() + j, row.get(selectColumns.get(1).get(j) + selectColumns.get(0).size()));
            }
            rows.add(newRow);
        }
        return rows;
    }

    @Override
    public TblJoin selectAll(){
        selectAllFromLeft();
        selectAllFromRight();
        return this;
    }
    @Override
    public TblJoin selectFromLeft(String column){
        int index = getColumnIndexFromLeft(column);
        if(index == -1){
            throw new IllegalArgumentException("Left Key not found");
        }
        this.selectColumns.getFirst().add(index);
        return this;
    }
    @Override
    public TblJoin selectFromLeft(String... columns){
        for(String column : columns){
            selectFromLeft(column);
        }
        return this;
    }
    @Override
    public TblJoin selectAllFromLeft(){
        this.selectColumns.getFirst().clear();
        for(int i = 0; i < left.getColumns().size(); i++){
            this.selectColumns.getFirst().add(i);
        }
        return this;
    }
    @Override
    public TblJoin selectFromRight(String column){
        int index = getColumnIndexFromRight(column);
        if(index == -1){
            throw new IllegalArgumentException("Right Key not found");
        }
        this.selectColumns.get(1).add(index);
        return this;
    }
    @Override
    public TblJoin selectFromRight(String... columns){
        for(String column : columns){
            selectFromRight(column);
        }
        return this;
    }
    @Override
    public TblJoin selectAllFromRight(){
        this.selectColumns.get(1).clear();
        for(int i = 0; i < right.getColumns().size(); i++){
            this.selectColumns.get(1).add(i);
        }
        return this;
    }

    @Override
    public int getColumnIndexFromLeft(String column){
        return this.left.getColumnIndex(column);
    }
    @Override
    public int getColumnIndexFromRight(String column){
        return this.right.getColumnIndex(column);
    }


}