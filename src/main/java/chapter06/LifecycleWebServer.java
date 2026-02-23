package chapter06;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {

    private static final int N_THREADS = 100;

    private static final ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);

    public void start() throws IOException {
        try (ServerSocket socket = new ServerSocket(80)) {
            while (!executor.isShutdown()) {
                Socket connection = socket.accept();
                try {
                    executor.execute(() -> handleRequest(connection));
                } catch (RejectedExecutionException e) {
                    if (!executor.isShutdown())
                        System.out.println("Request rejected due to executor shutdown");
                }
            }
        }
    }

    public void stop() {
        executor.shutdown();
    }


    private void handleRequest(Socket connection) {
        Request request = readRequest(connection);
        if (isShutdownRequest(request)) {
            stop();
        } else {
            dispatchRequest(request);
        }
    }

    private boolean isShutdownRequest(Request request) {
        return false;
    }

    private Request readRequest(Socket connection) {
        return new Request(connection);
    }


    private void dispatchRequest(Request request) {
        Socket connection = request.getSocket();
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

    private static class Request {
        private final Socket socket;

        private Request(Socket socket) {
            this.socket = socket;
        }

        public Socket getSocket() {
            return socket;
        }
    }
}
