package keyhub.common.lib.wms;

import java.util.List;

public interface WmsClient {
    List<WmsView> findWmsViewListByUserId(String userId);
}
