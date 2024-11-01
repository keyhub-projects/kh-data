package keyhub.data.document;

import java.util.List;

public record KhFragmentSequence<ID, FRAGMENT_ID>(
        ID documentId,
        List<FRAGMENT_ID> fragmentIds
) {
    public int findFragmentOrder(FRAGMENT_ID fragmentId) {
        return fragmentIds.indexOf(fragmentId);
    }
    public long findFragmentOrderByOrder(long order) {
        return fragmentIds.size() >= order ? fragmentIds.indexOf(order) : -1;
    }
}
