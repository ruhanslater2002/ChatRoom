import java.io.*;
import java.net.Socket;


class Client {
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Socket clientSocket;
    private String username;
    private String host;
    private Integer port;

    // CONSTRUCTOR
    public Client(String username, String host, Integer port) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.connect();

    }


    public void connect() {
        try {
            this.clientSocket = new Socket(this.host, this.port); // CONNECTS TO SERVER
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Thread clientReceiveHandler = new Thread(new ClientReceiveHandler(this.clientSocket, this.bufferedReader));
            Thread clientSendHandler = new Thread(new ClientSendHandler(this.clientSocket, this.bufferedWriter));
            clientSendHandler.start(); // STARTS THE SEND HANDLER AND CONTINUES CODE
            clientReceiveHandler.start(); // STARTS THE RECEIVE HANDLER AND CONTINUES CODE

        } catch (IOException error) {
            System.out.println("[-] Connection error: " + error);
            this.close_connection();
        }
    }


    public void close_connection() {
        try {
            System.out.println("[-] Closing client connection.");
            this.clientSocket.close();
            this.bufferedReader.close();
            this.bufferedWriter.close();

        } catch (IOException error) {
            System.out.println("[-] Receive handler error: " + error);
        }
    }


    public static void main(String[] args) {
        new Client("Ruhan", "localhost", 8843);
    }
}
