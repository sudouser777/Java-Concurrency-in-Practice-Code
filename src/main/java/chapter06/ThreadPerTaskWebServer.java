package chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {

    static void main() throws IOException {
        try (ServerSocket socket = new ServerSocket(80)) {
            while (true) {
                Socket connection = socket.accept();
                new Thread(() -> handleRequest(connection)).start();
            }
        }
    }

    private static void handleRequest(Socket connection) {
        try (connection; var out = connection.getOutputStream()) {
            System.out.println("Handling request from " + connection.getInetAddress());
            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        } catch (IOException e) {
            System.err.println("Error handling request: " + e.getMessage());
            try {
                connection.getOutputStream().write(("HTTP/1.1 500 Internal Server Error\r\n\r\n" + e.getMessage()).getBytes());
            } catch (IOException ignored) {
            }
        }
    }
}
