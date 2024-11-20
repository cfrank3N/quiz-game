package serverSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientInfo;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.clientInfo = socket.getInetAddress().getHostAddress() + ":" +socket.getPort();
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            out.write("Hello!");
            out.newLine();
            out.flush();

            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("client: " +clientInfo +" " +messageFromClient);
                if (messageFromClient.equalsIgnoreCase("EXIT")) {
                    out.write("server: Goodbye!");
                    out.newLine();
                    out.flush();
                    break;
                }
            }

            System.out.println("client disconnected");

        } catch (IOException e) {
            System.err.println("Error handling client connection" + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket" + e.getMessage());
            }
        }
    }
}