import java.io.*;
import java.net.Socket;
import java.util.Scanner;


class ClientSendHandler implements Runnable {
    private Socket clientSocket;
    private BufferedWriter bufferedWriter;


    public ClientSendHandler(Socket clientSocket, BufferedWriter bufferedWriter) {
        this.clientSocket = clientSocket;
        this.bufferedWriter = bufferedWriter;

    }


    // RUNS SEND HANDLING AS THREAD
    @Override
    public void run() {
        System.out.println("[+] Client send handler running.");
        try {
            while (!this.clientSocket.isClosed()) {
                Scanner scanner = new Scanner(System.in); // CREATES SCANNER WITH ATTRIBUTES
                String clientMessage = scanner.nextLine(); // STORES System.in FROM CONSOLE // BLOCK CODE
                bufferedWriter.write(clientMessage);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }


        } catch (IOException error) {
            System.out.println("[-] Send thread handling error: " + error);
            return;
        }
    }
}
