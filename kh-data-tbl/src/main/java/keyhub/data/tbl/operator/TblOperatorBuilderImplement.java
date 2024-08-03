package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;

public abstract class TblOperatorBuilderImplement implements TblOperatorBuilder{
    protected Tbl tbl;
    protected TblOperatorType operator;
    protected String column;
    protected Object value;

    @Override
    public TblOperatorBuilder tbl(Tbl tbl){
        this.tbl = tbl;
        return this;
    }
    @Override
    public TblOperatorBuilder operator(TblOperatorType operator){
        this.operator = operator;
        return this;
    }
    @Override
    public TblOperatorBuilder column(String column){
        this.column = column;
        return this;
    }
    @Override
    public TblOperatorBuilder value(Object value){
        this.value = value;
        return this;
    }
}
