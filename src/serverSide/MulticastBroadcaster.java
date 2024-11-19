package serverSide;

import java.io.*;
import java.net.*;

public class MulticastBroadcaster implements Runnable {

    private final String multicastGroup;
    private final int multicastPort;
    private final String serverInfo;

    public MulticastBroadcaster(String multicastGroup, int multicastPort, String serverInfo) {
        this.multicastGroup = multicastGroup;
        this.multicastPort = multicastPort;
        this.serverInfo = serverInfo;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(multicastGroup);

            while (true) {
                byte[] buffer = serverInfo.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, multicastPort);
                socket.send(packet);
                System.out.println("Broadcasting server info: " + serverInfo);
                Thread.sleep(2000);
            }
        } catch (UnknownHostException e) {
            System.err.println("Error broadcasting info: " + e.getMessage());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}