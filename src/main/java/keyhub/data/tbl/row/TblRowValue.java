package keyhub.data.tbl.row;

import keyhub.data.DataValue;
import keyhub.data.tbl.schema.TblSchema;

import java.util.List;

public class TblRowValue extends TblRowImplement implements DataValue {
    private final TblSchema schema;
    private final List<Object> values;

    public TblRowValue(TblRowValueBuilder builder) {
        this.schema = builder.schema;
        this.values = builder.values;
    }

    protected TblSchema schema() {
        return this.schema;
    }
    protected List<Object> values() {
        return this.values;
    }


    public static class TblRowValueBuilder {
        private TblSchema schema;
        private List<Object> values;

        public TblRowValueBuilder schema(TblSchema schema) {
            this.schema = schema;
            return this;
        }
        // todo


        public TblRowValue build() {
            return new TblRowValue(this);
        }
    }
}
