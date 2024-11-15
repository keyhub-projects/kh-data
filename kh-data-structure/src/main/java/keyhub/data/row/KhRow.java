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

import keyhub.data.cell.KhCell;
import keyhub.data.column.KhColumn;
import keyhub.data.schema.KhSchema;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface KhRow extends Iterable<KhCell> {
    static KhRow of(KhSchema schema, Object... values) {
        return KhRowImplement.of(schema, Arrays.stream(values).toList());
    }
    static KhRow of(KhCell... cells) {
        return KhRowImplement.of(cells);
    }
    static KhRow of(List<KhCell> cells) {
        return KhRowImplement.of(cells);
    }
    static KhRow of(KhSchema schema, List<Object> values) {
        return KhRowImplement.of(schema, values);
    }

    KhSchema getSchema();
    List<Object> toList();
    <T> Optional<KhCell<T>> findCell(String columnName);
    <T> KhCell<T> getCell(int columnIndex);

    List<KhCell> getCells();


    KhRow select(String[] columns);
    KhRow select(KhColumn[] columns);
}
