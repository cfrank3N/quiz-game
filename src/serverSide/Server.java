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
        new Thread(this::startMulticastBroadcaster).start();

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

    private void startMulticastBroadcaster() {
        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            String message = InetAddress.getLocalHost().getHostAddress() + ":" + PORT;

            while (true) {
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
                socket.send(packet);
                    System.out.println("Sending server info: " +message);
                    Thread.sleep(2000);
            }
    } catch (Exception e) {
            System.err.println("Problem with server: " + e.getMessage());
        }
}
}
