package serverSide;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientInfo;
    private final String password = "hejsan123"; // Server's password

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.clientInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            // Request password
            out.write("Enter password: ");
            out.newLine();
            out.flush();

            String enteredPassword = in.readLine();
            if (!password.equals(enteredPassword)) {
                out.write("Wrong password. Disconnecting.");
                out.newLine();
                out.flush();
                return; // Exit if password is incorrect
            }

            out.write("\nCorrect password. Welcome to the server!");
            out.newLine();
            out.flush();

            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                System.out.println("Client [" + clientInfo + "]: " + messageFromClient);

                if (messageFromClient.equalsIgnoreCase("exit")) {
                    out.write("Goodbye!");
                    out.newLine();
                    out.flush();
                    break;
                }

                // Echo the message back
                out.write("Received: " + messageFromClient);
                out.newLine();
                out.flush();
            }

            System.out.println("Client disconnected: " + clientInfo);
        } catch (IOException e) {
            System.err.println("Error handling client [" + clientInfo + "]: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
