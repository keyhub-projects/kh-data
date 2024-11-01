package keyhub.data.document;

public record KhFragment<FRAGMENT_ID>(
    FRAGMENT_ID id,
    KhOrder order,
    KhVersion version,
    String content,
    Boolean deleted
)implements Comparable<KhFragment<FRAGMENT_ID>>{

    @Override
    public int compareTo(KhFragment<FRAGMENT_ID> o) {
        int comparedByOrder = order.compareTo(o.order);
        int comparedByVersion = version.compareTo(o.version);
        if(comparedByOrder == 0){
            return comparedByVersion;
        }
        return comparedByOrder;
    }

    public int compareByLatest(KhFragment<FRAGMENT_ID> o) {
        return version.compareTo(o.version);
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
        KhFragment<FRAGMENT_ID> that = (KhFragment<FRAGMENT_ID>) obj;
        return id.equals(that.id)
            && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + version.hashCode();
    }

    @Override
    public String toString() {
        return "KhFragment{" +
                "id=" + id +
                ", order=" + order +
                ", version=" + version +
                ", content='" + content +
                "'}";
    }
}
