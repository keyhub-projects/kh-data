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

package keyhub.data.row;

import keyhub.data.cell.Cell;
import keyhub.data.column.Column;
import keyhub.data.schema.Schema;

import java.util.ArrayList;
import java.util.List;

public class RowValue extends RowImplement {
    private final Schema schema;
    private final List<Object> values;

    public RowValue(Schema schema, List<Object> values) {
        this.schema = schema;
        this.values = new ArrayList<>(values);
    }

    public RowValue(Cell... cells) {
        List<Column> columns = List.of(cells).stream().map(Cell::getColumnSchema).toList();
        this.schema = Schema.from(columns);
        this.values = List.of(cells).stream().map(Cell::getValue).toList();
    }

    public RowValue(List<Cell> cells) {
        List<Column> columns = cells.stream().map(Cell::getColumnSchema).toList();
        this.schema = Schema.from(columns);
        this.values = cells.stream().map(Cell::getValue).toList();
    }

    @Override
    protected Schema schema() {
        return this.schema;
    }
    @Override
    protected List<Object> values() {
        return this.values;
    }
}
