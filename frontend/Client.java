package frontend;

import javax.swing.*;
import java.io.*;
import java.net.*;

import backend.Account;

/**
 *  Client class
 *  
 *  Connects to the server as a client instance
 *  Performs a login or account creation
 *  Loads a GUI to view friends and a profile
 *  
 *  @author Team 15-3 CS 180 - Merge
 *  @version November 26, 2020
 */

public class Client { //TODO add connection to the server code, create friend and profile menus
	private static String accountName;
	private static String pass;
	private static Account user;

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		//find the server info so that the server host can be remote, or on a different port
		String hostname = JOptionPane.showInputDialog(null, "Server Hostname", JOptionPane.QUESTION_MESSAGE);
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port", JOptionPane.QUESTION_MESSAGE));
		//connects to the server and shows confirmation
		Socket socket = new Socket(hostname, port);
		JOptionPane.showInternalMessageDialog(null, "Successfully connected to server", "Connection Established", JOptionPane.INFORMATION_MESSAGE);
		
		//establishes IO method with server
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
        
        //creates server session
        writer.print("createSession");
		
		boolean hasAccount = false;
		//creates a login or account creation request to send to the server
		String[] makeNew = {"Login", "Create new Account"};
		do {
			String response = (String) JOptionPane.showInputDialog(null, "Login Prompt", "", JOptionPane.QUESTION_MESSAGE, null, makeNew,
					makeNew[0]);
			accountName = JOptionPane.showInputDialog(null, "Account Name", JOptionPane.QUESTION_MESSAGE);
			pass = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);
			
			
			
			if (response.equals("Login")) {
				//send account name and pass to login
				writer.print("loginUser " + accountName + " " + pass);
				String success = reader.readLine();
				switch (success) {
				case "Success":
					hasAccount = true;
					user = (Account) objectInput.readObject();
					break;
				case "usernameNotFound":
					JOptionPane.showInternalMessageDialog(null, "Username does not exist", "User Error!", JOptionPane.ERROR_MESSAGE);
					break;
				case "incorrectPassword":
					JOptionPane.showInternalMessageDialog(null, "Incorrect Password", "Password Error!", JOptionPane.ERROR_MESSAGE);
				}
				
			
			} else {
				//send account name and pass and do account setup
				//section for contact info
				String email = JOptionPane.showInputDialog(null, "Please enter your email", JOptionPane.QUESTION_MESSAGE);
				String phone = JOptionPane.showInputDialog(null, "Please enter your phone number", JOptionPane.QUESTION_MESSAGE);
				
				//section to gain all other needed account info to create the Account
				String bio = JOptionPane.showInputDialog(null, "Please enter your bio", JOptionPane.QUESTION_MESSAGE);
				String interests = JOptionPane.showInputDialog(null, "Please enter some of your interests", JOptionPane.QUESTION_MESSAGE);
				
				writer.print("createAccount " + accountName + " " + pass + " " + email + " " + phone + " " + bio + " " + interests);
				String result = reader.readLine();
				switch (result) {
				case "success":
					JOptionPane.showInternalMessageDialog(null, "Successfully created account", "Account Created!", JOptionPane.INFORMATION_MESSAGE);
					user = (Account) objectInput.readObject();
					break;
				case "usernameExists":
					JOptionPane.showInternalMessageDialog(null, "Username already exists", "User Error!", JOptionPane.ERROR_MESSAGE);
					break;
				case "emptyFields":
					JOptionPane.showInternalMessageDialog(null, "You must fill every field to create an account", "User Error!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				
			}
			
			// if connection is established, and account login is valid / valid account creation, then hasAccount becomes true.
		
		} while(!hasAccount);
		
		//at this point client is connected to server and logged in
		
		//base menu for client, when running the client is still running
		//once the Close option is chosen, the client closes all resources and terminates
		String[] menus = {"Friends", "Profile", "Close Client"};
		
		String menuChoice = (String) JOptionPane.showInputDialog(null, "Home", "", JOptionPane.QUESTION_MESSAGE, null, menus,
				menus[0]);
		do {
			switch(menuChoice) {
			case "Friends":
				friendMenu();
				break;
			case "Profile": 
				profileMenu();
				break;
			case "Close Client":
				//close all client resources here
				writer.print("closeSession");
				writer.close();
				reader.close();
				objectInput.close();
				socket.close();
				return;
			} 
		
		} while(true);

	}
	
	
	//method to open a window with the friend menu
	public static void friendMenu() {
		JFrame friendFrame = new JFrame();
	}
	
	
	//method to open a window with the profile menu
	public static void profileMenu() {
		JFrame profileFrame = new JFrame();
	}
	

}
