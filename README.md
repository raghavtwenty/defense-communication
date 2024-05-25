# DEFENSE COMMUNICATION
_Java based real-time messaging application primarily designed for communication in military operations_
<br><br><br>


### PROTOTYPE VIDEO
https://github.com/raghavtwenty/defense-communication/assets/126254197/f7687941-281e-40fa-b157-36eca937ebbf

<br><br>

### HOW TO EXECUTE

#### Terminal
```
git clone https://github.com/raghavtwenty/defense-communicatio.git
```
<br>

1. Open this repo in VS Code
2. In the left bottom, Java > "Referenced Libraries" click "+"
3. Choose "javafx-sdk-20.0.1" inside dependencies folder of this repo
4. Change path in _settings.json_ <br>
"/Users/raghava/Desktop/defense-communication/dependencies/javafx-sdk-20.0.1/**/*.jar" replace with your JavaFX path
```
{
    "java.project.sourcePaths": [
        "src"
    ],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar",
        "/Users/raghava/Desktop/defense-communication/dependencies/javafx-sdk-20.0.1/**/*.jar"
    ]
}
```

5. Change path in _launch.json_
```
"vmArgs": "--module-path /Users/raghava/Desktop/defense-communication/dependencies/javafx-sdk-20.0.1/lib --add-modules javafx.controls,javafx.fxml"
```
"/Users/raghava/Desktop/defense-communication/dependencies/javafx-sdk-20.0.1/lib" replace with your JavaFX path

<br><br>

### PROBLEM STATEMENT

Develop a Java application that implements networking concepts to facilitate communication between multiple clients and a central server. The application should allow clients to connect to the server over a network and exchange messages in real-time. The server should be capable of managing multiple client connections concurrently and relaying messages between clients as needed.
<br><br><br>


### OBJECTIVE

Develop a GUI-based Java application tailored for military scenarios, providing seamless real-time messaging capabilities. The application aims to empower military personnel with an intuitive interface for sending and receiving messages swiftly, enhancing communication efficiency in the field. Additionally, the application will feature custom quick reply messages, enabling users to respond promptly to incoming messages with predefined responses, further streamlining communication in dynamic operational environments.
<br><br><br>


### INTRODUCTION

The Defense Communication project is a Java-based real-time messaging application specifically designed to facilitate communication within military operations. This application employs networking concepts and GUI-based interfaces to streamline messaging among military personnel in the field. With a focus on concurrency and UDP connections, the application ensures efficient and reliable communication, enhancing situational awareness and operational effectiveness.
<br><br><br>


### CONCEPTS USED

- Client-Server architecture <br>
- Threading, Concurrency  <br>
- UDP Connection <br>
- Analysis & Validation of Datagram Packets <br>
- JavaFX GUI
<br><br><br>


### WORKING

1. The application follows a client-server architecture, with clients connecting to a central server over a network.
2. Clients send messages to the server, which relays them to other connected clients in real-time.
3. The server analyzes incoming messages, verifies their integrity through checksum values, Cyclic Redundancy Check (CRC), and ensures they are not corrupted.
4. Threading is used to handle multiple client connections concurrently, allowing for simultaneous message exchanges without blocking.
5. Predefined quick reply messages streamline communication, enabling users to respond promptly to common scenarios in military operations.
<br><br><br>


### END USERS

1. Students
2. Armed Forces 
<br><br><br>


### OUTPUTS

- Login Screen <br><br>
![1](https://github.com/raghavtwenty/defense-communication/assets/126254197/a653c645-85d9-45b7-a36e-5901b8e55fc4)


- Login Screen <br><br>
![2](https://github.com/raghavtwenty/defense-communication/assets/126254197/f6c57053-bd4a-4b7e-8d85-cfe0d0e5eb61)


- Home Screen <br><br>
![3](https://github.com/raghavtwenty/defense-communication/assets/126254197/65856bb9-d074-4605-a9a4-dbfe34476556)


- Messaging Screen (Team: Alpha) <br><br>
![4](https://github.com/raghavtwenty/defense-communication/assets/126254197/28840ea6-f10c-4f57-9fd1-4364de8f71ed)


- Messaging Screen (Team: Beta) <br><br>
![5](https://github.com/raghavtwenty/defense-communication/assets/126254197/13dcb728-b060-4cd3-a5cd-185d3055cd15)


- Messaging Screen (Team: Gamma) <br><br>
![6](https://github.com/raghavtwenty/defense-communication/assets/126254197/d7a363bb-af53-4028-b85d-492d8aa12557)


- Server Side Logs & Validation <br><br>
![7](https://github.com/raghavtwenty/defense-communication/assets/126254197/b64c0a0b-b3a8-4c61-94e6-5a4335241765)


- Overall Login Screen <br><br>
![8](https://github.com/raghavtwenty/defense-communication/assets/126254197/01e13ec2-a71e-4e6b-b45e-d472511356bc)


- Overall Messaging Screen <br><br>
![9](https://github.com/raghavtwenty/defense-communication/assets/126254197/37e8d6dd-df18-426a-9482-1fd62d6bd4b0)

<br><br>

### FUTURE SCOPE

The integration with drones allows the server file to be deployed directly onto the drone, enabling seamless communication between troops. The application is designed to accommodate future enhancements and customizations to further improve its functionality and utility in military operations.
<br><br>

_END OF README_
