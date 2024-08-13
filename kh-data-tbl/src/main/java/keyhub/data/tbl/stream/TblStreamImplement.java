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
import keyhub.data.tbl.function.TblPredicate;
import keyhub.data.tbl.function.TblSelector;
import keyhub.data.tbl.row.TblCell;
import keyhub.data.tbl.row.TblRow;
import keyhub.data.tbl.schema.TblColumn;
import keyhub.data.tbl.schema.TblSchema;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.*;

public class TblStreamImplement implements TblStream {
    private Stream<TblRow> rowStream;

    public TblStreamImplement(Stream<TblRow> rowStream) {
        this.rowStream = rowStream;
    }
    public static TblStream from(Stream<TblRow> rowStream) {
        return new TblStreamImplement(rowStream);
    }

    @Override
    public Tbl toTbl() {
        List<TblRow> rows = rowStream.toList();
        return Tbl.builder(rows.getFirst().getSchema())
                .addRows(rows)
                .build();
    }

    @Override
    public TblStream select(String... columnNames) {
        rowStream = rowStream.map(row -> {
            List<TblCell> cells = Stream.of(columnNames)
                    .map(columnName -> row.findCell(columnName).orElseThrow())
                    .collect(Collectors.toList());
            return TblRow.of(cells);
        });
        return this;
    }

    @Override
    public TblStream select(TblColumn... columns) {
        rowStream = rowStream.map(row -> {
            TblSchema schema = TblSchema.from(List.of(columns));
            List<TblCell> cells = Stream.of(columns)
                    .map(column -> row.findCell(column.getColumnName()).orElseThrow())
                    .collect(Collectors.toList());
            return TblRow.of(cells);
        });
        return this;
    }

    @Override
    public TblStream select(TblSelector selector) {
        rowStream = rowStream.map(selector);
        return this;
    }

    @Override
    public TblStream where(String column, TblPredicate filter) {
        rowStream = rowStream.filter(filter);
        return this;
    }

    @Override
    public Iterator<TblRow> iterator() {
        return rowStream.iterator();
    }

    @Override
    public Spliterator<TblRow> spliterator() {
        return rowStream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return rowStream.isParallel();
    }

    @Override
    public TblStream sequential() {
        rowStream.sequential();
        return this;
    }

    @Override
    public TblStream parallel() {
        rowStream.parallel();
        return this;
    }

    @Override
    public TblStream unordered() {
        rowStream = rowStream.unordered();
        return this;
    }

    @Override
    public TblStream onClose(Runnable closeHandler) {
        rowStream = rowStream.onClose(closeHandler);
        return this;
    }

    @Override
    public void close() {
        rowStream.close();
    }
}
