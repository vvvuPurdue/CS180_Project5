package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.Account;

/**
    * Client class
    *
    * Manages logic of client
    *
    * @author Team 15-3 CS 180 - Merge
    * @version November 26, 2020
*/

public class NewClient {
    
    // storing the current client signed in on client side
    private static Account user;

    // gui constants
    private static final Font titleFont = new Font("Arial", 1, 18);

    // Main menu elements
    private static JButton profileMenuButton;
    private static JButton friendMenuButton;
    private static JButton searchMenuButton;
    private static JButton deleteAccountButton;

    // edit account fields
    private static JButton editProfileButton;
    private static JButton saveChangesButton;
    private static JLabel phoneNumberLabel;
    private static JTextField phoneNumberTxtField;
    private static JLabel emailLabel;
    private static JTextField emailTxtField;
    private static JLabel bioLabel;
    private static JTextField bioTxtField;
    private static JLabel interestsLabel;
    private static JTextField interestsTxtField;
    private static JLabel usernameLabel;
    private static JTextField usernameTxtField;
    private static JLabel passwordLabel;
    private static JTextField passwordTxtField;

    public static ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        }
    };

    public static void main(String[] args) {
        System.out.println("Starting program...");
        // loop until user cancels at start menu or enters valid user info
        while (true) {
            int loginOrCreate = startingMenu();
            if ((loginOrCreate == 0 && login()) || (loginOrCreate == 1 && createAccount())) {
                break;
            } else if (loginOrCreate == -1) {
                return;
            }
        }
        mainMenu();
    }

    /*
        First menu that shows when program is opened. Give user the choice to login or create account
        Returns 0 to login, 1 to createAccount, -1 to close window
    */
    public static int startingMenu() {
        String[] options = { "Login", "Create New Account" };
        int result = JOptionPane.showOptionDialog(null, "Welcome to the Friends App!", "Friends App", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return result;
    }

    /*
        * Menu to allow user to login
        * Return true if successful, false if error occurred or user wants to cancel
        * If there are errors, show appropriate message dialogs
    */
    public static boolean login() {
        System.out.println("Logging in...");
        String username = JOptionPane.showInputDialog(null, "Username", JOptionPane.QUESTION_MESSAGE);
        // if user clicked cancel, return to start menu
        if (username == null) return false;
        String password = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);
        // if user clicked cancel, return to start menu
        if (password == null) return false;
        Object[] response = ClientRequest.sendToServer(new String[] { "loginUser", username, password });
        String status = (String) response[0];
        switch (status) {
            case "success":
                user = (Account) response[1];
                return true;
            case "usernameNotFound":
                JOptionPane.showMessageDialog(null, "Username does not exist", "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "incorrectPassword":
                JOptionPane.showMessageDialog(null, "Incorrect Password", "Password Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "connectionFailed":
                JOptionPane.showMessageDialog(null, "Could not connect to server!", "Connection Error!", JOptionPane.ERROR_MESSAGE);
                break;
        }
        return false;
    }


    /*
        * Menu to allow user to create a new account
        * Return true if successful, false if error occurred or user wants to cancel
        * If there are errors, show appropriate message dialogs
    */
    public static boolean createAccount() {
        System.out.println("Creating new account...");
        String username = JOptionPane.showInputDialog(null, "Enter a username", JOptionPane.QUESTION_MESSAGE);
        if (username == null) return false;
        String password = JOptionPane.showInputDialog(null, "Enter a password", JOptionPane.QUESTION_MESSAGE);
        if (password == null) return false;
        String email = JOptionPane.showInputDialog(null, "Please enter your email", JOptionPane.QUESTION_MESSAGE);
        if (email == null) return false;
        String phone = JOptionPane.showInputDialog(null, "Please enter your phone number", JOptionPane.QUESTION_MESSAGE);
        if (phone == null) return false;
        String bio = JOptionPane.showInputDialog(null, "Please enter your bio", JOptionPane.QUESTION_MESSAGE);
        if (bio == null) return false;
        String interests = JOptionPane.showInputDialog(null, "Please enter some of your interests", JOptionPane.QUESTION_MESSAGE);
        if (interests == null) return false;

        String[] accountInfo = { "createAccount", username, password, email, phone, bio, interests };
        Object[] response = ClientRequest.sendToServer(accountInfo);
        String status = (String) response[0];
        switch (status) {
            case "success":
                JOptionPane.showMessageDialog(null, "Successfully created account", "Account Created!", JOptionPane.INFORMATION_MESSAGE);
                user = (Account) response[1];
                return true;
            case "usernameExists":
                JOptionPane.showMessageDialog(null, "Username already exists", "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
            case "emptyFields":
                JOptionPane.showMessageDialog(null, "You must fill every field to create an account", "User Error!", JOptionPane.ERROR_MESSAGE);
                break;
        }
        return false;
    }

    /*
        * The main menu that the user will see once successfully logged in
        * Can view friends list, view profile, search new people, and delete account
    */
    public static void mainMenu() {
        System.out.println("Entering main menu...");
        // initial setup
        JFrame mainMenu = new JFrame();
        Container content = mainMenu.getContentPane();
        content.setLayout(new BorderLayout());
        // creating title
        JLabel header = new JLabel("Main Menu", SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header, BorderLayout.NORTH);
        // create new buttons
        profileMenuButton = new JButton("View Profile");
        friendMenuButton = new JButton("View Friends");
        searchMenuButton = new JButton ("Search for Users");
        deleteAccountButton = new JButton("Delete Account");
        // adding new buttons to new panel, then add to menu
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.add(profileMenuButton);
        mainMenuPanel.add(friendMenuButton);
        mainMenuPanel.add(searchMenuButton);
        mainMenuPanel.add(deleteAccountButton);
        content.add(mainMenuPanel, BorderLayout.CENTER);
        // after adding everything, finally show menu
        mainMenu.setSize(600, 400);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.pack();
        mainMenu.setVisible(true);
    }
}