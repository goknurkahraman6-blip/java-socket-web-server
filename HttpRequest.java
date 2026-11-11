package webserver;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {

    private final String method;
    private final String path;

    private HttpRequest(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static HttpRequest parse(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();   // Örn: GET / HTTP/1.1
        if (requestLine == null || requestLine.isEmpty()) {
            return null;
        }
        String[] parts = requestLine.split(" ");
        if (parts.length < 2) {
            return null;
        }
        return new HttpRequest(parts[0], parts[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}