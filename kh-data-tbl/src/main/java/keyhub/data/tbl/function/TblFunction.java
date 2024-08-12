package keyhub.data.tbl.function;

import java.util.function.Function;

@FunctionalInterface
public interface TblFunction <TBL, R> extends Function<TBL, R> {

}
