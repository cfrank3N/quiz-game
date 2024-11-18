package clientSide;

public class Player {

    private static int playerIDGenerator;
    private final int playerID = playerIDGenerator;
    private int points;
    private final String playerName;

    public enum PlayerOrder {PLAYER_ONE, PLAYER_TWO}
    private final PlayerOrder playerOrder;

    public Player(String playerName, PlayerOrder playerOrder) {
        this.playerName = playerName;
        this.playerOrder = playerOrder;
        playerIDGenerator++;
    }

    public int getPoints() {
        return points;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerOrder getPlayerOrder() {
        return playerOrder;
    }
}
