import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientInfo;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.clientInfo = socket.getInetAddress().getHostAddress() + ":" +socket.getPort();

    }

    public void run() {
        try (
            InputStreamReader inputReader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()));
            OutputStreamWriter outputWriter = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputWriter)
        ) {
            String messageToClient = "Connected to server";
            bufferedWriter.write("server: " + messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String messageFromClient;
            while ((messageFromClient = bufferedReader.readLine()) != null) {
                System.out.println("client: " +clientInfo +" " +messageFromClient);

                if (messageFromClient.equalsIgnoreCase("EXIT")) {
                    bufferedWriter.write("server: Goodbye.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    break;

                }

                bufferedWriter.write("server: received" + messageFromClient);
                bufferedWriter.newLine();
                bufferedWriter.flush();
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
