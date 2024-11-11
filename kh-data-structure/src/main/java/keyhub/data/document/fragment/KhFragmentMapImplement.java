package keyhub.data.document.fragment;

import keyhub.data.document.scrap.KhScrap;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class KhFragmentMapImplement<FRAGMENT_ID> implements KhFragmentMap<FRAGMENT_ID> {
    private final List<FRAGMENT_ID> fragmentSequence;
    private final Map<FRAGMENT_ID, List<KhFragment<FRAGMENT_ID>>> fragmentMap;

    private KhFragmentMapImplement() {
        this.fragmentMap = new HashMap<>();
        this.fragmentSequence = new KhFragmentIdSequenceList<>();
    }
    static <FRAGMENT_ID> KhFragmentMap<FRAGMENT_ID> empty(){
        return new KhFragmentMapImplement<>();
    }

    @Override
    public List<FRAGMENT_ID> getSequence() {
        return List.copyOf(fragmentSequence);
    }

    @Override
    public String getContents() {
        return fragmentSequence.stream()
                .map(fragmentId -> fragmentMap.get(fragmentId).stream().max(KhFragment::compareByLatest).get().content())
                .collect(Collectors.joining());
    }

    @Override
    public List<KhFragment<FRAGMENT_ID>> getList() {
        return fragmentSequence.stream()
                .map(fragmentId -> fragmentMap.get(fragmentId).stream().max(KhFragment::compareByLatest).get())
                .toList();
    }

    @Override
    public List<KhFragment<FRAGMENT_ID>> getHistories(FRAGMENT_ID key) {
        return fragmentMap.get(key).stream()
                .sorted(KhFragment::compareByLatest)
                .toList();
    }

    @Override
    public void setSequence(KhFragmentIdSequenceList<FRAGMENT_ID> sequence) {
        if(sequence.size() != this.fragmentSequence.size()
                || !sequence.containsAll(this.fragmentSequence)){
            throw new IllegalArgumentException("KhFragmentIdSequenceList size is not same");
        }
        fragmentSequence.clear();
        fragmentSequence.addAll(sequence);
    }

    @Override
    public int size() {
        return fragmentMap.size();
    }

    @Override
    public boolean isEmpty() {
        return fragmentMap.isEmpty();
    }

    @Override
    public boolean containsKey(FRAGMENT_ID key) {
        return fragmentMap.containsKey(key);
    }

    @Override
    public boolean containsValue(KhFragment<FRAGMENT_ID> value) {
        return fragmentMap.containsValue(value);
    }

    @Override
    public KhFragment<FRAGMENT_ID> get(FRAGMENT_ID key) {
        return fragmentMap.get(key).stream().max(KhFragment::compareByLatest).get();
    }

    @Override
    public KhFragment<FRAGMENT_ID> put(KhScrap<FRAGMENT_ID> scrap, KhFragmentIdSequenceList<FRAGMENT_ID> sequence) {
        // todo
        return null;
    }

    private KhFragment<FRAGMENT_ID> put(FRAGMENT_ID key, KhFragment<FRAGMENT_ID> value) {
        List<KhFragment<FRAGMENT_ID>> list = fragmentMap.getOrDefault(key, new ArrayList<>());
        list.add(value);
        fragmentMap.put(key, list);
        return value;
    }

    @Override
    public KhFragment<FRAGMENT_ID> remove(Object key) {
        return fragmentMap.remove(key).stream().max(KhFragment::compareByLatest).get();
    }

    @Override
    public void putAll(Map<? extends FRAGMENT_ID, ? extends KhFragment<FRAGMENT_ID>> map) {
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        fragmentMap.clear();
    }

    @Override
    public Set<FRAGMENT_ID> keySet() {
        return fragmentMap.keySet();
    }

    @Override
    public Collection<KhFragment<FRAGMENT_ID>> values() {
        return fragmentMap.values()
                .stream()
                .map(fragments -> fragments.stream().max(KhFragment::compareByLatest).get())
                .toList();
    }

    @Override
    public Set<Entry<FRAGMENT_ID, KhFragment<FRAGMENT_ID>>> entrySet() {
        return fragmentMap.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().stream().max(KhFragment::compareByLatest).get()))
                .collect(Collectors.toSet());
    }
}