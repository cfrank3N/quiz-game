package clientSide;

import packettosend.Pack;
import serverSide.Question;

import java.io.*;
import java.net.Socket;

import java.util.Scanner;

public class Client {

    public void startClient() {

        Scanner input = new Scanner(System.in); //Defines a Scanner to print messages to server. Will delete this later.

        //Initializes input and outputstreams
        try (Socket socket = new Socket("127.0.0.1", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            Object messageFromServer;

            System.out.println(in.readObject()); //Prints welcome message from server

            while (true) {
                messageFromServer = in.readObject();
                determineAction(messageFromServer);
                out.writeObject("Question Answered");
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void determineAction(Object fromServer) {
        Pack packFromServer = (Pack) fromServer;
        Scanner input = new Scanner(System.in);
        switch (packFromServer.header()) {
            case WAIT:
                System.out.println(packFromServer.object());
                break;
            case QUESTION:
                Question question = (Question) packFromServer.object();
                System.out.println(question.getQuestion());
                System.out.println(question.getSubjectQuestions());
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }
}