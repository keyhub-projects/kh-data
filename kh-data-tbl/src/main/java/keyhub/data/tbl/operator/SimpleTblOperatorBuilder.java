package keyhub.data.tbl.operator;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.operator.implement.*;

public class SimpleTblOperatorBuilder extends TblOperatorBuilderImplement{
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
            case IS_NULL -> IsNullOperator.of(this.tbl, this.column);
            case IS_NOT_NULL -> IsNotNullOperator.of(this.tbl, this.column);
            case EQUAL -> EqualOperator.of(this.tbl, this.column, this.value);
            case NOT_EQUAL -> NotEqualOperator.of(this.tbl, this.column, this.value);
            case GREATER_THAN -> GreaterThanOperator.of(this.tbl, this.column, this.value);
            case LESS_THAN -> LessThanOperator.of(this.tbl, this.column, this.value);
            case GREATER_THAN_OR_EQUAL -> GreaterThanOrEqualOperator.of(this.tbl, this.column, this.value);
            case LESS_THAN_OR_EQUAL -> LessThanOrEqualOperator.of(this.tbl, this.column, this.value);
            case IN -> InOperator.of(this.tbl, this.column, this.value);
            case NOT_IN -> NotInOperator.of(this.tbl, this.column, this.value);
            case LIKE -> LikeOperator.of(this.tbl, this.column, this.value);
            default -> throw new IllegalArgumentException("operator is not supported");
        };

        return operator.getResult();
    }
}
