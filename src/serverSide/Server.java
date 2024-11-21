package serverSide;
import serverSide.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/*
* starta serverSocket
* vänta på client, accept();
* skapa Player1 ?
* lägg till player1 i lista och Queue
* ett game är en tråd?
* */



public class Server {
    private final int PORT = Constants.PORT;
    private List<Player> players = new ArrayList<>();

    public Server () {
        BlockingDeque<Player> playerQueue = new LinkedBlockingDeque<>();
    }

    public void startServer () {
        try (ServerSocket serverSock = new ServerSocket(PORT)) {
            System.out.println("Server started: port " + PORT);

            while (true) {
                Socket clientSocket = serverSock.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                Player player =

            }
        } catch (IOException e) {
            System.err.println("Problem with server" + e.getMessage());
        }
    }

    public static void main(String[] args) {
       new Server().startServer();
    }
}