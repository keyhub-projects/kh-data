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

package keyhub.data.tbl.query;

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

public class TblQueryFactoryImplement implements TblQueryFactory {
    private Stream<Row> rowStream;

    public TblQueryFactoryImplement(Stream<Row> rowStream) {
        this.rowStream = rowStream;
    }
    public static TblQueryFactory from(Stream<Row> rowStream) {
        return new TblQueryFactoryImplement(rowStream);
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
    public TblQueryFactory select(String... columnNames) {
        rowStream = rowStream.map(row -> {
            List<Cell> cells = Stream.of(columnNames)
                    .map(columnName -> row.findCell(columnName).orElseThrow())
                    .collect(Collectors.toList());
            return Row.of(cells);
        });
        return this;
    }

    @Override
    public TblQueryFactory select(Column... columns) {
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
    public TblQueryFactory select(CellSelector... selector) {
        rowStream = rowStream.map(row -> {
            List<Cell> cells = Stream.of(selector)
                    .map(s -> s.apply(row))
                    .collect(Collectors.toList());
            return Row.of(cells);
        });
        return this;
    }

    @Override
    public TblQueryFactory where(RowPredicate filter) {
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

    public TblQueryFactory sequential() {
        rowStream.sequential();
        return this;
    }

    public TblQueryFactory parallel() {
        rowStream.parallel();
        return this;
    }

    public TblQueryFactory unordered() {
        rowStream = rowStream.unordered();
        return this;
    }

    public TblQueryFactory onClose(Runnable closeHandler) {
        rowStream = rowStream.onClose(closeHandler);
        return this;
    }

    public void close() {
        rowStream.close();
    }
}
