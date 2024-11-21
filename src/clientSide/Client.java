package clientSide;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public void startClient() {

        Scanner input = new Scanner(System.in);

        try (Socket socket = new Socket("127.0.0.1", 55555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            String messageToServer, messageFromServer;

            System.out.println(in.readObject());

            while (true) {
                messageFromServer = (String) in.readObject();

                if (messageFromServer.contains("YOUR TURN")) {
                    System.out.println("Your turn! Search for a person");
                    messageToServer = input.nextLine();

                    if (messageToServer.equalsIgnoreCase("quit")) {
                        break;
                    }

                    out.writeObject(messageToServer);
                    out.flush();
                    messageFromServer = (String) in.readObject();
                    System.out.println(messageFromServer);
                } else if (messageFromServer.contains("WAIT")) {
                    System.out.println("Wait for the other player!");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }

}