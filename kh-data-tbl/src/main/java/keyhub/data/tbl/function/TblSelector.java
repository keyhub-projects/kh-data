package keyhub.data.tbl.function;

import keyhub.data.tbl.row.TblRow;

@FunctionalInterface
public interface TblSelector extends TblFunction<TblRow> {
    TblRow apply(TblRow row);
    default TblSelector column(String columnName) {
        return null;
    }
    default TblSelector column(int columnIndex) {
        return null;
    }
    default TblSelector as(String alias) {
        return null;
    }
}
