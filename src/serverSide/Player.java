package serverSide;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.Socket;

public class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private transient Socket socket;
    private transient ObjectOutputStream objectOut;
    private transient ObjectInputStream objectIn;
    private String playerName;
    private int points;
//    private static int playerIDGenerator;
//    private final int playerID = playerIDGenerator;

    public enum PlayerOrder {PLAYER_ONE, PLAYER_TWO}
    private final PlayerOrder playerOrder;

    public Player(Socket socket, String playerName, PlayerOrder playerOrder, int points) {
        this.socket = socket;
        this.playerName = playerName;
        this.playerOrder = playerOrder;
        this.points = points;

//        playerIDGenerator++;
    }

    public int getPoints() {
        return points;
    }

//    public int getPlayerID() {
//        return playerID;
//    }

    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    public void setplayerName(String playerName) {

        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerOrder getPlayerOrder() {
        return playerOrder;
    }

    public void createPlayer () {
    }
}