package serverSide;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientInfo;
    private final String password = "hejsan123";


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
            out.write("Enter password och test: \n");
            out.newLine();
            out.flush();

            String enteredPassword = in.readLine();
            if (!password.equals(enteredPassword)) {
                out.write("Wrong password\n");
                out.newLine();
                out.flush();
                return;
            }

            out.write("Correct password, connected to server");
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

                out.write("received" + messageFromClient);
                out.newLine();
                out.flush();
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
