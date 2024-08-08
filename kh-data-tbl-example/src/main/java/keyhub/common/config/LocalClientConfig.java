package keyhub.common.config;

import jakarta.annotation.PostConstruct;
import keyhub.common.lib.pg.PgClient;
import keyhub.common.lib.pg.PgView;
import keyhub.common.lib.wms.WmsClient;
import keyhub.common.lib.wms.WmsView;
import keyhub.order.domain.Order;
import keyhub.order.infrastructure.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class LocalClientConfig {

    @Bean
    public PgClient fakePgClient() {
        return userId -> List.of(
            new PgView(2L, "pgState1", 1L),
            new PgView(4L, "pgState2", 2L),
            new PgView(6L, "pgState3", 3L)
        );
    }

    @Bean
    public WmsClient fakeWmsClient() {
        return userId -> List.of(
            new WmsView(3L, "wmsState1", 1L),
            new WmsView(6L, "wmsState2", 2L),
            new WmsView(9L, "wmsState3", 3L)
        );
    }

    @Autowired OrderJpaRepository orderJpaRepository;
    @PostConstruct
    public void init() {
        orderJpaRepository.save(Order.of(1L, "userId1"));
        orderJpaRepository.save(Order.of(2L, "userId1"));
        orderJpaRepository.save(Order.of(3L, "userId1"));
    }
}


