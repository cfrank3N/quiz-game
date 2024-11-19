package serverSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientInfo;
    private final BufferedReader in;
    private final BufferedWriter out;

//    private final String password = "hejsan123"; // Server's password

    public ClientHandler(Socket socket, BufferedReader in, BufferedWriter out) {
        this.socket = socket;
        this.clientInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Client " + clientInfo + ": " + messageFromClient);

                if (messageFromClient.equalsIgnoreCase("EXIT")) {
                    out.write("Goodbye");
                    out.newLine();
                    out.flush();
                    break;
                }
            }
            System.out.println("Client disconnected: " + clientInfo);

        } catch (IOException e) {
            System.err.println("Error handling client: " + clientInfo);
        } finally {
            try{ socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + clientInfo);
            }
        }
    }
}
