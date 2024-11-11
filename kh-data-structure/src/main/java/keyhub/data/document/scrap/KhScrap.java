package keyhub.data.document.scrap;

public record KhScrap<FRAGMENT_ID>(
        FRAGMENT_ID id,
        Long order,
        String content,
        Boolean deleted
) {
}
