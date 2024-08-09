package keyhub.order.domain;

import keyhub.order.domain.dto.OrderDetailView;
import keyhub.order.domain.dto.OrderSimpleView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderReader orderReader;

    @Transactional(readOnly = true)
    public List<OrderDetailView> findOrderedDetailViewList(String userId) {
        return orderReader.findOrderedDetailViewList(userId);
    }

    @Transactional(readOnly = true)
    public List<OrderSimpleView> findOrderedSimpleViewList(String userId) {
        return orderReader.findOrderedSimpleViewList(userId);
    }
}
