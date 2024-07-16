package keyhub.data;

public interface DataVariable {
    <T extends DataValue> T toValue();
}
