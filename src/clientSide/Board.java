package clientSide;

public class Board {
    private final Player me;
    private final Player opponent;

    public Board(Player me, Player opponent) {
        this.me = me;
        this.opponent = opponent;
    }

    public Player getMe() {
        return me;
    }

    public Player getOpponent() {
        return opponent;
    }
}
