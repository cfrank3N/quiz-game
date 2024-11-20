package serverSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT = 50001;

    public static void main(String[] args) {
       new Server().startServer();
    }
    public void startServer () {
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
            System.err.println("Problem with server" + e.getMessage());
        }
    }
}