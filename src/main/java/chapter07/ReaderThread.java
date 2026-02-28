package chapter07;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReaderThread extends Thread {

    private final Socket socket;

    private final InputStream inputStream;

    public ReaderThread(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }


    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException _) {
        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        byte[] buf = new byte[1024];
        try {
            while (true) {
                int count = inputStream.read(buf);
                if (count < 0)
                    break;
                else if(count > 0)
                    processBuffer(buf, count);
            }

        } catch (IOException _) {
            // Allow thread to exit
        }
    }

    private void processBuffer(byte[] buf, int count) {

    }
}
