import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAdress = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(serverAdress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Ansluten till servern: " + serverAdress + " på port: " + port);

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Servern säger; " + serverMessage);
            }


        } catch (IOException e) {
            System.out.println("Kunde inte ansluta till servern: " + e.getMessage());
        }


    }
}
