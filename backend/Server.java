package backend;

import java.io.*;
import java.net.*;

/**
    * Server
    *
    * Handles the server creation, requests, and authentication
    * The controller of the whole backend system
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Waiting for clients to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());

        Account bob = new Account("bob123", "password", "bob@email", "123", "a cool guy", "pizza, soccer, and sleeping");

        String message = "";
        do {
            message = reader.readLine();
            System.out.println("Received from client: " + message);
            
            if (message.equals("getUser")) {
                writer.write("good");
                writer.println();
                writer.flush();
                objectOutput.writeObject(bob);
                // objectOutput.flush();
            } else {
                writer.write("bad");
                writer.println();
                writer.flush();
            }
        } while (!message.equals("close server"));

        serverSocket.close();
        reader.close();
        writer.close();
    }
}
