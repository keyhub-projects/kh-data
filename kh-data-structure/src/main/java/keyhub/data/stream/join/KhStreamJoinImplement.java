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

package keyhub.data.stream.join;

import keyhub.data.column.KhColumn;
import keyhub.data.function.KhColumnSelector;
import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import keyhub.data.schema.KhSchemaBasedStructure;
import keyhub.data.schema.KhSchemaValue;
import keyhub.data.stream.KhStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class KhStreamJoinImplement implements KhStreamJoin{
    protected final List<List<Integer>> selectColumns;
    protected final List<List<Integer>> joinColumns;

    protected KhStreamJoinImplement() {
        this.selectColumns = new ArrayList<>();
        List<Integer> leftSelectColumns = new ArrayList<>();
        this.selectColumns.add(leftSelectColumns);
        List<Integer> rightSelectColumns = new ArrayList<>();
        this.selectColumns.add(rightSelectColumns);
        this.joinColumns = new ArrayList<>();
    }

    @Override
    public KhStream toOne() {
        computePreProcess();
        KhSchema schema = computeJoinSchema();
        Stream<KhRow> rowStream = computeJoinData(schema);
        return KhStream.of(schema, rowStream);
    }

    protected void computePreProcess(){
        // Do nothing
    }
    protected KhSchema computeJoinSchema(){
        KhSchemaValue.KhSchemaValueBuilder builder = KhSchemaValue.builder();
        selectColumns.get(0).forEach(index -> {
            KhColumn<?> columnSchema = left().getColumnSchema(index);
            builder.addColumn(columnSchema);
        });
        selectColumns.get(1).forEach(index -> {
            KhColumn<?> columnSchema = right().getColumnSchema(index);
            builder.addColumn(columnSchema);
        });
        return builder.build();
    }
    protected abstract Stream<KhRow> computeJoinData(KhSchema schema);
    protected boolean isJoinedRow(KhRow leftRow, KhRow rightRow){
        return this.joinColumns.stream().allMatch(pair -> {
            int leftIndex = pair.get(0);
            int rightIndex = pair.get(1);
            return leftRow.getCell(leftIndex)
                    .equals(rightRow.getCell(rightIndex));
        });
    }

    @Override
    public KhStreamJoin on(String key) {
        return on(key, key);
    }
    @Override
    public KhStreamJoin on(String leftKey, String rightKey) {
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
    public KhStreamJoin selectAll(){
        selectAllFromLeft();
        selectAllFromRight();
        return this;
    }

    @Override
    public KhStreamJoin selectFromLeft(KhColumnSelector... selectors) {
        for(KhColumnSelector selector : selectors){
            KhColumn<?> column = selector.apply(left().getSchema());
            selectFromLeft(column.getColumnName());
        }
        return this;
    }
    @Override
    public KhStreamJoin selectFromLeft(String column) {
        int index = getColumnIndexFromLeft(column);
        if(index == -1){
            throw new IllegalArgumentException("Left Key not found");
        }
        this.selectColumns.getFirst().add(index);
        return this;
    }
    @Override
    public KhStreamJoin selectFromLeft(String... columns) {
        for (String column : columns) {
            selectFromLeft(column);
        }
        return this;
    }
    @Override
    public KhStreamJoin selectAllFromLeft() {
        this.selectColumns.getFirst().clear();
        for(int i = 0; i < left().getColumnSize(); i++){
            this.selectColumns.getFirst().add(i);
        }
        return this;
    }

    @Override
    public KhStreamJoin selectFromRight(KhColumnSelector... selectors) {
        for(KhColumnSelector selector : selectors){
            KhColumn<?> column = selector.apply(right().getSchema());
            selectFromRight(column.getColumnName());
        }
        return this;
    }
    @Override
    public KhStreamJoin selectFromRight(String column) {
        int index = getColumnIndexFromRight(column);
        if(index == -1){
            throw new IllegalArgumentException("Right Key not found");
        }
        this.selectColumns.get(1).add(index);
        return this;
    }
    @Override
    public KhStreamJoin selectFromRight(String... columns) {
        for (String column : columns) {
            selectFromRight(column);
        }
        return this;
    }
    @Override
    public KhStreamJoin selectAllFromRight(){
        this.selectColumns.get(1).clear();
        for(int i = 0; i < right().getColumnSize(); i++){
            this.selectColumns.get(1).add(i);
        }
        return this;
    }

    protected abstract KhSchemaBasedStructure left();
    protected abstract KhSchemaBasedStructure right();
    @Override
    public int getColumnIndexFromLeft(String column) {
        return left().getColumnIndex(column);
    }

    @Override
    public int getColumnIndexFromRight(String column) {
        return right().getColumnIndex(column);
    }
}
