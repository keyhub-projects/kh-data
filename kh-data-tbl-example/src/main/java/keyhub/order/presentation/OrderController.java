package keyhub.order.presentation;

import keyhub.order.domain.OrderService;
import keyhub.order.domain.dto.OrderDetailView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{userId}")
    public List<OrderDetailView> findOrderedDetailViewList(@PathVariable String userId) {
        return orderService.findOrderedDetailViewList(userId);
    }


}
