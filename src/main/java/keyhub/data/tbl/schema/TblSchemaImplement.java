package keyhub.data.tbl.schema;

import java.util.List;
import java.util.Map;

public abstract class TblSchemaImplement implements TblSchema{
    protected abstract List<String> columns();
    protected abstract Map<String, Class<?>> columnTypes();

}
