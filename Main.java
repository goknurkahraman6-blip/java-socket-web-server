package webserver;

public class Main {

    public static void main(String[] args) {
        ServerConfig config = new ServerConfig(1989);
        PageProvider page = new BiographyPage("Göknur Kahraman", "Öğrenci No:5250505024");

        WebServer server = new WebServer(config, page);
        server.start();
    }
}
