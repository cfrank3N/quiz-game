package serverSide;

import database.QuestionRepository;
import packettosend.Pack;
import enums.GameState;
import enums.States;

import java.util.ArrayList;
import java.util.List;

import static enums.ESubject.SUBJECT3;
import static enums.GameState.FIRST_STEP;
import static enums.GameState.SECOND_STEP;

public class Game extends Thread {

    private final Player p1;
    private final Player p2;
    private Player currentPlayer;
    private final QuestionRepository db = new QuestionRepository();
    private GameState status = FIRST_STEP;

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
    }

    public void run() {

        try {
            Object messageFromClient, messageToClient;

            messageToClient = "Welcome to the server!";
            p1.sendToClient(messageToClient);
            p2.sendToClient(messageToClient);

            //Sets opponent as each other.
            p1.setOpponent(p2);
            p2.setOpponent(p1);

            determineAction(null);
            //Listens to and sends info to the current player.
            while (true) {
                messageFromClient = currentPlayer.receiveFromClient();
                determineAction(messageFromClient);
                /*
                currentPlayer.sendToClient(new Pack(States.PLAYING, "Your turn to play")); //Sends message to client
                currentPlayer.getOpponent().sendToClient(new Pack(States.WAITING, "Waiting for opponent"));
                messageFromClient = (String) currentPlayer.receiveFromClient(); //Receives message from client
                messageToClient = "Acceptable input\n" + messageFromClient;
                currentPlayer.sendToClient(messageToClient); //Sends back a message + what the client sent
                currentPlayer.sendToClient("WAIT"); //Sends wait to client to prompt it to wait for a new message from the server and to let the other player "play"
                currentPlayer = currentPlayer.getOpponent(); //Changes to the other player.

                */
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void determineAction(Object objectFromClient) {
        try {
            switch (status) {
                case FIRST_STEP:
                    currentPlayer.getOpponent().sendToClient(new Pack(States.WAIT, "Waiting for opponent"));
                    currentPlayer.sendToClient(new Pack(States.QUESTION, db.oneBySubject(SUBJECT3)));
                    currentPlayer.receiveFromClient();
                    break;
                default:
                    break;
            }
            //objectFromClient = currentPlayer.receiveFromClient();
            //currentPlayer = currentPlayer.getOpponent();
            //currentPlayer.receiveFromClient();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
