package serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer() {

        try (ServerSocket serverSocket = new ServerSocket(55555)) {

            while (true) {
                Socket socket1 = serverSocket.accept();
                Player p1 = new Player(socket1);
                System.out.println("Client one connected");
                Socket socket2 = serverSocket.accept();
                Player p2 = new Player(socket2);
                System.out.println("Client two connected");

                Game game = new Game(p1, p2);
                game.start();
                System.out.println("Server started");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

}
