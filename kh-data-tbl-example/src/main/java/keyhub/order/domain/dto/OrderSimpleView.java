package keyhub.order.domain.dto;

import java.util.Map;

public record OrderSimpleView (
        Long id
){
    public static OrderSimpleView fromRowMap(Map<String, Object> stringObjectMap) {
        return new OrderSimpleView(
                (Long) stringObjectMap.get("id")
        );
    }
}
