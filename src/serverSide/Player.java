package serverSide;

import shared.User;

import java.io.*;
import java.net.Socket;

//Should send to client at start so that client always have access to the way the server views the player
public class Player {
    private int point;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player opponent;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void incrementPoint() {
        point++;
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

        point = 0;
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
