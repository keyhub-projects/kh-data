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

package keyhub.data.tbl.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TblSchemaValue extends TblSchemaImplement {
    private final List<String> columnNames;
    private final Map<String, Class<?>> columnTypes;

    public TblSchemaValue(){
        this.columnNames = new ArrayList<>();
        this.columnTypes = new HashMap<>();
    }

    public TblSchemaValue(List<TblColumn> schemas){
        this.columnNames = schemas.stream().map(TblColumn::getColumnName).toList();
        this.columnTypes = new HashMap<>();
        schemas.forEach(schema -> this.columnTypes.put(schema.getColumnName(), schema.getColumnType()));
    }

    public TblSchemaValue(TblSchema tblSchema) {
        this.columnNames = tblSchema.getColumnNames();
        this.columnTypes = new HashMap<>();
        tblSchema.getColumnTypes().forEach(this.columnTypes::put);
    }

    public TblSchemaValue(TblSchemaValueBuilder builder) {
        this.columnNames = builder.columnNames;
        this.columnTypes = new HashMap<>();
        this.columnNames.forEach(columnName ->
                this.columnTypes.put(columnName, builder.columnTypes.get(columnName))
        );
    }

    @Override
    protected List<String> columnNames() {
        return this.columnNames;
    }
    @Override
    protected Map<String, Class<?>> columnTypes() {
        return this.columnTypes;
    }

    public static class TblSchemaValueBuilder {
        private List<String> columnNames = new ArrayList<>();
        private Map<String, Class<?>> columnTypes = new HashMap<>();

        public TblSchemaValueBuilder addColumn(String columnName, Class<?> columnType) {
            this.columnNames.add(columnName);
            this.columnTypes.put(columnName, columnType);
            return this;
        }
        public TblSchemaValueBuilder addColumn(TblColumn<?> tblColumn) {
            this.columnNames.add(tblColumn.getColumnName());
            this.columnTypes.put(tblColumn.getColumnName(), tblColumn.getColumnType());
            return this;
        }
        public TblSchemaValueBuilder addColumns(List<TblColumn<?>> tblColumns) {
            tblColumns.forEach(this::addColumn);
            return this;
        }
        public TblSchemaValue build() {
            return new TblSchemaValue(this);
        }
    }
}
