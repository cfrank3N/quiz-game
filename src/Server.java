import serverSide.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server () {
        int port = 1234;
        boolean bothPlayersConnected = false;

        try (ServerSocket serverSock = new ServerSocket(port)) {
            System.out.println("Server started; port " + port);

            while (true) {
                Socket p1sock = serverSock.accept();
                System.out.println("Player1 connected" + p1sock.getRemoteSocketAddress());
                Socket p2sock = serverSock.accept();
                System.out.println("Player2 connected" + p2sock.getRemoteSocketAddress());

                ObjectOutputStream p1Out = new ObjectOutputStream(p1sock.getOutputStream());
                ObjectInputStream p1In = new ObjectInputStream(p2sock.getInputStream());
                ClientHandler p1Handler = new ClientHandler(p1sock);

                ObjectOutputStream p2Out = new ObjectOutputStream(p2sock.getOutputStream());
                ObjectInputStream p2In = new ObjectInputStream(p2sock.getInputStream());
                ClientHandler p2Handler = new ClientHandler(p2sock);

                Thread p1Thread = new Thread(p1Handler);
                Thread p2Thread = new Thread(p2Handler);

                p1Thread.start();
                Thread.sleep(100);
                p2Thread.start();
            }
        } catch (IOException e) {
            System.err.println("Problem with server" + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
       new Server();
    }
}
