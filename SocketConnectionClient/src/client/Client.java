// CLIENT

package client;

import ChatSession.ReceiveChatSession;
import ChatSession.SendChatSession;

import java.net.Socket;
import java.io.*;


public class Client {
    private Integer port;
    private String host;
    public static Socket clientSocket;
    public static String username;


    public Client(String host, Integer port, String username) {
        try {
            this.port = port;
            this.host = host;
            this.username = username;
            System.out.print("[+] Connecting to the server.. ");
            this.clientSocket = new Socket(this.host, this.port);
            System.out.print("Connection established.");
            Thread receiveChatSession = new Thread(new ReceiveChatSession());
            Thread sendChatSession = new Thread(new SendChatSession());
            receiveChatSession.start();
            sendChatSession.start();

        } catch (IOException e) {
            System.out.println("[-] Connection error: " + e);
            this.close_connection();
        }
    }


    private void close_connection() {
        try {
            if (clientSocket.isConnected()) {
                System.out.println("[!] Closing session.. ");
                this.clientSocket.close();
                System.out.println("[-] Session has been closed.");
            }

        } catch (IOException e) {
            System.out.println("[-] Connection end error: " + e);
        }
    }


    public static void main(String[] args) {
        new Client("localhost", 8843, "Ruhan");
    }
}

