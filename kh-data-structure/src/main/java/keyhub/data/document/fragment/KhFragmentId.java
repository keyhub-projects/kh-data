package keyhub.data.document.fragment;

import keyhub.data.version.KhVersion;

public record KhFragmentId<FRAGMENT_ID>(
        FRAGMENT_ID id,
        KhVersion version
) {
    private static final KhFragmentId<?> EMPTY = new KhFragmentId<>(null, KhVersion.of(0L,0L,1L));
    public static <FRAGMENT_ID> KhFragmentId<FRAGMENT_ID> empty(){
        @SuppressWarnings("unchecked")
        var empty = (KhFragmentId<FRAGMENT_ID>) EMPTY;
        return empty;
    }
    public static <FRAGMENT_ID> KhFragmentId<FRAGMENT_ID> of(FRAGMENT_ID id, KhVersion version){
        return new KhFragmentId<>(id, version);
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
        KhFragmentId<FRAGMENT_ID> that = (KhFragmentId<FRAGMENT_ID>) obj;
        return id.equals(that.id)
            && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return id.hashCode() + version.hashCode();
    }

    @Override
    public String toString() {
        return id + "." + version;
    }
}
