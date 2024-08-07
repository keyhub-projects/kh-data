package keyhub.order.infrastructure;

import keyhub.common.lib.pg.PgClient;
import keyhub.common.lib.pg.PgView;
import keyhub.common.lib.wms.WmsClient;
import keyhub.common.lib.wms.WmsView;
import keyhub.data.tbl.Tbl;
import keyhub.order.domain.Order;
import keyhub.order.domain.OrderReader;
import keyhub.order.domain.dto.OrderDetailView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class OrderReaderImplement implements OrderReader {
    private final OrderJpaRepository orderJpaRepository;
    private final PgClient pgClient;
    private final WmsClient wmsClient;

    @Override
    public List<OrderDetailView> findOrderedDetailViewList(String userId) throws IllegalAccessException {
        List<Order> orderList = orderJpaRepository.findByUserId(userId);
        List<PgView> pgViewList = pgClient.findPgViewListByUserId(userId);
        List<WmsView> wmsViewList = wmsClient.findWmsViewListByUserId(userId);

        Tbl orderTbl = Tbl.asObjects(orderList);
        Tbl pgTbl = Tbl.asObjects(pgViewList);
        Tbl wmsTbl = Tbl.asObjects(wmsViewList);
        Tbl result = orderTbl.innerJoin(pgTbl)
                .on("id", "orderId")
                .selectAll()
                .toTbl()
                .innerJoin(wmsTbl)
                .on("id", "orderId")
                .selectAll()
                .toTbl();
        List<Map<String, Object>> mapList = result.toRowMapList();
        return mapList.stream()
                .map(OrderDetailView::fromRowMap)
                .toList();
    }
}
