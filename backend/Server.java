import java.io.*;
import java.net.*;

/**
    * Server
    *
    * brief description of the program
    *
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
    *
*/

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Waiting for clients to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");
    }
}
