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

package keyhub.data.tbl.filter.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.filter.TblFilter;
import keyhub.data.tbl.filter.TblFilterImplement;
import keyhub.data.tbl.schema.TblSchema;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InFilter extends TblFilterImplement {
    private final TblSchema schema;
    private final List<List<Object>> rows;

    public InFilter(TblSchema schema, List<List<Object>> rows) {
        this.schema = schema;
        this.rows = rows;
    }

    public static TblFilter of(Tbl tbl, String column, Object value) {
        TblSchema originSchema = tbl.getSchema();
        if(!originSchema.contains(column)){
            throw new IllegalArgumentException("Column not found");
        }
        List<List<Object>> originData = tbl.getRawRows();
        List<List<Object>> filtered = originData.stream().filter(row -> {
            int index = originSchema.getColumnIndex(column);
            Optional<Object> cell = Optional.ofNullable(row.get(index));
            if(cell.isEmpty()){
                return false;
            }
            var cellValue = cell.get();
            return switch (value){
                case List list -> list.contains(cellValue);
                case Object[] array -> Arrays.asList(array).contains(cellValue);
                default -> false;
            };
        }).toList();
        return new InFilter(originSchema, filtered);
    }
    
    @Override
    public List<List<Object>> rows() {
        return this.rows;
    }

    @Override
    public TblSchema schema() {
        return this.schema;
    }
}