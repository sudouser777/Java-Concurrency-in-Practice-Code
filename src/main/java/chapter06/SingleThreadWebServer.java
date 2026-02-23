package chapter06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadWebServer {

    static void main() throws IOException {
        try (ServerSocket socket = new ServerSocket(80)) {
            while (true) {
                Socket connection = socket.accept();
                handleRequest(connection);
            }
        }
    }

    private static void handleRequest(Socket connection) throws IOException {
        try (connection) {
            System.out.println("Handling request from " + connection.getInetAddress());
            connection.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        }
    }
}
