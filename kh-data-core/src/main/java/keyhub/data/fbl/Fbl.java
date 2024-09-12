package keyhub.data.fbl;

import keyhub.data.column.Column;
import keyhub.data.function.CellSelector;
import keyhub.data.function.RowPredicate;
import keyhub.data.row.Row;
import keyhub.data.schema.Schema;
import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.stream.TblStream;

import java.util.stream.Stream;

public interface Fbl extends Stream<Row> {
    Schema getSchema();
    Tbl toTbl();
    TblStream select(String... columns);
    TblStream select(Column<?>... columns);
    TblStream select(CellSelector... selector);
    TblStream where(RowPredicate filter);
}
