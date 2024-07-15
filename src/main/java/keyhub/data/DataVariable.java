package keyhub.data;

public interface DataVariable {
    public <T extends DataValue> T toValue();
}
