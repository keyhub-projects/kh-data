package keyhub.data;

public interface DataValue {
    public <T extends DataVariable> T toVariable();
}
