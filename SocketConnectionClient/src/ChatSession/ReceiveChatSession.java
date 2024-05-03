package ChatSession;


import client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ReceiveChatSession implements Runnable {
    private Socket clientSocket;
    private BufferedReader bufferedReader;


    public ReceiveChatSession() throws IOException {
        try {
            this.clientSocket = Client.clientSocket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

        } catch (IOException e) {
            System.out.println("[-] Receive chat session error: " + e);
            this.close_connection();
        }
    }


    @Override
    public void run() {
        try {
            while (this.clientSocket.isConnected()) {
                String recvMessage = this.bufferedReader.readLine();

                if (recvMessage == null) {
                    this.close_connection();
                    return;
                }

                else {
                    System.out.println(recvMessage); // BLOCK CODE
                }
            }

        } catch (IOException e) {
            System.out.println("[-] Receive chat session error: " + e);
            this.close_connection();
        }
    }


    private void close_connection() {
        try {
            if (clientSocket.isConnected()) {
                System.out.println("[!] Closing connection..");
                clientSocket.close();
                System.out.println("[-] Connection closed.");
            }

        } catch (IOException e) {
            System.out.println("[-] Receive chat session close error: " + e);
        }
    }
}
