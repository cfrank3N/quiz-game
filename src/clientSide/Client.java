package clientSide;

import enums.ESubject;
import enums.States;
import packettosend.Pack;
import shared.Question;
import shared.PlayerDTO;
import shared.ScoreboardDTO;
import shared.User;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

import java.util.List;
import java.util.Scanner;

public class Client {
    private Board board;
    private User user;

    public void startClient() {
        //Login
        user = new User("Guest", "", "", 0, "src/avatars/basic_boy.png");

        //Initializes input and outputstreams
        try (Socket socket = new Socket("127.0.0.1", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {

            Object messageFromServer;

            while (true) {
                messageFromServer = in.readObject();
                determineAction(messageFromServer, out);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void determineAction(Object fromServer, ObjectOutputStream out) throws IOException {
        Pack packFromServer = (Pack) fromServer;
        Scanner scan = new Scanner(System.in);
        switch (packFromServer.header()) {
            case WELCOME:
                System.out.println("Welcome to the game.");
                break;
            case SEND_USER:
                out.writeObject(new Pack(States.PLAYER_DTO, user));
                break;
            case PLAYER_DTO:
                PlayerDTO opponentInformation = (PlayerDTO) packFromServer.object();

                Player me = new Player(0, user.getUsername(), new ImageIcon(user.getAvatarPath()));
                Player opponent = new Player(0, opponentInformation.name(), new ImageIcon(opponentInformation.avatarPath()));

                board = new Board(me, opponent);
                break;

            case CHOOSE_CATEGORY:
                List<ESubject> catagories = (List<ESubject>)packFromServer.object();
                System.out.println(catagories);
                String chosenSubject = scan.nextLine();
                for (ESubject s : catagories) {
                    if (chosenSubject.equalsIgnoreCase(s.getNameText())) {
                        out.writeObject(new Pack(States.CATEGORY, s));
                    }
                }

                break;
            case WAIT:
                System.out.println(packFromServer.object());
                break;
            case SEND_ANSWER:
                System.out.println("Your turn");
                Question question = (Question) packFromServer.object();
                System.out.println(question.getQuestion());
                System.out.println(question.getSubjectQuestions());
                System.out.print("Answer: ");
                String input = scan.nextLine();
                System.out.println();
                out.writeObject(new Pack(States.GUESS, input));
                break;
            case SCOREBOARD_DTO:
                //Update
                ScoreboardDTO scoreboardDTO = (ScoreboardDTO) packFromServer.object();
                board.getMe().setPoints(scoreboardDTO.you());
                board.getOpponent().setPoints(scoreboardDTO.opponent());

                //View
                System.out.println("Current score: ");
                System.out.println("You: " + board.getMe().getPoints());
                System.out.println("Opponent: " + board.getOpponent().getPoints());
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }
}