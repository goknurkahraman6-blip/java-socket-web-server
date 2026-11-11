package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private final ServerConfig config;
    private final PageProvider pageProvider;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public WebServer(ServerConfig config, PageProvider pageProvider) {
        this.config = config;
        this.pageProvider = pageProvider;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {
            System.out.println("Sunucu başlatıldı. Port: " + config.getPort());
            System.out.println("Tarayıcıdan açın: http://localhost:" + config.getPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();   // bağlantı bekler
                threadPool.submit(new ClientHandler(clientSocket, pageProvider));
            }
        } catch (IOException e) {
            System.out.println("Sunucu hatası: " + e.getMessage());
        }
    }
}