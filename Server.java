import java.net.ServerSocket;
import java.net.Socket;


class Server {
    // SERVER ATTRIBUTES
    private Integer port;
    private ServerSocket serverSocket; // FOR TO LISTEN FOR CONNECTIONS

    // CONSTRUCTOR
    public Server(Integer port) {
        // INITIATING SERVER ATTRIBUTES __init__ FUNCTION
        this.port = port;
    }


    public void start_server() throws Exception {
        try {
            // SETS SERVER SOCKET WITH A PORT
            this.serverSocket = new ServerSocket(this.port);

            // RUNS AND CHECKS FOR CONNECTION
            while (!this.serverSocket.isClosed()) {
                Socket clientSocket = this.serverSocket.accept(); // LISTENS FOR CONNECTION IN A LOOP
                System.out.println("[+] A new client has connected to the server. ");
                ServerClientHandler serverClientHandler = new ServerClientHandler(clientSocket);
                Thread thread = new Thread(serverClientHandler); // STARTS A THREAD FOR THE CLIENT, PASSES clientSocket TO INSTANCE
                thread.start();
                System.out.println("[+] Created thread for client " + clientSocket.getInetAddress());
            }
        } catch (Exception error) {
            System.out.println("[-] Starting server error: " + error);
            this.close_server();
            return;
        }
    }


    public void close_server() throws Exception {
        System.out.println("[!] Closing server..");
        this.serverSocket.close();
        System.out.println("[-] Server closed.");
        return;
    }


    public static void main(String[] args) throws Exception {
        new Server(8843).start_server();
    }
}
