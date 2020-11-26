import javax.swing.*;
/**
 *  Client class
 *  
 *  Connects to the server as a client instance
 *  Performs a login or account creation
 *  Loads a GUI to view friends and a profile
 *  
 *  @author Team 15-3 CS 180 - Merge
 *  @version November 24, 2020
 */

public class Client {
	private static String accountName;
	private static String pass;

	public static void main(String[] args) {
		boolean hasAccount = false;
		//creates a login or account creation request to send to the server
		String[] makeNew = {"Login", "Create new Account"};
		String response = (String) JOptionPane.showInputDialog(null, "Login Prompt", "", JOptionPane.QUESTION_MESSAGE, null, makeNew,
                makeNew[0]);
		accountName = JOptionPane.showInputDialog(null, "Account Name", JOptionPane.QUESTION_MESSAGE);
		pass = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);
		if (response.equals("Login")) {
			//send account name and pass to login
			
		} else {
			//send account name and pass and do account setup
		}
		

	}
	
	
	//method to open a window with the friend menu
	public void friendMenu() {
		
	}
	
	
	//method to open a window with the profile menu
	public void profileMenu() {
		
	}
	

}
