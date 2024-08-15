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
import keyhub.data.tbl.function.TblRowPredicate;
import keyhub.data.tbl.function.TblColumnSelector;
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
    public TblSchema getSchema() {
        return rowStream.findFirst().orElseThrow().getSchema();
    }

    @Override
    public Tbl toTbl() {
        try{
            List<TblRow> rows = rowStream.toList();
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
    public TblStream select(TblColumnSelector... selector) {
        rowStream = rowStream.map(row -> {
            List<TblCell> cells = Stream.of(selector)
                    .map(s -> s.apply(row))
                    .collect(Collectors.toList());
            return TblRow.of(cells);
        });
        return this;
    }

    @Override
    public TblStream where(TblRowPredicate filter) {
        rowStream = rowStream.filter(filter);
        return this;
    }

    public Iterator<TblRow> iterator() {
        return rowStream.iterator();
    }

    public Spliterator<TblRow> spliterator() {
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
