package clientSide;

import java.io.*;
import java.net.Socket;

import java.util.Scanner;

public class Client {

    public void startClient() {

        Scanner input = new Scanner(System.in); //Defines a Scanner to print messages to server. Will delete this later.

        //Initializes input and outputstreams
        try (Socket socket = new Socket("127.0.0.1", 55555);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            String messageToServer, messageFromServer;

            System.out.println(in.readObject()); //Prints welcome message from server

            while (true) {
                messageFromServer = (String) in.readObject(); //Listens to server to determine what to do

                if (messageFromServer.contains("YOUR TURN")) {  //Checks serverMessage and determines what to do.
                    System.out.println("Your turn! Search for a person"); //Message to client from client
                    messageToServer = input.nextLine(); //Takes in input from user

                    if (messageToServer.equalsIgnoreCase("quit")) { //If it is "quit" then exit the loop
                        break;
                    }

                    out.writeObject(messageToServer); //Write message to server
                    out.flush();
                    messageFromServer = (String) in.readObject(); //Recieve message from server again
                    System.out.println(messageFromServer); //Print server message to client
                } else if (messageFromServer.contains("WAIT")) { //Waits for a new reply from the server if the initial serverMessage contained "WAIT"
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