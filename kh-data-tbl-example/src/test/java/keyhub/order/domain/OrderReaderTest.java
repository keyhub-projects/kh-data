package keyhub.order.domain;

import keyhub.order.infrastructure.OrderJpaRepository;
import keyhub.order.infrastructure.OrderReaderImplement;
import org.junit.jupiter.api.Test;
import keyhub.order.domain.dto.OrderDetailView;
import keyhub.common.lib.pg.PgClient;
import keyhub.common.lib.pg.PgView;
import keyhub.common.lib.wms.WmsClient;
import keyhub.common.lib.wms.WmsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderReaderTest {

    @Autowired
    private OrderReader orderReader;

    @Test
    public void testFindOrderedDetailViewList() throws IllegalAccessException{

        /*
        * [
        *     OrderDetailView[orderId=1, pgId=2, pgState=state1, wmsId=3, wmsState=wmsState1],
        *     OrderDetailView[orderId=2, pgId=4, pgState=state2, wmsId=6, wmsState=wmsState2],
        *     OrderDetailView[orderId=3, pgId=6, pgState=state3, wmsId=9, wmsState=wmsState3]
        * ]
        */
        List<OrderDetailView> expected = new ArrayList<>();
        expected.add(new OrderDetailView(1L, 2L, "pgState1", 3L, "wmsState1"));
        expected.add(new OrderDetailView(2L, 4L, "pgState2", 6L, "wmsState2"));
        expected.add(new OrderDetailView(3L, 6L, "pgState3", 9L, "wmsState3"));

        List<OrderDetailView> result = orderReader.findOrderedDetailViewList("userId1");

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expected, result);
     }
}