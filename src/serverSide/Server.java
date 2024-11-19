package serverSide;

import java.io.*;
import java.net.*;

public class Server {
    private final int PORT = 50001;
    private final String MULTICAST_GROUP = "230.0.0.0";
    private final int MULTICAST_PORT = 50000;

    public static void main(String[] args) {
       new Server().startServer();
    }
    public void startServer () {

        String serverInfo = getLocalIPAddress() + ":" + PORT;
        Thread multicastThread = new Thread(new MulticastBroadcaster(MULTICAST_GROUP, MULTICAST_PORT, serverInfo));
        multicastThread.start();

        try (ServerSocket serverSock = new ServerSocket(PORT)) {
            System.out.println("Server started: port " + PORT);

            while (true) {
                Socket clientSocket = serverSock.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Problem with server: " + e.getMessage());
        }
    }

    private String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to get local IP address" +e.getMessage());
        }
    }
}
