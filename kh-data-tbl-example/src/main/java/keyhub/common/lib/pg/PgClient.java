package keyhub.common.lib.pg;

import java.util.List;

public interface PgClient {
    List<PgView> findPgViewListByUserId(String userId);
}
