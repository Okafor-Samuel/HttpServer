
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class SocketServer {
    private int port;
    public SocketServer(int port) {
        this.port = port;
    }
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleClient(Socket clientSocket) {
        SocketClient socketClient = new SocketClient();
        socketClient.handleRequest(clientSocket);
    }
}
