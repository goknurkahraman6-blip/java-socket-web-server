package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final PageProvider pageProvider;

    public ClientHandler(Socket clientSocket, PageProvider pageProvider) {
        this.clientSocket = clientSocket;
        this.pageProvider = pageProvider;
    }

    @Override
    public void run() {
        try (Socket socket = clientSocket;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {

            HttpRequest request = HttpRequest.parse(reader);
            if (request == null) {
                return;
            }

            HttpResponse response;
            if (request.getMethod().equals("GET") && request.getPath().equals("/")) {
                response = new HttpResponse(200, "OK", pageProvider.getHtml());
            } else {
                response = new HttpResponse(404, "Not Found",
                        "<h1>404 - Sayfa bulunamadı</h1>");
            }

            out.write(response.toBytes());
            out.flush();

        } catch (IOException e) {
            System.out.println("İstemci hatası: " + e.getMessage());
        }
    }
}