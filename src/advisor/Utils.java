package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class Utils {
    public static Map<String, String> splitQuery(String query) {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
            }
        }
        return query_pairs;
    }

    public static Map<String, String> parseCliArgs(String[] args) {
        Map<String, String> arguments = new HashMap<>();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i += 2) {
                arguments.put(args[i], args[i + 1]);
            }
        }
        return arguments;
    }

    public static String parseAccessToken(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        String value = jo.get("access_token").getAsString();
        return value;
    }

    public static List<List<String>> parseNewJson(String json) {
        List<List<String>> list = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonArray items = jo.getAsJsonObject("albums").getAsJsonArray("items");
        for (JsonElement item : items) {
            List<String> row = new ArrayList<>();
            JsonObject el = item.getAsJsonObject();
            JsonArray artistsArr = el.get("artists").getAsJsonArray();
            List<String> artists = new ArrayList<>();
            for (JsonElement artist : artistsArr) {
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }
            row.add(el.get("name").getAsString());
            row.add(artists.toString());
            row.add(el.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            list.add(row);
        }
        return list;
    }

    public static List<List<String>> parseCategoriesJson(String json) {
        List<List<String>> list = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonArray items = jo.getAsJsonObject("categories").getAsJsonArray("items");
        for (JsonElement item : items) {
            List<String> row = new ArrayList<>();
            JsonObject el = item.getAsJsonObject();
            row.add(el.get("name").getAsString());
            list.add(row);
        }
        return list;
    }

    public static String findCategoryId(String json, String categoryName) {
        String result = null;
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonArray items = jo.getAsJsonObject("categories").getAsJsonArray("items");
        for (JsonElement item : items) {
            JsonObject el = item.getAsJsonObject();
            String name = el.get("name").getAsString();
            if (name.equalsIgnoreCase(categoryName)) {
                result = el.get("id").getAsString();
                break;
            }
        }
        return result;
    }

    public static List<List<String>> parsePlaylists(String json) {
        List<List<String>> list = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        if (jo.getAsJsonObject("error") != null) {
            System.out.println(jo.getAsJsonObject("error").get("message").getAsString());
        } else {
            JsonArray items = jo.getAsJsonObject("playlists").getAsJsonArray("items");
            for (JsonElement item : items) {
                List<String> row = new ArrayList<>();
                JsonObject el = item.getAsJsonObject();
                row.add(el.get("name").getAsString());
                row.add(el.get("external_urls").getAsJsonObject().get("spotify").getAsString());
                list.add(row);
            }
        }
        return list;
    }

    public static int getTotal(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        return jo.getAsJsonObject("categories").get("total").getAsInt();
    }
}
