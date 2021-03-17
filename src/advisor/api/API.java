package advisor.api;

public interface API {

    String getCode();

    void auth();

    void getNew();

    void getFeatured();

    void getCategories();

    void getPlaylists(String name);

    void getAccessToken(String code);

    void next();

    void prev();
}
