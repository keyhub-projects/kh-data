package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public abstract class TblRowImplement implements TblRow {
    protected abstract TblSchema schema();
    protected abstract List<Object> values();


}
