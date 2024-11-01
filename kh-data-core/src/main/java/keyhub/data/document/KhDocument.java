package keyhub.data.document;

import java.util.List;

public interface KhDocument<ID, FRAGMENT_ID> {
    static <ID, FRAGMENT_ID> KhDocument<ID, FRAGMENT_ID> of(ID id, String title){
        return KhDocumentImplement.of(id, title);
    }
    ID id();
    String title();
    KhVersion version();
    List<KhFragment<FRAGMENT_ID>> fragments();
    KhFragmentSequence<ID, FRAGMENT_ID> latestFragmentSequence();

    KhDocument<ID, FRAGMENT_ID> addScrap(KhScrap<FRAGMENT_ID> scrap, KhFragmentSequence<ID, FRAGMENT_ID> sequence);
}
