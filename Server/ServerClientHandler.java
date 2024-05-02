import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


class ServerClientHandler implements Runnable {
    private static ArrayList<Socket> clientsConnected = new ArrayList<>();
    private Socket clientSocket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String clientUsername;


    // INITIATING INSTANCE OF CLASS ATTRIBUTES
    public ServerClientHandler(Socket clientSocket) throws Exception {
        try {
            this.clientSocket = clientSocket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); // RECEIVE HANDLING // ONLY READS FROM CURRENT
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream())); // SEND HABDLING TO SOCKET CONNECTED
            this.bufferedWriter.write("USERNAME: ");
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            this.clientUsername = bufferedReader.readLine(); // READS THE LINE AS A STRING THAT THE CLIENT SENDS THAT WILL BE THE USERNAME // BLOCK CODE
            clientsConnected.add(this.clientSocket); // TAKES ServerClientHandler OBJECT AND STORES IT IN clientsConnected ARRAY LIST AS A ServerClientHandler OBJECT
            System.out.println("SERVER: " + this.clientUsername + " has joined the server."); // PRINTS TO CONSOLE
            this.broadcast_message("SERVER: " + this.clientUsername + " has joined the server."); // SENDS BROADCAST MESSAGE, WILL USE BufferWriter TO DO THAT

        } catch (Exception error) {
            System.out.println("[-] ServerClientHandler error: " + error);
            close_client_connection();
        }
    }


    public void broadcast_message(String messageToSend) throws Exception {
        for (Socket clientSocket : clientsConnected) { // FOR EACH clientConnection IN clientsConnected
            try {
                if (!clientSocket.equals(this.clientSocket)) { // IF NOT THIS USERNAME, IT WILL SEND TO THE CLIENT
                    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); // SEND HABDLING TO SOCKET CONNECTED
                    this.bufferedWriter.write(messageToSend); // SENDS TO THE CLIENT
                    this.bufferedWriter.newLine(); // ACTS LIKE ENTER KEY FOR CLIENT, DONE SENDING DATA NO NEED TO WAIT FOR THE CLIENT
                    this.bufferedWriter.flush(); // FLUSH BUFFER
                }

            } catch (Exception error) {
                System.out.println("[-] Broadcast message error: " + error);
                this.close_client_connection();
            }
        }
    }


    public void close_client_connection() throws Exception {
        clientsConnected.remove(this);
        this.broadcast_message("SERVER: " + this.clientUsername + " has left the chat!");
        this.clientSocket.close();
    }


    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                String messageFromClient = this.bufferedReader.readLine(); // BLOCKING OPPERATION, RETURNS AS STRING
                broadcast_message(messageFromClient);

            } catch (Exception error) {
                System.out.println("[-] Broadcast message error: " + error);
                return;
            }
        }
    }
}
