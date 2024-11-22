package serverSide;

public class Game {
    Player winner = null;
    Player player1 = null;
    Player player2 = null;


    public void game(Player player1, Player player2){
        System.out.println("Game started");

    }

//    public void checkPoints(int player1.points, int player2.points) {
//        Player winner = (player1.points > player2.points) ? player1 : player2;
//    }

    enum GameState {
        WAITING_FOR_PLAYERS,
        START_GAME_THREAD,
    }
}