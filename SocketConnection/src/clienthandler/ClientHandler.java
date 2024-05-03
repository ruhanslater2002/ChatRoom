// THIS CLASS HANDLES ALL THE FUNCTIONS THAT THE CLIENT CAN PERFORM

package clienthandler;

import console.ConsoleColored;
import server.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements Runnable {
    public static ArrayList<Socket> clientConnected = new ArrayList<>();
    public static ArrayList<String> clientUsernames = new ArrayList<>();
    private String clientUsername;
    private Socket clientSocket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    // CONSTRUCTOR
    public ClientHandler(Socket clientSocket) throws IOException {
        try {
            this.clientSocket = clientSocket;
            clientConnected.add(this.clientSocket); // ADDS CLIENT TO STATIC LIST CONNECTIONS
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            // RECEIVES USERNAME
            this.clientUsername = this.bufferedReader.readLine();
            // CHECKS IF THE USERNAME IS VALID
            this.check_validity();
            // ADDS THE USERNAME TO STATIC LIST
            clientUsernames.add(this.clientUsername);
            // WRITE MESSAGE TO THIS CLIENT
            this.bufferedWriter.write(ConsoleColored.color("[+] Welcone to the server!", "green"));
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();

        } catch (IOException e) {
            System.out.println(ConsoleColored.color("[-] Client " + this.clientUsername + " " + this.clientSocket + " handle error: " + e, "red"));
            this.disconnect_client();
        }
    }


    @Override
    public void run() {
        try {
            while (!this.clientSocket.isClosed()) {
                // BLOCK CODE, LISTENS FOR CLIENT MESSAGE
                String message = this.bufferedReader.readLine();
                System.out.println(message);
                // TAKES THIS CLIENT MESSAGE AND SENDS IT OUT TO ALL CLIENTS
                this.send_out_message(message);
            }

        } catch (IOException e) {
            System.out.println(ConsoleColored.color("[-] Client handle error: " + e, "red"));
        }
    }


    private void check_validity() {
        try {
            if (clientUsernames != null) {
                System.out.println(ConsoleColored.color("[!] Checking validity of " + this.clientUsername, "yellow"));
                for (String clientUsername : clientUsernames) {
                    if (this.clientUsername.equals(clientUsername)) {
                        this.bufferedWriter.write(ConsoleColored.color("[-] Username already taken " + clientUsername, "red"));
                        this.bufferedWriter.newLine();
                        this.bufferedWriter.flush();
                        this.disconnect_client();
                        System.out.println(ConsoleColored.color("[-] Username already taken " + clientUsername + " for client " + this.clientSocket, "red"));
                        return;
                    }
                }
                System.out.println(ConsoleColored.color("[+] Username " + this.clientUsername + " is valid.", "green"));
            }

        } catch (IOException e) {
            System.out.print(ConsoleColored.color("[-] Check validity error: " + e, "red"));
        }
    }


    private void send_out_message(String message) {
        try {
            for (Socket clientSocket : clientConnected) {
                if (!clientSocket.equals(this.clientSocket)) {
                    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    this.bufferedWriter.write(ConsoleColored.color(this.clientUsername + " > ", "lightblue") + message);
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();
                }
            }

        } catch (IOException e) {
            System.out.print(ConsoleColored.color("[-] Send out message error: " + e, "red"));
        }
    }


    private void disconnect_client() throws IOException {
        if (clientConnected != null) {
            // ITERATES THROUGH THE LIST ON EVERY SOCKET CONNECTED
            for (Socket clientSocket : clientConnected) {
                // IF CURRENT SOCKET IS IN LIST
                if (this.clientSocket.equals(clientSocket)) {
                    // WILL REMOVE SOCKET FROM LIST
                    clientConnected.remove(this.clientSocket);
                    break;
                }
            }
        }

        if (this.clientSocket.isConnected()) {
            System.out.println(ConsoleColored.color("[!] Closing connection for " + this.clientSocket, "red"));
            this.clientSocket.close();
        }

        System.out.print(ConsoleColored.color("[-] Client " + this.clientSocket + " disconnected.", "red"));
    }
}
