package keyhub.order.presentation;

import keyhub.order.domain.OrderService;
import keyhub.order.domain.dto.OrderDetailView;
import keyhub.order.domain.dto.OrderSimpleView;
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

    @GetMapping("/detail/{userId}")
    public List<OrderDetailView> findOrderedSimpleViewList(@PathVariable String userId) {
        return orderService.findOrderedDetailViewList(userId);
    }

    @GetMapping("/simple/{userId}")
    public List<OrderSimpleView> findOrderedDetailViewList(@PathVariable String userId) {
        return orderService.findOrderedSimpleViewList(userId);
    }

    @GetMapping("/test")
    public List<String> test() {
        return List.of("test");
    }


}
