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

public class Client { //TODO create friend and profile menus, establish all server requests
	//user account variables
	private static String accountName;
	private static String pass;
	private static Account user;
	//static request arrays for requests with no parameters
	public static final String[] createSession = {"createSession"};
	public static final String[] closeSession = {"closeSession"};
	
	//io declaration
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter writer;
	public static ObjectInputStream objectInput;
	public static ObjectOutputStream objectOut;
	
	public static String serverHost;
	public static int serverPort;

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		//find the server info so that the server host can be remote, or on a different port
		serverHost = JOptionPane.showInputDialog(null, "Server Hostname", JOptionPane.QUESTION_MESSAGE);
		serverPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port", JOptionPane.QUESTION_MESSAGE));
		//connects to the server and shows confirmation
		socket = new Socket(serverHost, serverPort);
		JOptionPane.showInternalMessageDialog(null, "Successfully connected to server", "Connection Established", JOptionPane.INFORMATION_MESSAGE);
		
		//establishes IO method with server
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        objectInput = new ObjectInputStream(socket.getInputStream());
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        
        //creates server session
        objectOut.writeObject(createSession);
		
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
				hasAccount = loginUser();
				
			
			} else {
				//send account name and pass and do account setup
				hasAccount = createUser();
				
			}
			
			// if connection is established, and account login is valid / valid account creation, then hasAccount becomes true.
		
		} while(!hasAccount);
		
		//at this point client is connected to server and logged in
		
		//base menu for client, when running the client is still running
		//once the Close option is chosen, the client closes all resources and terminates
		String[] menus = {"Friends", "Profile", "Close Client", "Delete Account"};
		
		
		do {
			String menuChoice = (String) JOptionPane.showInputDialog(null, "Home", "", JOptionPane.QUESTION_MESSAGE, null, menus,
				menus[0]);
			switch(menuChoice) {
			case "Friends":
				friendMenu();
				break;
			case "Profile": 
				profileMenu();
				break;
			case "Delete Account":
				if(deleteAccount()) {
					return;
				}
			case "Close Client":
				//close all client resources here
				
				closeClient();
				return;
			} 
		
		} while(true);

	}
	
	
	//method to open a window with the friend menu
	public static void friendMenu() {
		JFrame friendFrame = new JFrame();
		//hasRequested method implemented
		//isFriendsWith method implemented
		//sendFriendRequest method implemented
		//cancelFriendRequest method implemented
		//acceptFriendRequest method implemented
		//declineFriendRequest method implemented
		//removeFriend method implemented
		//getuser method implemented
	}
	
	
	//method to open a window with the profile menu
	public static void profileMenu() {
		JFrame profileFrame = new JFrame();
		//updateAccount method implemented
	}
	
	public static boolean loginUser() throws IOException, ClassNotFoundException {
		boolean hasAccount = false;
		String[] accountInfo = {"loginUser", accountName, pass};
		objectOut.writeObject(accountInfo);
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
		return hasAccount;
	}
	
	public static boolean createUser() throws ClassNotFoundException, IOException {
		boolean hasAccount = false;
		//section for contact info
		String email = JOptionPane.showInputDialog(null, "Please enter your email", JOptionPane.QUESTION_MESSAGE);
		String phone = JOptionPane.showInputDialog(null, "Please enter your phone number", JOptionPane.QUESTION_MESSAGE);
		
		//section to gain all other needed account info to create the Account
		String bio = JOptionPane.showInputDialog(null, "Please enter your bio", JOptionPane.QUESTION_MESSAGE);
		String interests = JOptionPane.showInputDialog(null, "Please enter some of your interests", JOptionPane.QUESTION_MESSAGE);
		
		String[] accountParams = {"createAccount", accountName, pass, email, phone, bio, interests};
		objectOut.writeObject(accountParams);
		String result = reader.readLine();
		switch (result) {
		case "success":
			JOptionPane.showInternalMessageDialog(null, "Successfully created account", "Account Created!", JOptionPane.INFORMATION_MESSAGE);
			user = (Account) objectInput.readObject();
			hasAccount = true;
			break;
		case "usernameExists":
			JOptionPane.showInternalMessageDialog(null, "Username already exists", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "You must fill every field to create an account", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
		return hasAccount;
	}
	
	public static void closeClient() throws IOException {
		objectOut.writeObject(closeSession);
		writer.close();
		reader.close();
		objectInput.close();
		objectOut.close();
		socket.close();
	}
	
	public static boolean deleteAccount() throws IOException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Confirmation Required", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			String[] deleteAccount = {"deleteAccount", accountName, pass};
			objectOut.writeObject(deleteAccount);
			closeClient();
			return true;
		}
		return false;
	}
	
	public static void updateAccount(String email, String phoneNo, String bio, String interests) throws IOException, ClassNotFoundException {
		String[] updateString = {"updateAccount", accountName, email, phoneNo, bio, interests};
		objectOut.writeObject(updateString);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username could not be found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "incorrectPassword":
			JOptionPane.showInternalMessageDialog(null, "Password was not correct!", "Password Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "usernameExists":
			JOptionPane.showInternalMessageDialog(null, "Username already exists!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "invalidUsername":
			JOptionPane.showInternalMessageDialog(null, "Username is invalid!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Account information cannot be empty!", "Account Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
	
	public static void updateAccount(String email, String phoneNo, String bio, String interests, String pass, String newUsername, String newPassword) throws IOException, ClassNotFoundException {
		String[] updateString = {"updateAccount", accountName, email, phoneNo, bio, interests, newUsername, newPassword};
		objectOut.writeObject(updateString);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username could not be found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "incorrectPassword":
			JOptionPane.showInternalMessageDialog(null, "Password was not correct!", "Password Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "usernameExists":
			JOptionPane.showInternalMessageDialog(null, "Username already exists!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "invalidUsername":
			JOptionPane.showInternalMessageDialog(null, "Username is invalid!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Account information cannot be empty!", "Account Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
	
	public static boolean isFriendsWith(String username, String username2) throws IOException {
		String[] isFriends = {"isFriendsWith", username, username2};
		objectOut.writeObject(isFriends);
		String code = reader.readLine();
		switch (code) {
		case "success":
			boolean isFriend = objectInput.readBoolean();
			return isFriend;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username2 + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Fields are empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
		return false;
	}
	
	public static void sendFriendRequest(String username) throws IOException, ClassNotFoundException {
		String[] friendRequest = {"sendFriendRequest", accountName, username};
		objectOut.writeObject(friendRequest);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Friend request cannot be empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void cancelFriendRequest(String username) throws IOException, ClassNotFoundException {
		String[] friendRequest = {"cancelFriendRequest", accountName, username};
		objectOut.writeObject(friendRequest);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Friend request to cancel cannot be empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void acceptFriendRequest(String username) throws IOException, ClassNotFoundException {
		String[] friendRequest = {"acceptFriendRequest", accountName, username};
		objectOut.writeObject(friendRequest);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Friend to accept must not be empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void declineFriendRequest(String username) throws IOException, ClassNotFoundException {
		String[] friendRequest = {"declineFriendRequest", accountName, username};
		objectOut.writeObject(friendRequest);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Friend to deny must not be empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void removeFriend(String username) throws IOException, ClassNotFoundException {
		String[] friendRequest = {"removeFriend", accountName, username};
		objectOut.writeObject(friendRequest);
		String code = reader.readLine();
		switch (code) {
		case "success":
			user = (Account) objectInput.readObject();
			break;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + accountName + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Friend to remove must not be empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static Account getUser(String username) throws IOException, ClassNotFoundException {
		String[] request = {"getUser", username};
		objectOut.writeObject(request);
		String code = reader.readLine();
		switch (code) {
		case "success":
			return (Account) objectInput.readObject();
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return null;
	}
	
	public static boolean hasRequested(String username, String username2) throws IOException {
		String[] request = {"hasRequested", username, username2};
		objectOut.writeObject(request);
		String code = reader.readLine();
		switch (code) {
		case "success":
			boolean hasRequest = objectInput.readBoolean();
			return hasRequest;
		case "usernameNotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "username2NotFound":
			JOptionPane.showInternalMessageDialog(null, "Username " + username2 + " is not found!", "User Error!", JOptionPane.ERROR_MESSAGE);
			break;
		case "emptyFields":
			JOptionPane.showInternalMessageDialog(null, "Usernames must not be empty!", "Input Error!", JOptionPane.ERROR_MESSAGE);
			break;
		}
		return false;
	}
	
	public static void connectServer() throws UnknownHostException, IOException {
		
		socket = new Socket(serverHost, serverPort);
		
		//establishes IO method with server
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        objectInput = new ObjectInputStream(socket.getInputStream());
        objectOut = new ObjectOutputStream(socket.getOutputStream());
	}
	
	public static void disconnectServer() throws IOException {
		socket.close();
		reader.close();
		writer.close();
		objectInput.close();
		objectOut.close();
	}
	

}
