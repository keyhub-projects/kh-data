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

package keyhub.data.table.query;

import keyhub.data.column.KhColumn;
import keyhub.data.row.KhRow;
import keyhub.data.table.KhTable;
import keyhub.data.function.KhRowPredicate;
import keyhub.data.function.KhCellSelector;
import keyhub.data.cell.KhCell;
import keyhub.data.schema.KhSchema;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.*;

public class KhTableQueryFactoryImplement implements KhTableQueryFactory {
    private Stream<KhRow> rowStream;

    public KhTableQueryFactoryImplement(Stream<KhRow> rowStream) {
        this.rowStream = rowStream;
    }
    public static KhTableQueryFactory from(Stream<KhRow> rowStream) {
        return new KhTableQueryFactoryImplement(rowStream);
    }

    @Override
    public KhSchema getSchema() {
        return rowStream.findFirst().orElseThrow().getSchema();
    }

    @Override
    public KhTable toTbl() {
        try{
            List<KhRow> rows = rowStream.toList();
            return KhTable.builder(rows.getFirst().getSchema())
                    .addRows(rows)
                    .build();
        }finally {
            close();
        }
    }

    @Override
    public KhTableQueryFactory select(String... columnNames) {
        rowStream = rowStream.map(row -> {
            List<KhCell> cells = Stream.of(columnNames)
                    .map(columnName -> row.findCell(columnName).orElseThrow())
                    .collect(Collectors.toList());
            return KhRow.of(cells);
        });
        return this;
    }

    @Override
    public KhTableQueryFactory select(KhColumn... columns) {
        rowStream = rowStream.map(row -> {
            KhSchema schema = KhSchema.from(List.of(columns));
            List<KhCell> cells = Stream.of(columns)
                    .map(column -> row.findCell(column.getColumnName()).orElseThrow())
                    .collect(Collectors.toList());
            return KhRow.of(cells);
        });
        return this;
    }

    @Override
    public KhTableQueryFactory select(KhCellSelector... selector) {
        rowStream = rowStream.map(row -> {
            List<KhCell> cells = Stream.of(selector)
                    .map(s -> s.apply(row))
                    .collect(Collectors.toList());
            return KhRow.of(cells);
        });
        return this;
    }

    @Override
    public KhTableQueryFactory where(KhRowPredicate filter) {
        rowStream = rowStream.filter(filter);
        return this;
    }

    public Iterator<KhRow> iterator() {
        return rowStream.iterator();
    }

    public Spliterator<KhRow> spliterator() {
        return rowStream.spliterator();
    }

    public boolean isParallel() {
        return rowStream.isParallel();
    }

    public KhTableQueryFactory sequential() {
        rowStream.sequential();
        return this;
    }

    public KhTableQueryFactory parallel() {
        rowStream.parallel();
        return this;
    }

    public KhTableQueryFactory unordered() {
        rowStream = rowStream.unordered();
        return this;
    }

    public KhTableQueryFactory onClose(Runnable closeHandler) {
        rowStream = rowStream.onClose(closeHandler);
        return this;
    }

    public void close() {
        rowStream.close();
    }
}
