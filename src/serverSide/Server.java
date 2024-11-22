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
    private final List<Player> players = new ArrayList<>();
    BlockingDeque<Player> playerQueue = new LinkedBlockingDeque<>();

    public Server () {
    }

    public void startServer () {
        try (ServerSocket serverSock = new ServerSocket(PORT)) {
            System.out.println("Server started: port " + PORT);

            while (true) {
                Socket clientSocket = serverSock.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());



                new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                Thread gameThread = new Thread();
                gameThread.start();

                Player player = new Player(clientSocket, "name", Player.PlayerOrder.PLAYER_ONE,0);


            }
        } catch (IOException e) {
            System.err.println("Problem with server" + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            out.writeObject("Enter username: ");
            String playerName = (String) in.readObject();

            Player.PlayerOrder order = players.size() % 2 == 0
                    ? Player.PlayerOrder.PLAYER_ONE
                    : Player.PlayerOrder.PLAYER_TWO;

            Player newPlayer = new Player(clientSocket, playerName, order, 0);

            players.add(newPlayer);
            players.add(newPlayer);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
       new Server().startServer();
    }
}