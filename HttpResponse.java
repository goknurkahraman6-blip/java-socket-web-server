package webserver;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final int statusCode;
    private final String statusText;
    private final String body;

    public HttpResponse(int statusCode, String statusText, String body) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
    }

    public byte[] toBytes() {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);

        String header = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Content-Length: " + bodyBytes.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
        out.write(headerBytes, 0, headerBytes.length);
        out.write(bodyBytes, 0, bodyBytes.length);
        return out.toByteArray();
    }
}