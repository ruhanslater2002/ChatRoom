// THIS CLASS HANDLES ALL THE FUNCTIONS THAT THE SERVER CAN PERFORM

package server;

import clienthandler.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import console.ConsoleColored;
import console.ServerConsole;


public class Server implements SocketServer {
    ServerSocket serverSocket;
    Socket clientSocket;


    public Server(Integer serverPort) {
        try {
            System.out.println(ConsoleColored.color("[+] STARTING THE SERVER..", "green"));
            this.serverSocket = new ServerSocket(serverPort);
            this.start_server();
            this.stop_server();

        } catch (IOException e) {
            System.out.println(ConsoleColored.color("[-] Server error: " + e, "red"));
        }
    }


    @Override
    public void start_server() {
        try {
            // STARTS SERVER COMMAND CONSOLE
            Thread console = new Thread(new ServerConsole(this.serverSocket));
            console.start();

            while (!this.serverSocket.isClosed()) {
                this.clientSocket = this.serverSocket.accept(); // BLOCK CODE, WAITS FOR CONNECTION
                System.out.println(ConsoleColored.color("[+] Connection from " + this.clientSocket.getInetAddress(), "green"));
                ClientHandler clientHandler = new ClientHandler(this.clientSocket); // CREATE CLIENT INSTANCE
                Thread client = new Thread(clientHandler); // PASSING OBJECT CLASS FOR THREADING
                client.start(); // STARTS THREADING AND CONTINUES WITH THE CODE
            }

        } catch (IOException e) {
            System.out.println(ConsoleColored.color("[-] Server: " + e, "red"));
            this.stop_server();
        }
    }


    @Override
    public void stop_server() {
        try {
            if (!this.serverSocket.isClosed()) {
                this.clientSocket.close();
                this.serverSocket.close();
            }

        } catch (IOException e) {
            System.out.println(ConsoleColored.color("[-] Server stoping error: " + e, "red"));
        }
    }


    // SERVER RUNS FROM CONSOLE
    public static void main(String[] args) {
        new Server(8843);
    }
}
