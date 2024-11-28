package shared;

import java.io.Serializable;
import java.util.List;

public class Scoreboard implements Serializable {
    private int round;
    private List<Integer> me;

    @Override
    public String toString() {
        return "Scoreboard{" +
                "me=" + me +
                ", opponent=" + opponent +
                '}';
    }

    private List<Integer> opponent;

    public List<Integer> getMe() {
        return me;
    }

    public List<Integer> getOpponent() {
        return opponent;
    }

    public void setMe(List<Integer> me) {
        this.me = me;
    }

    public void setOpponent(List<Integer> opponent) {
        this.opponent = opponent;
    }

    public int getRound() {
        return round;
    }

    public Scoreboard(List<Integer> me, List<Integer> opponent, int round) {
        this.round = round;
        this.me = me;
        this.opponent = opponent;
    }
}
