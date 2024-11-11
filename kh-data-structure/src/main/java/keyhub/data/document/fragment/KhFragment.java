package keyhub.data.document.fragment;

import keyhub.data.order.KhOrder;
import keyhub.data.version.KhVersion;

public interface KhFragment<FRAGMENT_ID> extends Comparable<KhFragment<FRAGMENT_ID>>{
    static <FRAGMENT_ID> KhFragment<FRAGMENT_ID> of(KhFragmentId<FRAGMENT_ID> id, KhOrder order, String content, Boolean deleted){
        return KhFragmentImplement.of(id, order, content, deleted);
    }

    KhFragmentId<FRAGMENT_ID> fragmentId();
    FRAGMENT_ID id();
    KhVersion version();
    KhOrder order();
    String content();
    Boolean deleted();

    int compareByLatest(KhFragment<FRAGMENT_ID> o);
}
