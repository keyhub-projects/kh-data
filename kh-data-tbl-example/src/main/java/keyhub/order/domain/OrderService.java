package keyhub.order.domain;

import keyhub.order.domain.dto.OrderDetailView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderReader orderReader;

    @Transactional(readOnly = true)
    public List<OrderDetailView> findOrderedDetailViewList(String userId) throws IllegalAccessException {
        return orderReader.findOrderedDetailViewList(userId);
    }
}
