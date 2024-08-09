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
            new PgView(6L, "pgState3", 3L),
            new PgView(8L, "pgState4", 4L),
            new PgView(10L, "pgState5", 5L),
            new PgView(12L, "pgState6", 6L),
            new PgView(14L, "pgState7", 7L),
            new PgView(16L, "pgState8", 8L),
            new PgView(18L, "pgState9", 9L),
            new PgView(20L, "pgState10", 10L),
            new PgView(22L, "pgState11", 11L),
            new PgView(24L, "pgState12", 12L),
            new PgView(26L, "pgState13", 13L),
            new PgView(28L, "pgState14", 14L),
            new PgView(30L, "pgState15", 15L),
            new PgView(32L, "pgState16", 16L),
            new PgView(34L, "pgState17", 17L),
            new PgView(36L, "pgState18", 18L),
            new PgView(38L, "pgState19", 19L),
            new PgView(40L, "pgState20", 20L),
            new PgView(42L, "pgState21", 21L),
            new PgView(44L, "pgState22", 22L),
            new PgView(46L, "pgState23", 23L),
            new PgView(48L, "pgState24", 24L),
            new PgView(50L, "pgState25", 25L),
            new PgView(52L, "pgState26", 26L),
            new PgView(54L, "pgState27", 27L),
            new PgView(56L, "pgState28", 28L),
            new PgView(58L, "pgState29", 29L),
            new PgView(60L, "pgState30", 30L),
            new PgView(62L, "pgState31", 31L),
            new PgView(64L, "pgState32", 32L),
            new PgView(66L, "pgState33", 33L),
            new PgView(68L, "pgState34", 34L),
            new PgView(70L, "pgState35", 35L),
            new PgView(72L, "pgState36", 36L),
            new PgView(74L, "pgState37", 37L),
            new PgView(76L, "pgState38", 38L),
            new PgView(78L, "pgState39", 39L),
            new PgView(80L, "pgState40", 40L),
            new PgView(82L, "pgState41", 41L),
            new PgView(84L, "pgState42", 42L),
            new PgView(86L, "pgState43", 43L),
            new PgView(88L, "pgState44", 44L),
            new PgView(90L, "pgState45", 45L),
            new PgView(92L, "pgState46", 46L),
            new PgView(94L, "pgState47", 47L),
            new PgView(96L, "pgState48", 48L),
            new PgView(98L, "pgState49", 49L),
            new PgView(100L, "pgState50", 50L)
        );
    }

    @Bean
    public WmsClient fakeWmsClient() {
        return userId -> List.of(
            new WmsView(3L, "wmsState1", 1L),
            new WmsView(6L, "wmsState2", 2L),
            new WmsView(9L, "wmsState3", 3L),
            new WmsView(12L, "wmsState4", 4L),
            new WmsView(15L, "wmsState5", 5L),
            new WmsView(18L, "wmsState6", 6L),
            new WmsView(21L, "wmsState7", 7L),
            new WmsView(24L, "wmsState8", 8L),
            new WmsView(27L, "wmsState9", 9L),
            new WmsView(30L, "wmsState10", 10L),
            new WmsView(33L, "wmsState11", 11L),
            new WmsView(36L, "wmsState12", 12L),
            new WmsView(39L, "wmsState13", 13L),
            new WmsView(42L, "wmsState14", 14L),
            new WmsView(45L, "wmsState15", 15L),
            new WmsView(48L, "wmsState16", 16L),
            new WmsView(51L, "wmsState17", 17L),
            new WmsView(54L, "wmsState18", 18L),
            new WmsView(57L, "wmsState19", 19L),
            new WmsView(60L, "wmsState20", 20L),
            new WmsView(63L, "wmsState21", 21L),
            new WmsView(66L, "wmsState22", 22L),
            new WmsView(69L, "wmsState23", 23L),
            new WmsView(72L, "wmsState24", 24L),
            new WmsView(75L, "wmsState25", 25L),
            new WmsView(78L, "wmsState26", 26L),
            new WmsView(81L, "wmsState27", 27L),
            new WmsView(84L, "wmsState28", 28L),
            new WmsView(87L, "wmsState29", 29L),
            new WmsView(90L, "wmsState30", 30L),
            new WmsView(93L, "wmsState31", 31L),
            new WmsView(96L, "wmsState32", 32L),
            new WmsView(99L, "wmsState33", 33L),
            new WmsView(102L, "wmsState34", 34L),
            new WmsView(105L, "wmsState35", 35L),
            new WmsView(108L, "wmsState36", 36L),
            new WmsView(111L, "wmsState37", 37L),
            new WmsView(114L, "wmsState38", 38L),
            new WmsView(117L, "wmsState39", 39L),
            new WmsView(120L, "wmsState40", 40L),
            new WmsView(123L, "wmsState41", 41L),
            new WmsView(126L, "wmsState42", 42L),
            new WmsView(129L, "wmsState43", 43L),
            new WmsView(132L, "wmsState44", 44L),
            new WmsView(135L, "wmsState45", 45L),
            new WmsView(138L, "wmsState46", 46L),
            new WmsView(141L, "wmsState47", 47L),
            new WmsView(144L, "wmsState48", 48L),
            new WmsView(147L, "wmsState49", 49L),
            new WmsView(150L, "wmsState50", 50L)
        );
    }

    @Autowired OrderJpaRepository orderJpaRepository;
    @PostConstruct
    public void init() {
        orderJpaRepository.save(Order.of(1L, "userId1"));
        orderJpaRepository.save(Order.of(2L, "userId1"));
        orderJpaRepository.save(Order.of(3L, "userId1"));
        orderJpaRepository.save(Order.of(4L, "userId2"));
        orderJpaRepository.save(Order.of(5L, "userId2"));
        orderJpaRepository.save(Order.of(6L, "userId2"));
        orderJpaRepository.save(Order.of(7L, "userId2"));
        orderJpaRepository.save(Order.of(8L, "userId2"));
        orderJpaRepository.save(Order.of(9L, "userId2"));
        orderJpaRepository.save(Order.of(10L, "userId2"));
        orderJpaRepository.save(Order.of(11L, "userId2"));
        orderJpaRepository.save(Order.of(12L, "userId2"));
        orderJpaRepository.save(Order.of(13L, "userId2"));
        orderJpaRepository.save(Order.of(14L, "userId2"));
        orderJpaRepository.save(Order.of(15L, "userId2"));
        orderJpaRepository.save(Order.of(16L, "userId2"));
        orderJpaRepository.save(Order.of(17L, "userId2"));
        orderJpaRepository.save(Order.of(18L, "userId2"));
        orderJpaRepository.save(Order.of(19L, "userId2"));
        orderJpaRepository.save(Order.of(20L, "userId2"));
        orderJpaRepository.save(Order.of(21L, "userId2"));
        orderJpaRepository.save(Order.of(22L, "userId2"));
        orderJpaRepository.save(Order.of(23L, "userId2"));
        orderJpaRepository.save(Order.of(24L, "userId2"));
        orderJpaRepository.save(Order.of(25L, "userId2"));
        orderJpaRepository.save(Order.of(26L, "userId2"));
        orderJpaRepository.save(Order.of(27L, "userId2"));
        orderJpaRepository.save(Order.of(28L, "userId2"));
        orderJpaRepository.save(Order.of(29L, "userId2"));
        orderJpaRepository.save(Order.of(30L, "userId2"));
        orderJpaRepository.save(Order.of(31L, "userId2"));
        orderJpaRepository.save(Order.of(32L, "userId2"));
        orderJpaRepository.save(Order.of(33L, "userId2"));
        orderJpaRepository.save(Order.of(34L, "userId2"));
        orderJpaRepository.save(Order.of(35L, "userId2"));
        orderJpaRepository.save(Order.of(36L, "userId2"));
        orderJpaRepository.save(Order.of(37L, "userId2"));
        orderJpaRepository.save(Order.of(38L, "userId2"));
        orderJpaRepository.save(Order.of(39L, "userId2"));
        orderJpaRepository.save(Order.of(40L, "userId2"));
        orderJpaRepository.save(Order.of(41L, "userId2"));
        orderJpaRepository.save(Order.of(42L, "userId2"));
        orderJpaRepository.save(Order.of(43L, "userId2"));
        orderJpaRepository.save(Order.of(44L, "userId2"));
        orderJpaRepository.save(Order.of(45L, "userId2"));
        orderJpaRepository.save(Order.of(46L, "userId2"));
        orderJpaRepository.save(Order.of(47L, "userId2"));
        orderJpaRepository.save(Order.of(48L, "userId2"));
        orderJpaRepository.save(Order.of(49L, "userId2"));
        orderJpaRepository.save(Order.of(50L, "userId2"));
    }
}


