package keyhub.data.document;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KhDocumentImplement<ID, FRAGMENT_ID> implements KhDocument<ID, FRAGMENT_ID>{
    private final ID id;
    private final String title;
    private final KhVersion version;
    private final List<KhFragment<FRAGMENT_ID>> fragments;

    private KhDocumentImplement(ID id, String title, KhVersion version, List<KhFragment<FRAGMENT_ID>> fragments) {
        this.id = id;
        this.title = title;
        this.version = version;
        this.fragments = fragments;
    }
    public static <ID, FRAGMENT_ID> KhDocument<ID, FRAGMENT_ID> of(ID id, String title){
        @SuppressWarnings("unchecked")
        var result = (KhDocument<ID, FRAGMENT_ID>) KhDocumentImplement.builder()
                .id(id)
                .title(title)
                .version(KhVersion.of(0L,0L,1L))
                .build();
        return result;
    }
    public static <ID, FRAGMENT_ID> KhDocumentBuilder<ID, FRAGMENT_ID> builder() {
        return new KhDocumentBuilder<>();
    }
    public static class KhDocumentBuilder<ID, FRAGMENT_ID>{
        private ID id;
        private String title;
        private KhVersion version = KhVersion.of(0L,0L,1L);
        private final List<KhFragment<FRAGMENT_ID>> fragments = new ArrayList<>();
        public KhDocumentBuilder<ID, FRAGMENT_ID> id(ID id) {
            this.id = id;
            return this;
        }
        public KhDocumentBuilder<ID, FRAGMENT_ID> title(String title) {
            this.title = title;
            return this;
        }
        public KhDocumentBuilder<ID, FRAGMENT_ID> version(KhVersion version){
            this.version = version;
            return this;
        }
        public KhDocumentBuilder<ID, FRAGMENT_ID> addFragment(KhFragment<FRAGMENT_ID> fragment){
            fragments.add(fragment);
            return this;
        }
        public KhDocument<ID, FRAGMENT_ID> build() {
            @SuppressWarnings("unchecked")
            var result = (KhDocument<ID, FRAGMENT_ID>) new KhDocumentImplement<ID, FRAGMENT_ID>(this.id, this.title, this.version, this.fragments);
            return result;
        }
    }

    @Override
    public ID id() {
        return id;
    }
    @Override
    public String title(){
        return this.title;
    }
    @Override
    public KhVersion version(){
        return this.version;
    }
    @Override
    public List<KhFragment<FRAGMENT_ID>> fragments(){
        return this.fragments;
    }
    @Override
    public KhFragmentSequence<ID, FRAGMENT_ID> latestFragmentSequence(){
        // 각 fragment id로 그룹화하여, 가장 높은 version을 가진 fragment를 찾기
        var list = fragments.stream()
                .collect(Collectors.groupingBy(KhFragment::id, Collectors.maxBy(KhFragment::compareByLatest)))
                .values().stream().map(Optional::orElseThrow)
                .map(KhFragment::id)
                .toList();
        return new KhFragmentSequence<>(id, list);
    }

    // 모든 변경은 단일 스크랩 단위로만 입력받아 변경된다.
    // 스크랩이 왔다는 것은, 스크랩이 변경되었다는 것. 신규 추가든 수정이든.
    @Override
    public KhDocument<ID, FRAGMENT_ID> addScrap(KhScrap<FRAGMENT_ID> scrap, KhFragmentSequence<ID, FRAGMENT_ID> sequence) {
        // 신규
        // 변경 - 순서
        // 변경 - 값
        // 변경 - 삭제
        return this;
    }

    Optional<KhFragment<FRAGMENT_ID>> findLatestFragment(FRAGMENT_ID fragmentId){
        return fragments.stream()
                .filter(f -> f.id().equals(fragmentId))
                .max(KhFragment::compareByLatest);
    }
    boolean isFragmentSequenceSame(KhFragmentSequence<ID, FRAGMENT_ID> sequence){
        var list = sequence.fragmentIds();
        if(fragments.size() != list.size()){
            return false;
        }
        var latestSequence = latestFragmentSequence();
        return latestSequence.fragmentIds().equals(list);
    }
}
