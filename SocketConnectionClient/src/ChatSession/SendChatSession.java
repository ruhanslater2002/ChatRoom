package ChatSession;


import client.Client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendChatSession implements Runnable {
    private Socket clientSocket;
    private BufferedWriter bufferedWriter;
    private Scanner scanner = new Scanner(System.in);


    public SendChatSession() {
        try {
            this.clientSocket = Client.clientSocket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            // SENDS USERNAME TO SERVER
            this.bufferedWriter.write(Client.username);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();

        } catch (IOException e) {
            System.out.println("[-] Send chat constructor error: " + e);
            this.close_connection();
        }
    }


    @Override
    public void run() {
        try {
            while (this.clientSocket.isConnected()) {
                // scanner.nextLine() // BLOCK CODE, WAITS FOR INPUT FROM CLIENT
                this.bufferedWriter.write(scanner.nextLine());
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
            }

        } catch (IOException e) {
            System.out.println("[-] Send chat session error: " + e);
            this.close_connection();
        }
    }


    private void close_connection() {
        try {
            // CLOSE SCANNER SESSION
            this.scanner.close();

            if (this.clientSocket.isConnected()) {
                System.out.print("[!] Closing connection.. ");
                this.clientSocket.close();
                System.out.print("Connection closed.");
            }

        } catch (IOException e) {
            System.out.print("[-] Receive receive session close error: " + e);
        }
    }
}
