package clientSide;

import questions.Question;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAdress = "localhost";
        int port = 55555;

        try (Socket socket = new Socket(serverAdress, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream()))
        {

            System.out.println("Ansluten till servern: " + serverAdress + " på port: " + port);
            Object toServer = new String("Hej");
            Object serverMessage;
            while ((serverMessage = in.readObject()) != null) {
                out.writeObject(toServer);
                Question q = (Question) serverMessage;
                System.out.println("Servern säger; " + q.getCorrectAnswer());
            }


        } catch (IOException e) {
            System.out.println("Kunde inte ansluta till servern: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
