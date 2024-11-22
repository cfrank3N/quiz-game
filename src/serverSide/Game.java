package serverSide;

public class Game extends Thread {

    private final Player p1;
    private final Player p2;
    private Player currentPlayer;

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
    }

    public void run() {

        try {
            String messageFromClient, messageToClient;

            messageToClient = "Welcome to the server!";
            p1.sendToClient(messageToClient);
            p2.sendToClient(messageToClient);

            //Sets opponent as each other.
            p1.setOpponent(p2);
            p2.setOpponent(p1);

            //Listens to and sends info to the current player.
            while (true) {
                currentPlayer.sendToClient("YOUR TURN"); //Sends message to client
                messageFromClient = (String) currentPlayer.receiveFromClient(); //Receives message from client
                messageToClient = "Acceptable input\n" + messageFromClient;
                currentPlayer.sendToClient(messageToClient); //Sends back a message + what the client sent
                currentPlayer.sendToClient("WAIT"); //Sends wait to client to prompt it to wait for a new message from the server and to let the other player "play"
                currentPlayer = currentPlayer.getOpponent(); //Changes to the other player.
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
