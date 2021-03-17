package advisor.api;

import advisor.*;

import java.util.concurrent.CountDownLatch;

public class SpotifyAPI implements API {
    protected Server server;
    protected Client client;
    protected String link;
    private CountDownLatch latch = new CountDownLatch(1);
    private AdvisorModel model = AdvisorModel.getInstance();

    public SpotifyAPI(Server server, Client client) {
        this.server = server;
        this.client = client;
        this.link = Config.getAccessServerPoint() + "/authorize?" +
                "client_id=" + Config.CLIENT_ID +
                "&redirect_uri=" + Config.REDIRECT_URI +
                "&response_type=code";
    }

    public String getCode() {
        return server.getCode();
    }

    public void auth() {
        server.start(latch);
        System.out.println("use this link to request the access code:");
        System.out.println(link);
        System.out.println("waiting for code...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.stop();
        System.out.println("code received");
        System.out.println("making http request for access_token...");
        getAccessToken(server.getCode());
    }

    @Override
    public void getAccessToken(String code) {
        String bodyParams = new StringBuilder("grant_type=authorization_code")
                .append("&code=" + code)
                .append("&client_id=" + Config.CLIENT_ID)
                .append("&client_secret=" + Config.SECRET)
                .append("&redirect_uri=" + Config.REDIRECT_URI)
                .toString();
        String response = client.post(Config.getAccessServerPoint() + "/api/token", bodyParams);
        client.setAccessToken(Utils.parseAccessToken(response));
        System.out.println("access_token: " + client.getAccessToken());
        System.out.println("Success!");
    }

    @Override
    public void getFeatured() {
        String response = client.get(Config.getAccessResource() + "/v1/browse/featured-playlists");
        model.setData(Utils.parsePlaylists(response));
    }

    @Override
    public void getNew() {
        String response = client.get(Config.getAccessResource() + "/v1/browse/new-releases");
        model.setData(Utils.parseNewJson(response));
    }

    @Override
    public void getCategories() {
        String response = client.get(Config.getAccessResource() + "/v1/browse/categories");
        model.setData(Utils.parseCategoriesJson(response));
    }

    @Override
    public void getPlaylists(String categoryName) {
        int offset = 0;
        String categories = client.get(Config.getAccessResource() + "/v1/browse/categories?limit=50&offset=" + offset);
        int total = Utils.getTotal(categories);
        String CATEGORY_ID = Utils.findCategoryId(categories, categoryName);
        while (offset < total && CATEGORY_ID == null) {
            offset += 50;
            categories = client.get(Config.getAccessResource() + "/v1/browse/categories?limit=50&offset=" + offset);
            CATEGORY_ID = Utils.findCategoryId(categories, categoryName);
        }
        if (CATEGORY_ID == null) {
            System.out.println("Unknown category name.");
            return;
        }
        String playlists = client.get(Config.getAccessResource() + "/v1/browse/categories/" + CATEGORY_ID + "/playlists");
        model.setData(Utils.parsePlaylists(playlists));
    }

    public void next() {
        model.next();
    }

    public void prev() {
        model.prev();
    }
}
