package keyhub.data.document;

import keyhub.data.document.fragment.KhFragment;
import keyhub.data.document.fragment.KhFragmentIdSequenceList;
import keyhub.data.document.scrap.KhScrap;
import keyhub.data.version.KhVersion;

import java.util.List;

public interface KhDocument<ID, FRAGMENT_ID> {
    static <ID, FRAGMENT_ID> KhDocument<ID, FRAGMENT_ID> of(ID id, String title){
        return KhDocumentImplement.of(id, title);
    }
    ID id();
    String title();
    KhVersion version();
    List<KhFragment<FRAGMENT_ID>> fragments();
    List<KhFragment<FRAGMENT_ID>> histories(FRAGMENT_ID key);
    List<FRAGMENT_ID> fragmentSequence();
    String contents();

    void title(String title);
    void version(KhVersion version);

    KhFragment<FRAGMENT_ID> addScrap(KhScrap<FRAGMENT_ID> scrap, KhFragmentIdSequenceList<FRAGMENT_ID> sequence);
    void setFragmentSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence);
    boolean equalsFragmentSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence);
}
