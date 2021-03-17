package advisor.api;

public class SecuredAPI extends APIDecorator {
    public SecuredAPI(SpotifyAPI api) {
        super(api);
    }

    @Override
    public void getFeatured() {
        if (getCode() != null) super.getFeatured();
        else System.out.println("Please, provide access for application.");
    }

    @Override
    public void getNew() {
        if (getCode() != null) super.getNew();
        else System.out.println("Please, provide access for application.");
    }

    @Override
    public void getCategories() {
        if (getCode() != null) super.getCategories();
        else System.out.println("Please, provide access for application.");
    }

    @Override
    public void getPlaylists(String categoryName) {
        if (getCode() != null) super.getPlaylists(categoryName);
        else System.out.println("Please, provide access for application.");
    }
}
