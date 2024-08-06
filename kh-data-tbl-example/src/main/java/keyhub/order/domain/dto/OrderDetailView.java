package keyhub.order.domain.dto;

import java.util.Map;

public record OrderDetailView (
    Long orderId,
    Long pgId,
    String pgState,
    Long wmsId,
    String wmsState
){
    public static OrderDetailView fromRowMap(Map<String, Object> stringObjectMap) {
        return new OrderDetailView(
            (Long) stringObjectMap.get("id"),
            (Long) stringObjectMap.get("pgId"),
            (String) stringObjectMap.get("pgState"),
            (Long) stringObjectMap.get("wmsId"),
            (String) stringObjectMap.get("wmsState")
        );
    }
}
