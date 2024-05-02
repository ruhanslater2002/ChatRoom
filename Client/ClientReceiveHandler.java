import java.io.*;
import java.net.Socket;


class ClientReceiveHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader bufferedReader;


    public ClientReceiveHandler(Socket clientSocket, BufferedReader bufferedReader) {
        this.clientSocket = clientSocket;
        this.bufferedReader = bufferedReader;

    }


    // RUNS RECEIVE HANDLING AS THREAD
    @Override
    public void run() {
        System.out.println("[+] Client receive handler running.");
        try {
            while (!this.clientSocket.isClosed()) {
                System.out.println(this.bufferedReader.readLine()); // WAITS FOR MESSAGE TO RECEIVE FROM SERVER // BLOCK CODE
            }

        } catch (IOException error) {
            System.out.println("[-] Receive thread handling error: " + error);
            return;
        }
    }
}
