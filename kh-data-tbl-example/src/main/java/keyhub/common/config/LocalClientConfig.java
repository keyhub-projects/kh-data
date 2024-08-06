package keyhub.common.config;

import keyhub.common.lib.pg.PgClient;
import keyhub.common.lib.pg.PgView;
import keyhub.common.lib.wms.WmsClient;
import keyhub.common.lib.wms.WmsView;
import keyhub.order.domain.Order;
import keyhub.order.infrastructure.OrderJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LocalClientConfig {



    @Bean
    public PgClient fakePgClient() {
        return new PgClient() {
            @Override
            public List<PgView> findPgViewListByUserId(String userId) {
                return List.of(
                    new PgView(2L, "state1", 1L),
                    new PgView(4L, "state2", 2L),
                    new PgView(6L, "state3", 3L)
                );
            }
        };
    }

    @Bean
    public WmsClient fakeWmsClient() {
        return new WmsClient() {
            @Override
            public List<WmsView> findWmsViewListByUserId(String userId) {
                return List.of(
                    new WmsView(3L, "state1", 1L),
                    new WmsView(6L, "state2", 2L),
                    new WmsView(9L, "state3", 3L)
                );
            }
        };
    }


    class Sample{


    }

    // 스프링 앱이 시작될 때, 한번만 실행되는 코드
    @Bean
    public Sample init(OrderJpaRepository orderJpaRepository) {
        Order order1 = Order.of(1L, "userId1");
        orderJpaRepository.save(order1);
        Order order2 = Order.of(2L, "userId1");
        orderJpaRepository.save(order2);
        Order order3 = Order.of(3L, "userId1");
        orderJpaRepository.save(order3);
        return new Sample();
    }

}


