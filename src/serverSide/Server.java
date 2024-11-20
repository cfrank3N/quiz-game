package serverSide;

import java.io.*;
import java.net.*;

public class Server {
    private final int PORT = 50001;
    private final String MULTICAST_GROUP = "230.0.0.0";
    private final int MULTICAST_PORT = 50000;
    private final String pw = "hej";

    public static void main(String[] args) {
       new Server().startServer();
    }
    public void startServer () {
        String serverInfo = getLocalIPAddress() + ":" + PORT;
        Thread multicastThread = new Thread(new MulticastBroadcaster(MULTICAST_GROUP, MULTICAST_PORT, serverInfo));
        multicastThread.start();

        try (ServerSocket serverSock = new ServerSocket(PORT)) {
            System.out.println("Server started: port " + PORT);

            while (true) {
                Socket clientSocket = serverSock.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                boolean isAuthenticated = authenticateClient(clientSocket, in, out);

                if (!isAuthenticated) {
                    clientSocket.close();
                    continue;
                }
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, in, out);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            }
        } catch (IOException e) {
            System.err.println("Problem with server: " + e.getMessage());
        }
    }

    private boolean authenticateClient(Socket clientSocket, BufferedReader in, BufferedWriter out) {
        try{
            out.write("Enter password:\n");
            out.newLine();
            out.flush();

            String enteredPassword = in.readLine();
            if (!pw.equals(enteredPassword)) {
                out.write("Wrong password. Disconnecting.");
                out.newLine();
                out.flush();
                System.out.println("Invalid password attempted" + clientSocket.getRemoteSocketAddress());
                return false;
            }

            out.write("Correct password. Welcome to the server!");
            out.newLine();
            out.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Problem with server: " + e.getMessage());
            return false;
        }
    }

    private String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Failed to get local IP address" +e.getMessage());
        }
    }
}
