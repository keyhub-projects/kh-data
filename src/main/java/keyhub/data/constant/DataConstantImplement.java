package keyhub.data.constant;

import keyhub.data.DataConstant;

public class DataConstantImplement implements DataConstant {
    private final Object value;

    public DataConstantImplement(Object value) {
        this.value = value;
    }

    public static DataConstant of(Object value) {
        return new DataConstantImplement(value);
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if(!clazz.equals(this.value.getClass())){
            throw new ClassCastException();
        }
        return clazz.cast(this.value);
    }

}
