/*
Project Name: Defense Communication
Developers:
    Muhilan
    Raghava
Filename: _1_client.java
Title: Client application with JavaFX GUI
GitHub: @raghavtwenty
Date Created: May 20, 2023 | Last Updated: May 25, 2024
Language: Java | Version: 21.0.3
*/


// Package
package client;

// Importing the required modules
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Current file main class
public class _1_client extends Application {

    // Variable initialization
    private static final InetAddress address;
    private static final int PORT = 1234;
    private static final DatagramSocket socket;
    public static String identifier = "";
    private static final TextArea messageArea = new TextArea();
    private static final TextField inputBox = new TextField();

    // Create a socket
    static {
        try {
            socket = new DatagramSocket();
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

    // Encapsulation process
    public static String Encapsulation(String message) {

        // Current date time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String currDateTime = currentDateTime.format(dateTimeFormat);

        // Add current time in front of message
        message = currDateTime + " --- " + message;

        // Packet Analysis - Start of Encapsulation
        String startFlag = "0"; // Start flag

        String ipAdd = address.toString(); // Ip address

        String poNo = Integer.toString(PORT); // Port number

        Integer msgInputLen = message.length(); // Message length in integer
        String strMsgInputLen = Integer.toString(msgInputLen); // Message length in string

        byte[] msgBytes = message.getBytes(); // Input message in bytes

        Integer msgByteCheck = msgBytes[0] + msgBytes[msgInputLen - 1]; // Sum of 1st and last byte
        String strMsgByte = Integer.toString(msgByteCheck); // Sum of 1st and last in string

        Integer checkCode = 2; // Checksum code
        String strCheckCode = Integer.toString(checkCode); // String checksum code

        Integer msgCheckCode = msgInputLen % 2; // Remainder code
        String strCheckSum = Integer.toString(msgCheckCode); // Remainder code string

        String endFlag = "0";

        // Make it a whole string
        String encapMsg = startFlag + "|" + ipAdd + "|" + poNo + "|" + strMsgInputLen + "|" + strCheckCode + "|"
                + strCheckSum + "|" + strMsgByte + "|" + message + "|" + endFlag;
        // Packet Analysis - End of Encapsulation

        return encapMsg;
    }

    // Send the message in to the socket
    public static void sendMessage(String currMessage) {
        String encapMsg = Encapsulation(currMessage);
        byte[] sendMsgBytes = encapMsg.getBytes(); // Convert whole packet to bytes

        // Create a data gram packet
        DatagramPacket sendMsg = new DatagramPacket(sendMsgBytes, sendMsgBytes.length, address, PORT);

        try {
            // Send the message
            socket.send(sendMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Button on action send message
    public static void buttonSendMsg(String newMessage) {
        // Update message on the screen
        messageArea.setText(messageArea.getText() + newMessage + "\n");
        inputBox.setText(""); // Reset input GUI box

        // Call the send message function and pass the current message
        sendMessage(newMessage);
    }

    // Main method
    public static void main(String[] args) throws IOException {

        // Thread for receiving messages
        _0_client_thread clientThread = new _0_client_thread(socket, messageArea);
        clientThread.start();

        String msg = "New User ---> " + identifier;
        String en_msg = Encapsulation(msg);

        // send initialization message to the server
        byte[] uuid = en_msg.getBytes();
        DatagramPacket initialize = new DatagramPacket(uuid, uuid.length, address, PORT);
        socket.send(initialize);

        launch(); // launch GUI
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the login page
        Stage loginStage = new Stage();
        GridPane loginWindow = createLoginPage(loginStage);

        // Create the chat page
        Stage chatStage = new Stage();
        GridPane chatWindow = createChatPage(chatStage);

        // Set the login stage
        Scene loginScene = new Scene(loginWindow, 360, 300);
        loginStage.setScene(loginScene);
        loginStage.setTitle("Defense Communication");
        loginStage.show();

        // Set the chat stage, initially hidden
        Scene chatScene = new Scene(chatWindow, 400, 700);
        chatStage.setScene(chatScene);
        chatStage.setTitle("Defense Communication");
    }

    // GUI - Login page
    private GridPane createLoginPage(Stage loginStage) {
        GridPane loginWindow = new GridPane();
        loginWindow.setVgap(10);
        loginWindow.setHgap(20);
        loginWindow.setPadding(new Insets(20));

        // Username in login page
        Label usernameLabel = new Label("Username ");
        TextField usernameField = new TextField();

        // Password in login page
        Label passwordLabel = new Label("Password ");
        PasswordField passwordField = new PasswordField();

        // Login button in login page
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Check username and password
            if ((username.equals("alpha") && password.equals("1234")) ||
                (username.equals("beta") && password.equals("5678")) ||
                (username.equals("gamma") && password.equals("9012"))) {

                // Set the identifier to the username
                identifier = username;

                // Hide the login page
                loginStage.close();

                // Show the chat page
                Stage chatStage = (Stage) messageArea.getScene().getWindow();
                chatStage.show();

            } else {
                // If username and password doesn't match
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            }
        });

        // Developers
        Label devLabel = new Label("Designed & Developed by");
        Label authorsLabel = new Label("Muhilan & Raghava");
        Label gitLabel = new Label("GitHub: @raghavtwenty");
        Label datesLabel = new Label("Date Created: May 20, 2023 | Last Updated: May 25, 2024");

        // Set the attributes on the window
        loginWindow.add(usernameLabel, 0, 0);
        loginWindow.add(usernameField, 0, 1);
        loginWindow.add(passwordLabel, 0, 2);
        loginWindow.add(passwordField, 0, 3);
        loginWindow.add(loginButton, 0, 4);
        loginWindow.add(devLabel, 0, 5);
        loginWindow.add(authorsLabel, 0, 6);
        loginWindow.add(gitLabel, 0, 7);
        loginWindow.add(datesLabel, 0, 8);

        return loginWindow;
    }

    // GUI - Chat Page
    private GridPane createChatPage(Stage chatStage) {
        GridPane chatWindow = new GridPane();
        chatWindow.setVgap(20);
        chatWindow.setHgap(20);
        chatWindow.setPadding(new Insets(20));

        // Old message area
        messageArea.setMaxHeight(Double.MAX_VALUE);
        messageArea.setMaxWidth(400);
        messageArea.setEditable(false);

        // Message input box
        inputBox.setMaxWidth(400);
        inputBox.setPromptText("Message ...");

        // Send message button
        Button sendMsgBut = new Button("SEND MESSAGE");
        sendMsgBut.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        sendMsgBut.setOnAction(event -> {
            // Message from user input
            String inputBoxMsg = identifier + " : " + inputBox.getText();

            buttonSendMsg(inputBoxMsg);
        });

        // Attack button
        Button attackBut = new Button("ATTACK");
        attackBut.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        attackBut.setOnAction(event -> {
            String attackMsg = identifier + " --- " + "ATTACK THE ENEMIES";
            buttonSendMsg(attackMsg);
        });

        // Emergency button
        Button emergencyBut = new Button("EMERGENCY");
        emergencyBut.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        emergencyBut.setStyle("-fx-background-color: #FF0000; ");
        emergencyBut.setOnAction(event -> {
            String emerMsg = identifier + " --- " + "EMERGENCY" + " --- Left the Communication !!!";
            buttonSendMsg(emerMsg);

            // Close the chat screen
            chatStage.close();
            System.exit(0);
        });

        // Mission Accomplished button
        Button missionAccomBut = new Button("MISSION ACCOMPLISHED");
        missionAccomBut.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        missionAccomBut.setStyle("-fx-background-color: #00FF00; ");
        missionAccomBut.setOnAction(event -> {
            String misAccMsg = identifier + " --- " + "MISSION ACCOMPLISHED";
            buttonSendMsg(misAccMsg);
        });

        // Assemble point
        Button assemblePointBut = new Button("ASSEMBLE");
        assemblePointBut.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        assemblePointBut.setOnAction(event -> {
            String assemMsg = identifier + " --- " + "BACK TO ASSEMBLY POINT";
            buttonSendMsg(assemMsg);
        });

        // Help button
        Button helpBut = new Button("HELP");
        helpBut.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        helpBut.setStyle("-fx-background-color: #FFFF00; ");
        helpBut.setOnAction(event -> {
            String helpMsg = identifier + " --- " + "HELP US !!!";
            buttonSendMsg(helpMsg);
        });

        // Plan B button
        Button planB = new Button("Plan B");
        planB.setMaxWidth(Double.MAX_VALUE); // Make button fill width
        planB.setOnAction(event -> {
            String planBMsg = identifier + " --- " + "CHANGE IN PLAN, EXECUTE PLAN-B";
            buttonSendMsg(planBMsg);
        });

        // Add buttons
        VBox buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(sendMsgBut, attackBut, emergencyBut, missionAccomBut, assemblePointBut, helpBut, planB);

        // Insert elements
        chatWindow.add(messageArea, 0, 0);
        chatWindow.add(inputBox, 0, 1);
        chatWindow.add(buttonsBox, 0, 2);

        return chatWindow;
    }
}
