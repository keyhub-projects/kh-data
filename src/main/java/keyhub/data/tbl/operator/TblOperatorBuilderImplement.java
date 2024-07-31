package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.implement.*;

public class TblOperatorBuilderImplement implements TblOperatorBuilder{
    private Tbl tbl;
    private TblOperatorType operator;
    private String column;
    private Object value;

    public static TblOperatorBuilder of(){
        return new TblOperatorBuilderImplement();
    }

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

    @Override
    public Tbl build(){
        if(this.operator == null){
            throw new IllegalArgumentException("operator is null");
        }else if(this.tbl == null) {
            throw new IllegalArgumentException("tbl is null");
        }else if(this.column == null){
            throw new IllegalArgumentException("column is null");
        }

        TblOperator operator = switch (this.operator) {
            case GREATER_THAN -> GreaterThanOperator.of(this.tbl, this.column, this.value);
            case LESS_THAN -> LessThanOperator.of(this.tbl, this.column, this.value);
            case GREATER_THAN_OR_EQUAL -> GreaterThanOrEqualOperator.of(this.tbl, this.column, this.value);
            case LESS_THAN_OR_EQUAL -> LessThanOrEqualOperator.of(this.tbl, this.column, this.value);
            case EQUAL -> EqualOperator.of(this.tbl, this.column, this.value);
            case NOT_EQUAL -> NotEqualOperator.of(this.tbl, this.column, this.value);
            // 정규식..
            case LIKE -> LikeOperator.of(this.tbl, this.column, this.value);
            // 집합..
            case IN -> InOperator.of(this.tbl, this.column, this.value);
            case NOT_IN -> NotInOperator.of(this.tbl, this.column);
            // 2개
            case IS_NULL -> IsNullOperator.of(this.tbl, this.column);
            case IS_NOT_NULL -> IsNotNullOperator.of(this.tbl, this.column);
        };

        return operator.getResult();
    }
}
