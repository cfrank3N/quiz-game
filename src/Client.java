import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAdress = "localhost";
        int port = 55555;

        try (
            Socket socket = new Socket(serverAdress, port);
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter serverOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server: " + serverAdress + " port: " + port);

            String serverMessage;
            if ((serverMessage = serverIn.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
            }

            String password = userIn.readLine();
            serverOut.write(password);
            serverOut.newLine();
            serverOut.flush();

            Thread readerThread = new Thread(() -> {
                String response;
                try {
                    while ((response = serverIn.readLine()) != null) {
                        System.out.println("Server: " +response);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            readerThread.start();


            String userMessage;
            while ((userMessage = serverIn.readLine()) != null) {
                serverOut.write(userMessage);
                serverOut.newLine();
                serverOut.flush();

                if (userMessage.equalsIgnoreCase("exit")) {
                    break;
                }
            }
            socket.close();
            System.out.println("connection terminated");


        } catch (IOException e) {
            System.out.println("Unable to connect to server: " + e.getMessage());
        }
    }
}