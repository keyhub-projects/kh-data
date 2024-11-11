package keyhub.data.document.fragment;

import keyhub.data.order.KhOrder;
import keyhub.data.version.KhVersion;

public class KhFragmentImplement<FRAGMENT_ID> implements KhFragment<FRAGMENT_ID>{
    private final KhFragmentId<FRAGMENT_ID> id;
    private final KhOrder order;
    private final String content;
    private final Boolean deleted;

    private KhFragmentImplement(KhFragmentId<FRAGMENT_ID> id, KhOrder order, String content, Boolean deleted) {
        this.id = id;
        this.order = order;
        this.content = content;
        this.deleted = deleted;
    }
    public static <FRAGMENT_ID> KhFragment<FRAGMENT_ID> of(KhFragmentId<FRAGMENT_ID> id, KhOrder order, String content, Boolean deleted){
        return new KhFragmentImplement<>(id, order, content, deleted);
    }

    @Override
    public KhFragmentId<FRAGMENT_ID> fragmentId() {
        return id;
    }
    @Override
    public FRAGMENT_ID id() {
        return id.id();
    }
    @Override
    public KhVersion version() {
        return id.version();
    }
    @Override
    public KhOrder order() {
        return order;
    }
    @Override
    public String content() {
        return content;
    }
    @Override
    public Boolean deleted() {
        return deleted;
    }

    @Override
    public int compareTo(KhFragment<FRAGMENT_ID> o) {
        int comparedByOrder = order().compareTo(o.order());
        int comparedByVersion = version().compareTo(o.version());
        if(comparedByOrder == 0){
            return comparedByVersion;
        }
        return comparedByOrder;
    }

    @Override
    public int compareByLatest(KhFragment<FRAGMENT_ID> o) {
        if(!id().equals(o.id())){
            throw new IllegalArgumentException("KhFragment id is not same");
        }
        return version().compareTo(o.version());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        @SuppressWarnings("unchecked")
        KhFragmentImplement<FRAGMENT_ID> that = (KhFragmentImplement<FRAGMENT_ID>) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "KhFragment{" +
                "id=" + id +
                ", order=" + order +
                ", content='" + content +
                "'}";
    }
}
