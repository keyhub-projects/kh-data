package keyhub.common.lib.pg;

public record PgView(
    Long pgId,
    String pgState,
    Long orderId
) {
}
