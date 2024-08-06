package keyhub.order.domain;

import keyhub.order.domain.dto.OrderDetailView;

import java.util.List;

public interface OrderReader {
    List<OrderDetailView> findOrderedDetailViewList(String userId);
}
