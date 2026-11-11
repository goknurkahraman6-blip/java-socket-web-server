package webserver;

public class ServerConfig {
    private final int port;

    public ServerConfig(int port){
        this.port = port;
    }
    public int getPort(){
        return port;
    }
}
