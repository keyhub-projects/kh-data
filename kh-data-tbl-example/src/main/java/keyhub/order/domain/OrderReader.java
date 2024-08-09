package keyhub.order.domain;

import keyhub.order.domain.dto.OrderDetailView;
import keyhub.order.domain.dto.OrderSimpleView;

import java.util.List;

public interface OrderReader {
    List<OrderDetailView> findOrderedDetailViewList(String userId);

    List<OrderSimpleView> findOrderedSimpleViewList(String userId);
}
