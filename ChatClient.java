import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome! Enter your username: ");
        
        String username = userInput.nextLine();
        System.out.println("Hello " + username + "!");
        System.out.println("Ready for chat now. Enter /exit to quit.");
        // Add A feature allow the user to exit the chat by typing "/exit" to break the sending loop

        try {
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress mcastaddr = InetAddress.getByName("224.2.2.3");
            NetworkInterface netIf = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            InetSocketAddress groupAddr = new InetSocketAddress(mcastaddr, PORT);
            socket.joinGroup(groupAddr, netIf);

            Thread receiver = new Thread(new Runnable() {
                public void run() {
                    byte[] inBuf = new byte[256];
                    DatagramPacket inPacket;

                    try {
                        while (true) {
                            inPacket = new DatagramPacket(inBuf, inBuf.length);
                            socket.receive(inPacket);
                            String msg = new String(inBuf, 0, inPacket.getLength());

                            InetAddress senderAddr = inPacket.getAddress();
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            });
            receiver.start();

            while (true) {
                String text = userInput.nextLine();
                
                if (text.equalsIgnoreCase("/exit")) {
                    break;
                } 
                //typing "/exit" to break the sending loop

                if (text.isEmpty()) {
                    continue;
                }
                
                String msg = username + " : " + text;
                byte[] outBuf = msg.getBytes();
                DatagramPacket outPacket = new DatagramPacket(outBuf, outBuf.length, mcastaddr, PORT);
                socket.send(outPacket);
            }

            socket.leaveGroup(groupAddr, netIf);
            socket.close();
            userInput.close();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
