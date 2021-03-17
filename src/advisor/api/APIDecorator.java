package advisor.api;

public class APIDecorator implements API {
    private API api;

    public APIDecorator(API api) {
        this.api = api;
    }

    @Override
    public String getCode() {
        return api.getCode();
    }

    @Override
    public void auth() {
        api.auth();
    }

    @Override
    public void getAccessToken(String code) {
        api.getAccessToken(code);
    }

    public void getNew() {
        api.getNew();
    }

    public void getFeatured() {
        api.getFeatured();
    }

    public void getCategories() {
        api.getCategories();
    }

    public void getPlaylists(String name) {
        api.getPlaylists(name);
    }

    @Override
    public void next() {
        api.next();
    }

    @Override
    public void prev() {
        api.prev();
    }
}
