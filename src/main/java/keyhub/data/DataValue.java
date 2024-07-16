package keyhub.data;

public interface DataValue {
    <T extends DataVariable> T toVariable();
}
