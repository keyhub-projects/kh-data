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

package keyhub.data.tbl.stream;

import keyhub.data.tbl.Tbl;
import keyhub.data.function.RowPredicate;
import keyhub.data.function.CellSelector;
import keyhub.data.cell.Cell;
import keyhub.data.row.Row;
import keyhub.data.column.Column;
import keyhub.data.schema.Schema;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.*;

public class TblStreamImplement implements TblStream {
    private Stream<Row> rowStream;

    public TblStreamImplement(Stream<Row> rowStream) {
        this.rowStream = rowStream;
    }
    public static TblStream from(Stream<Row> rowStream) {
        return new TblStreamImplement(rowStream);
    }

    @Override
    public Schema getSchema() {
        return rowStream.findFirst().orElseThrow().getSchema();
    }

    @Override
    public Tbl toTbl() {
        try{
            List<Row> rows = rowStream.toList();
            return Tbl.builder(rows.getFirst().getSchema())
                    .addRows(rows)
                    .build();
        }finally {
            close();
        }
    }

    @Override
    public TblStream select(String... columnNames) {
        rowStream = rowStream.map(row -> {
            List<Cell> cells = Stream.of(columnNames)
                    .map(columnName -> row.findCell(columnName).orElseThrow())
                    .collect(Collectors.toList());
            return Row.of(cells);
        });
        return this;
    }

    @Override
    public TblStream select(Column... columns) {
        rowStream = rowStream.map(row -> {
            Schema schema = Schema.from(List.of(columns));
            List<Cell> cells = Stream.of(columns)
                    .map(column -> row.findCell(column.getColumnName()).orElseThrow())
                    .collect(Collectors.toList());
            return Row.of(cells);
        });
        return this;
    }

    @Override
    public TblStream select(CellSelector... selector) {
        rowStream = rowStream.map(row -> {
            List<Cell> cells = Stream.of(selector)
                    .map(s -> s.apply(row))
                    .collect(Collectors.toList());
            return Row.of(cells);
        });
        return this;
    }

    @Override
    public TblStream where(RowPredicate filter) {
        rowStream = rowStream.filter(filter);
        return this;
    }

    public Iterator<Row> iterator() {
        return rowStream.iterator();
    }

    public Spliterator<Row> spliterator() {
        return rowStream.spliterator();
    }

    public boolean isParallel() {
        return rowStream.isParallel();
    }

    public TblStream sequential() {
        rowStream.sequential();
        return this;
    }

    public TblStream parallel() {
        rowStream.parallel();
        return this;
    }

    public TblStream unordered() {
        rowStream = rowStream.unordered();
        return this;
    }

    public TblStream onClose(Runnable closeHandler) {
        rowStream = rowStream.onClose(closeHandler);
        return this;
    }

    public void close() {
        rowStream.close();
    }
}
