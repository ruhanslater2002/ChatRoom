// SERVER STARTING INTERFACE

package server;


import java.io.IOException;

interface SocketServer {
    public void start_server() throws IOException;
    public void stop_server() throws IOException;
}
