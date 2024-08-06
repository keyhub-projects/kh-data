package keyhub.order.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "orders")
@Entity
public class Order {
    @Id
    Long id;
    String userId;

    public static Order of(Long id, String userId) {
        Order order = new Order();
        order.id = id;
        order.userId = userId;
        return order;
    }
}
