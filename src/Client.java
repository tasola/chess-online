import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.awt.*;

public class Client extends Thread
{
    private Board board;
    private int port;
    private String host;
    private int receiverPort;
    private DatagramSocket datagramSocket;

    // Setup the client with given connection information
    public Client(String[] customConnection, Board board) {
        this.board = board;
        if (customConnection.length == 3) {
            this.port = Integer.parseInt(customConnection[0]);
            this.host = customConnection[1];
            this.receiverPort = Integer.parseInt(customConnection[2]);
            setupDatagramSocket();
            this.start();
        } else {
            System.out.println("Not enough information! Provide <port> <host> <receiverPort>!");
            System.exit(0);
        }
    }

    public void setupDatagramSocket() {
        try {
            this.datagramSocket = new DatagramSocket(this.port);
        } catch (SocketException se) {
            System.out.println("Client setupDatagramSocket() SocketException" + se);
        }
    }

    // Use a DatagramPacket to send the point to the receiverPort (the other client)
    public void send(Integer xPos, Integer yPos) {
        String point = xPos + " " + yPos;
        try {
            byte[] byteArr = point.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(byteArr, byteArr.length, InetAddress.getByName(this.host), this.receiverPort);
            datagramSocket.send(datagramPacket);
        }
        catch (IOException ioe) {
            System.out.println("Client send() IOException" + ioe);
        }
    }

    // Listen for incoming UDP packages, convert them to points and plot them out
    public void run() {
        try {
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
                datagramSocket.receive(datagramPacket);
                this.board.opponentMove(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
