/*
Project Name: Defense Communication
Developers:
    Muhilan
    Raghava
Filename: _0_client_thread.java
Title: Threading for new users and new messages
GitHub: @raghavtwenty
Date Created: May 20, 2023 | Last Updated: May 25, 2024
Language: Java | Version: 21.0.3
*/


// Package
package client;

// Importing the required modules
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// Current file main class
public class _0_client_thread extends Thread {

    // Initializing the variables
    private DatagramSocket socket;
    private byte[] incomingMsg = new byte[1024];
    private TextArea textArea;

    // Constructor for current class
    public _0_client_thread(DatagramSocket socket, TextArea textArea) {
        this.socket = socket;
        this.textArea = textArea;
    }

    // Start the thread
    @Override
    public void run() {
        System.out.println("Thread has been started.");
        while (true) {
            // Create a packet
            DatagramPacket incmgMsgPacket = new DatagramPacket(incomingMsg, incomingMsg.length);

            try {
                // Receive the message
                socket.receive(incmgMsgPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Display on the screen
            String message = new String(incmgMsgPacket.getData(), 0, incmgMsgPacket.getLength()) + "\n";
            String current = textArea.getText();
            textArea.setText(current + message);
        }
    }
}
