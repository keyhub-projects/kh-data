package keyhub.data.document;

public record KhOrder(
        String bucket,
        String fixedKey,
        String variableKey
)  {

    public int compareTo(KhOrder order) {
        return 0;
    }
}
