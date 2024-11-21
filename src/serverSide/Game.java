package serverSide;

public class Game extends Thread {

    private final Player p1;
    private final Player p2;

    public Game(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void run() {

        try {
            String personName, messageToClient;

            messageToClient = "Welcome to the server!";
            p1.sendToClient(messageToClient);
            p2.sendToClient(messageToClient);

            while (true) {
                p1.sendToClient("YOUR TURN");
                personName = (String) p1.receiveFromClient();
                messageToClient = "Acceptable input" + personName;
                p1.sendToClient(messageToClient);
                p1.sendToClient("WAIT");
                p2.sendToClient("YOUR TURN");
                personName = (String) p2.receiveFromClient();
                messageToClient = "Acceptable input" + personName;
                p2.sendToClient(messageToClient);
                p2.sendToClient("WAIT");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
