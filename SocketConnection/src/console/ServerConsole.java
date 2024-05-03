// CONSOLE COMMANDS COME HERE

package console;

import clienthandler.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerConsole implements Runnable {
    ServerSocket serverSocket;


    public ServerConsole(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    @Override
    public void run() {
        try {
            while (!this.serverSocket.isClosed()) {
                Scanner scanner = new Scanner(System.in);
//                System.out.print(ConsoleColored.color("CONSOLE: ", "lightblue"));
                String serverArgs = scanner.nextLine();
                if (serverArgs.equals("stop")) {
                    System.out.println(ConsoleColored.color("[!] Closing server socket..", "yellow"));
                    scanner.close();
                    // FIRST CLOSES ALL CLIENT CONNECTION
                    if (ClientHandler.clientConnected != null) {
                        for (Socket clientConnected : ClientHandler.clientConnected) {
                            clientConnected.close();
                        }
                    }
                    this.serverSocket.close();
                    return;
                }
            }

        } catch (Exception e) {
            System.out.println(ConsoleColored.color("[-] Console error: " + e, "red"));
        }
    }
}
