package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Server {
    private HttpServer server;
    private String code;
    private boolean access = false;

    public Server() {
    }


    public String getCode() {
        return code;
    }

    public boolean hasAccess() {
        return access;
    }

    public void start(CountDownLatch latch) {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/",
                    new HttpHandler() {
                        @Override
                        public void handle(HttpExchange exchange) throws IOException {
                            String query = exchange.getRequestURI().getQuery();
                            String message;
                            if (query != null && query.contains("code")) {
                                message = "Got the code. Return back to your program.";
                                Map<String, String> params = Utils.splitQuery(query);
                                code = params.get("code");
                                latch.countDown();
                            } else {
                                message = "Authorization code not found. Try again.";
                            }
                            exchange.sendResponseHeaders(200, message.length());
                            exchange.getResponseBody().write(message.getBytes());
                            exchange.getResponseBody().close();
                        }
                    }
            );
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        server.stop(1);
    }

}
