package serverSide;

import java.io.*;
import java.net.Socket;

public class Player {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private int point;
    private Player opponent;

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    //Constructor taking in a socket and defining the input and outputstreams for the player.
    public Player(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(this.socket.getInputStream());
            out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    //Sends a message in the form of an object to the client
    public void sendToClient(Object objectToSend) throws IOException {
        out.writeObject(objectToSend);
        out.flush();
    }

    //Receives a message in the form of an object to the client
    public Object receiveFromClient() throws ClassNotFoundException, IOException {
        return in.readObject();
    }

}
