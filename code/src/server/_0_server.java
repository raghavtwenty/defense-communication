/*
Project Name: Defense Communication
Developers:
    Muhilan
    Raghava
Filename: _0_server.java
Title: Main server for communication
GitHub: @raghavtwenty
Date Created: May 20, 2023 | Last Updated: May 25, 2024
Language: Java | Version: 21.0.3
*/


// Package
package server;

// Importing the required modules
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

// Current file main class
public class _0_server {

    // Initializing the variables
    private static final InetAddress address;
    private static final int PORT = 1234;
    private static ArrayList<Integer> Users = new ArrayList<>();
    private static DatagramSocket socket;
    private static byte[] incomingMsg = new byte[1024];

    // Create a socket
    static {
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    // Get the current ip address
    static {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    // Main method
    public static void main(String[] args) {

        // Confirmation message
        System.out.println("Server started on port : " + PORT);

        while (true) {
            // Prepare the message packet for incoming message
            DatagramPacket incmgMsgPacket = new DatagramPacket(incomingMsg, incomingMsg.length);

            try {
                // Receive the message packet
                socket.receive(incmgMsgPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("--------------------------------------------------------");

            // Packet Analysis - Decapsulate
            // Convert packet to string
            String msgReceived = new String(incmgMsgPacket.getData(), 0, incmgMsgPacket.getLength());

            // Split the message with | as delimiter
            String[] rcdMsgSplit = msgReceived.split("\\|");
            Integer lenRcdMsgSplit = rcdMsgSplit.length;

            // Show the message contents in the server
            System.out.println("Start Flag: " + rcdMsgSplit[0]);
            System.out.println("IP Address: " + rcdMsgSplit[1]);
            System.out.println("Port Number: " + rcdMsgSplit[2]);
            System.out.println("Message Length: " + rcdMsgSplit[3]);
            System.out.println("Checksum Validator: " + rcdMsgSplit[4]);
            System.out.println("Checksum Value: " + rcdMsgSplit[5]);
            System.out.println("Byte Checking Code: " + rcdMsgSplit[6]);
            System.out.println("Actual Message: " + rcdMsgSplit[7]);
            System.out.println("End Flag: " + rcdMsgSplit[8]);

            // Original byte check
            String originalByteCheck = rcdMsgSplit[lenRcdMsgSplit - 3];
            String originalMessage = rcdMsgSplit[lenRcdMsgSplit - 2];

            // Current byte check
            Integer lenmsg = originalMessage.length();
            // Input message in bytes
            byte[] msgBytes = originalMessage.getBytes();
            // Sum of 1st and last byte
            Integer msgByteCheck = msgBytes[0] + msgBytes[lenmsg - 1];
            // Sum of 1st and last in string
            String strMsgByte = Integer.toString(msgByteCheck);

            // Byte checking validation
            if (originalByteCheck.equals(strMsgByte)) {
                System.out.println("Message validated.");
            } else {
                System.out.println("Message could not be validated.");
            }

            System.out.println("--------------------------------------------------------");

            // Get port from the packet
            int userPort = incmgMsgPacket.getPort();

            // If new user, add the current user to the users list
            if (originalMessage.contains("New User ---> ")) {
                Users.add(userPort);
            }

            // If new message, forward current message
            else {
                // Convert the string to bytes
                byte[] byteMessage = originalMessage.getBytes();

                // Forward the current message to all other users exclude sender
                for (int forwardPort : Users) {
                    // Exclude sender
                    if (forwardPort != userPort) {
                        DatagramPacket forwardMsg = new DatagramPacket(byteMessage,
                                byteMessage.length,
                                address,
                                forwardPort);
                        try {
                            // Send message
                            socket.send(forwardMsg);
                            System.out.println("--- Message Sent ---"); // Confirmation message
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
}