import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            // Discover server dynamically
            String serverInfo = discoverServer();
            String[] parts = serverInfo.split(":");
            String serverAddress = parts[0];
            int port = Integer.parseInt(parts[1]);

            // Connect to the discovered server
            try (
                Socket socket = new Socket(serverAddress, port);
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter serverOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in))
            ) {
                System.out.println("Connected to server: " + serverAddress + " port: " + port);

                // Read password prompt from server
                String serverMessage = serverIn.readLine();
                System.out.println("Server: " + serverMessage);

                // Send password
                System.out.print("Enter password: ");
                String password = userIn.readLine();
                serverOut.write(password);
                serverOut.newLine();
                serverOut.flush();

                // Check server response
                serverMessage = serverIn.readLine();
                System.out.println("Server: " + serverMessage);

                // Disconnect if password is incorrect
                if (serverMessage.startsWith("Wrong password")) {
                    return;
                }

                // Start thread to read server messages
                Thread readerThread = new Thread(() -> {
                    try {
                        String response;
                        while ((response = serverIn.readLine()) != null) {
                            System.out.println("Server: " + response);
                        }
                    } catch (IOException e) {
                        System.err.println("Connection to server lost: " + e.getMessage());
                    }
                });
                readerThread.start();

                // Send user messages to server
                String userMessage;
                while ((userMessage = userIn.readLine()) != null) {
                    serverOut.write(userMessage);
                    serverOut.newLine();
                    serverOut.flush();

                    if (userMessage.equalsIgnoreCase("exit")) {
                        break;
                    }
                }

                System.out.println("Connection terminated.");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String discoverServer() throws IOException {
        final String MULTICAST_GROUP = "230.0.0.0";
        final int MULTICAST_PORT = 50000;

        try (MulticastSocket socket = new MulticastSocket(MULTICAST_PORT)) {
            socket.joinGroup(InetAddress.getByName(MULTICAST_GROUP));
            System.out.println("Listening for server broadcasts...");

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);
            String serverInfo = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Discovered server: " + serverInfo);

            return serverInfo;
        }
    }
}
