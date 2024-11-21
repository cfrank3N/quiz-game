package serverSide;

import java.io.*;
import java.net.Socket;

public class Player {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private int point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Player(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(this.socket.getInputStream());
            out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendToClient(String message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public Object receiveFromClient() throws ClassNotFoundException, IOException {
        return in.readObject();
    }

}
