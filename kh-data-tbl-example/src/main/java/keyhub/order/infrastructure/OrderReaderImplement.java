package keyhub.order.infrastructure;

import keyhub.common.lib.pg.PgClient;
import keyhub.common.lib.pg.PgView;
import keyhub.common.lib.wms.WmsClient;
import keyhub.common.lib.wms.WmsView;
import keyhub.data.tbl.Tbl;
import keyhub.order.domain.Order;
import keyhub.order.domain.OrderReader;
import keyhub.order.domain.dto.OrderDetailView;
import keyhub.order.domain.dto.OrderSimpleView;
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
    public List<OrderDetailView> findOrderedDetailViewList(String userId){
        List<Order> orderList = orderJpaRepository.findByUserId(userId);
        List<PgView> pgViewList = pgClient.findPgViewListByUserId(userId);
        List<WmsView> wmsViewList = wmsClient.findWmsViewListByUserId(userId);

        Tbl orderTbl = Tbl.from(orderList);
        Tbl pgTbl = Tbl.from(pgViewList);
        Tbl wmsTbl = Tbl.from(wmsViewList);
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

    @Override
    public List<OrderSimpleView> findOrderedSimpleViewList(String userId) {
        List<Order> orderList = orderJpaRepository.findByUserId(userId);
        Tbl orderTbl = Tbl.from(orderList);
        List<Map<String, Object>> mapList = orderTbl.toRowMapList();
        return mapList.stream()
                .map(OrderSimpleView::fromRowMap)
                .toList();
    }
}
