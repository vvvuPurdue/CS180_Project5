package backend;

import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
    * Client testing
    *
    * A dummie client program to ensure that
    * server works as intended
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class ClientTest {
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 4242);
        System.out.println("Connected to server!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());

        Scanner scan = new Scanner(System.in);
        String message;
        String response;
        do {
            System.out.print("Send message to server: ");
            message = scan.nextLine();
            writer.write(message);
            writer.println();
            writer.flush();
        } while (!message.equals("close server"));

        socket.close();
        writer.close();
        reader.close();
        scan.close();
    }
}
