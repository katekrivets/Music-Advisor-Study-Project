package advisor;

public class Config {
    public static final String CLIENT_ID = /* copy client_id from spotify dev api */;
    public static final String SECRET = /* copy secret from spotify dev api */;
    public static final String REDIRECT_URI = "http://localhost:8080";
    private static String accessServerPoint = "https://accounts.spotify.com";
    private static String accessResource = "https://api.spotify.com";
    private static Integer pageSize = 5;

    public static String getAccessServerPoint() {
        return accessServerPoint;
    }

    public static void setAccessServerPoint(String accessServerPoint) {
        if (accessServerPoint != null) Config.accessServerPoint = accessServerPoint;
    }

    public static String getAccessResource() {
        return accessResource;
    }

    public static void setAccessResource(String accessResource) {
        if (accessResource != null) Config.accessResource = accessResource;
    }

    public static Integer getPageSize() {
        return pageSize;
    }

    public static void setPageSize(String pageSize) {
        if (pageSize != null) Config.pageSize = Integer.parseInt(pageSize);
    }
}
