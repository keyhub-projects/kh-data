package keyhub.data.document.fragment;

import keyhub.data.document.scrap.KhScrap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface KhFragmentMap<FRAGMENT_ID> {
    static <FRAGMENT_ID> KhFragmentMap<FRAGMENT_ID> empty(){
        return  KhFragmentMapImplement.empty();
    }

    List<FRAGMENT_ID> getSequence();

    String getContents();

    List<KhFragment<FRAGMENT_ID>> getList();

    List<KhFragment<FRAGMENT_ID>> getHistories(FRAGMENT_ID key);

    void setSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence);

    KhFragment<FRAGMENT_ID> put(KhScrap<FRAGMENT_ID> scrap, KhFragmentIdSequenceList<FRAGMENT_ID> sequence);

    int size();

    boolean isEmpty();

    boolean containsKey(FRAGMENT_ID key);

    boolean containsValue(KhFragment<FRAGMENT_ID> value);

    KhFragment<FRAGMENT_ID> get(FRAGMENT_ID key);

    KhFragment<FRAGMENT_ID> remove(FRAGMENT_ID key);

    void putAll(Map<? extends FRAGMENT_ID, ? extends KhFragment<FRAGMENT_ID>> map);

    void clear();

    Set<FRAGMENT_ID> keySet();

    Collection<KhFragment<FRAGMENT_ID>> values();

    Set<Entry<FRAGMENT_ID, KhFragment<FRAGMENT_ID>>> entrySet();
}