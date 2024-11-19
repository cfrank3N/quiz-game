package clientSide;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastDiscovery {
    private final String multicastGroup;
    private final int multicastPort;

    public MulticastDiscovery(String multicastGroup, int multicastPort) {
        this.multicastGroup = multicastGroup;
        this.multicastPort = multicastPort;
    }

    public String discoverServer() {
        try (MulticastSocket socket = new MulticastSocket(multicastPort)) {
            socket.joinGroup(InetAddress.getByName(multicastGroup));
            System.out.println("Listening for server.");

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);
            String serverInfo = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Discovered server" +serverInfo);

            return serverInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}