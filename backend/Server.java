package backend;

import java.io.*;
import java.net.*;

/**
    * Server
    *
    * Handles the server creation, session creation,
    * and requests, and authentication (through session threads)
    * The "controller" of the whole backend system
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Server {
    
    static ObjectInputStream reader;
    static PrintWriter writer;
    static ObjectOutputStream objectWriter;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // server creation
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Server running on port 4242. Press Ctrl+C to terminate");

        // Starting up our database/manageer/model
        Manager manager = new Manager();

        // when server is terminated/interrupted all of a sudden, quickly save files
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Server has ended");
                manager.saveToFile();
            }
        });

        while (true) {
            // client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            new ServerThread(socket, manager).start();
        }
    }
}
