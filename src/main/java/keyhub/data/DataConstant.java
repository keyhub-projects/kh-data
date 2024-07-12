package keyhub.data;

import keyhub.data.constant.DataConstantImplement;

public interface DataConstant extends DataVariable {
    static DataConstant of(Object value) {
        return DataConstantImplement.of(value);
    }
    default boolean checkType(Class<?> clazz){
        return clazz.equals(DataConstant.class);
    }

    Object getValue();

    <T> T getValue(Class<T> clazz);
}
