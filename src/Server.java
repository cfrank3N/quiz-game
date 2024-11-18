import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server () {
        int port = 1234;

        try (ServerSocket serverSock = new ServerSocket(port)) {
            System.out.println("Server started; port " + port);

            while (true) {
                try (
                    Socket socket = serverSock.accept();
                    InputStreamReader inputReader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()));
                    OutputStreamWriter outputWriter = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()));
                    BufferedReader bufferedReader = new BufferedReader(inputReader);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputWriter))
                    {

                    System.out.println("Client connected" + socket.getRemoteSocketAddress());

                    String messageToClient = "Connected to server";
                    bufferedWriter.write("server: " +messageToClient);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String messageFromClient;
                    while ((messageFromClient = bufferedReader.readLine()) != null) {
                        System.out.println("client: " + messageFromClient);

                        if (messageFromClient.equalsIgnoreCase("EXIT")) {
                            break;
                        }
                    }
                    System.out.println("client disconnected");

                } catch (IOException e) {
                    System.err.println("Error handling client connection" + e.getMessage());;
                }
        }

        } catch (IOException e) {
            System.err.println("Problem with server" +e.getMessage());
        }
    }

    public static void main(String[] args) {
       new Server();
    }
}
