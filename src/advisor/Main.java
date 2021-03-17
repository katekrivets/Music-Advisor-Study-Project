package advisor;

import advisor.api.API;
import advisor.api.SecuredAPI;
import advisor.api.SpotifyAPI;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> arguments = Utils.parseCliArgs(args);
        Config.setAccessServerPoint(arguments.get("-access"));
        Config.setAccessResource(arguments.get("-resource"));
        Config.setPageSize(arguments.get("-page"));
        getData();
    }

    public static void getData() {
        Server server = new Server();
        Client client = new Client();
        API api = new SecuredAPI(new SpotifyAPI(server, client));
        while (true) {
            String[] cliArgs = InputReader.readCommand();
            switch (cliArgs[0]) {
                case "auth":
                    api.auth();
                    break;
                case "new":
                    api.getNew();
                    break;
                case "featured":
                    api.getFeatured();
                    break;
                case "categories":
                    api.getCategories();
                    break;
                case "playlists":
                    String playlistName = "";
                    for (int i = 1; i < cliArgs.length; i++) {
                        playlistName += cliArgs[i] + " ";
                    }
                    api.getPlaylists(playlistName.trim());
                    break;
                case "next":
                    api.next();
                    break;
                case "prev":
                    api.prev();
                    break;
                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
                default:
                    System.out.println("Please, provide access for application.");
                    break;
            }
        }
    }


}
