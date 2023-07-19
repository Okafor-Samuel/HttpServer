import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SocketClient {
    public void handleRequest(Socket clientSocket) {

        System.out.println("Client connected");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {
            String requestLine = in.readLine();

            if (requestLine != null) {
                String[] parts = requestLine.split(" ");
                String method = parts[0];
                String path = parts[1];

                System.out.println("HTTP/1.1 200 OK\\r\\n\"\n" +
                        " \"Content-Type: text/html\\r\\n\"\n" +
                        " \"Content-Length: \" + content.length() + \"\\r\\n\"\n" +
                        " \"\\r\\n\"\n" +
                        " content; " + path);

                if (method.equals("GET")) {
                    if (path.equals("/")) {
                        sendHtmlResponse(out, "/Users/decagon/Downloads/NetworkingWeek4/src/FilePath/HTML_Path.html");
                    } else if (path.equals("/json")) {
                        sendJsonResponse(out, "/Users/decagon/Downloads/NetworkingWeek4/src/FilePath/Json_Path");
                    } else {
                        sendNotFoundResponse(out);
                    }
                } else {
                    sendMethodNotAllowedResponse(out);
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendHtmlResponse(OutputStream out, String filePath) throws IOException {
        String content = readFileContents(filePath);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + content;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
    private void sendJsonResponse(OutputStream out, String filePath) throws IOException {
        String content = readFileContents(filePath);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + content;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
    private void sendNotFoundResponse(OutputStream out) throws IOException {
        String content = "404 Not Found";
        String response = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\n\r\n" + content;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
    private void sendMethodNotAllowedResponse(OutputStream out) throws IOException {
        String content = "405 Method Not Allowed";
        String response = "HTTP/1.1 405 Method Not Allowed\r\nContent-Type: text/plain\r\n\r\n" + content;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
    private String readFileContents(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
            return ""; // Return an empty string if the file cannot be read
        }
    }
}
