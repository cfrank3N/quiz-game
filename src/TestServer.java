import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Testserver körs på port: " + port);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Klient ansluten!");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Välkommen till testservern!");
            out.println("Här kommer ett meddelande.");
            out.println("EXIT");

            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Fel på servern: " + e.getMessage());
        }
    }

}

