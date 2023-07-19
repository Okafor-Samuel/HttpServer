
//import com.sun.net.httpserver.HttpServer;
//import org.example.HttpServer.SocketServer;

public class Main {
    public static void main(String[] args) {
                int port = 8080;
                SocketServer server = new SocketServer(port);
                server.start();
    }
}