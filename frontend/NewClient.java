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
    private static Account currentUser;
    // this is a temporary variable that stores whichever user we want to show
    // for use in the actionListener
    // example: Bob want to see Sam's info. Store "Sam" into this variable
    private static String usernameToShow;

    // gui constants
    private static final Font titleFont = new Font("Arial", 1, 18);

    // edit account fields
    private static JTextField phoneNumberTxtField;
    private static JTextField emailTxtField;
    private static JTextField bioTxtField;
    private static JTextField interestsTxtField;
    private static JTextField newUsernameTxtField;
    private static JTextField oldPasswordTxtField;
    private static JTextField newPasswordTxtField;

    /*
        * There are several actions that we can listen to:
        * View Profile : show profile menu and display the user info
        * 
    */
    public static ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                String action = (String) ((JButton) e.getSource()).getText();
                switch (action) {
                    case "View Profile":
                        // if view profile button was pressed, show profile window
                        showProfile(currentUser);
                        break;
                    case "Edit Account":
                        // if edit profile button was pressed, show profile window for this current user
                        showEditProfile();
                        break;
                    case "Save Changes":
                        // if saved changes button was pressed, send new info request to server
                        Object[] response;
                        if (oldPasswordTxtField.getText().equals("")
                            && newPasswordTxtField.getText().equals("")
                            && newUsernameTxtField.getText().equals("")
                        ) {
                            String[] request = new String[] {
                                "updateAccount", currentUser.getUsername(),
                                emailTxtField.getText(), phoneNumberTxtField.getText(),
                                bioTxtField.getText(), phoneNumberTxtField.getText()
                            };
                            response = ClientRequest.sendToServer(request);
                        } else {
                            String[] request = new String[] {
                                "updateAccount", currentUser.getUsername(),
                                emailTxtField.getText(), phoneNumberTxtField.getText(),
                                bioTxtField.getText(), phoneNumberTxtField.getText(),
                                oldPasswordTxtField.getText(), newUsernameTxtField.getText(), newPasswordTxtField.getText()
                            };
                            response = ClientRequest.sendToServer(request);
                        }
                        // check to see if successful
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Changes have been successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "usernameNotFound":
                                JOptionPane.showMessageDialog(null, "Username could not be found!", "User Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "incorrectPassword":
                                JOptionPane.showMessageDialog(null, "Password was not correct!", "Password Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "usernameExists":
                                JOptionPane.showMessageDialog(null, "Username already exists!", "User Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "invalidUsername":
                                JOptionPane.showMessageDialog(null, "Username is invalid!", "User Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "emptyFields":
                                JOptionPane.showMessageDialog(null, "Account information cannot be empty!", "Account Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                        }
                        break;
                }
            }
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
        showMainMenu();
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
                currentUser = (Account) response[1];
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
                currentUser = (Account) response[1];
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
    public static void showMainMenu() {
        System.out.println("Entering main menu...");
        // initial setup
        JFrame mainMenu = new JFrame();
        Container content = mainMenu.getContentPane();
        content.setLayout(new BorderLayout());
        // creating title
        JLabel header = new JLabel("Logged in as: " + currentUser.getUsername(), SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header, BorderLayout.NORTH);
        // create new buttons
        JButton profileMenuButton = new JButton("View Profile");
        profileMenuButton.addActionListener(actionListener);
        JButton friendMenuButton = new JButton("View Friends");
        JButton searchMenuButton = new JButton ("Search for Users");
        // adding new buttons to new panel, then add to menu
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.add(profileMenuButton);
        mainMenuPanel.add(friendMenuButton);
        mainMenuPanel.add(searchMenuButton);
        content.add(mainMenuPanel, BorderLayout.CENTER);
        // after adding everything, finally show menu
        mainMenu.setTitle("Main Menu");
        mainMenu.setSize(600, 400);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.pack();
        mainMenu.setVisible(true);
    }

    public static void showProfile(Account user) {
        System.out.println("Viewing " + user.getUsername() + " profile...");
        JFrame profileWindow = new JFrame();
        Container content = profileWindow.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel header = new JLabel("Profile of: " + user.getUsername(), SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header, BorderLayout.NORTH);
        content.add(Box.createVerticalStrut(10));

        JLabel username = new JLabel("Username: " + user.getUsername(), SwingConstants.CENTER);
        JLabel email = new JLabel("Email: " + user.getEmail(), SwingConstants.CENTER);
        JLabel phoneNumber = new JLabel("Phone Number: " + user.getPhoneNumber(), SwingConstants.CENTER);
        JLabel bio = new JLabel("Biography: " + user.getBio(), SwingConstants.CENTER);
        JLabel interests = new JLabel("Interests: " + user.getInterests(), SwingConstants.CENTER);
        content.add(username);
        content.add(Box.createVerticalStrut(10));
        content.add(email);
        content.add(Box.createVerticalStrut(10));
        content.add(phoneNumber);
        content.add(Box.createVerticalStrut(10));
        content.add(bio);
        content.add(Box.createVerticalStrut(10));
        content.add(interests);

        if (user.equals(currentUser)) {
            JButton editProfile = new JButton("Edit Account");
            editProfile.addActionListener(actionListener);
            JButton deleteAccountButton = new JButton("Delete Account");
            content.add(editProfile);
            content.add(deleteAccountButton);
        } else {
            JButton viewFriends = new JButton("View Friends");
            content.add(viewFriends);
        }

        profileWindow.setTitle("Profile Menu");
        profileWindow.setSize(600, 1000);
        profileWindow.setLocationRelativeTo(null);
        profileWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileWindow.pack();
        profileWindow.setVisible(true);
    }

    public static void showEditProfile() {
        JFrame editProfile = new JFrame();
        Container content = editProfile.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel header = new JLabel("Edit Account", SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header);
        content.add(Box.createVerticalStrut(10));

        JPanel emailPanel = new JPanel(new BorderLayout());
        JLabel emailLabel = new JLabel("Email:");
        emailTxtField = new JTextField(currentUser.getEmail(), 20);
        emailPanel.add(emailLabel, BorderLayout.WEST);
        emailPanel.add(emailTxtField, BorderLayout.EAST);
        content.add(emailPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel phoneNumberPanel = new JPanel(new BorderLayout());
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberTxtField = new JTextField(currentUser.getPhoneNumber(), 15);
        phoneNumberPanel.add(phoneNumberLabel, BorderLayout.WEST);
        phoneNumberPanel.add(phoneNumberTxtField, BorderLayout.EAST);
        content.add(phoneNumberPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel bioPanel = new JPanel(new BorderLayout());
        JLabel bioLabel = new JLabel("Biography:");
        bioTxtField = new JTextField(currentUser.getBio(), 30);
        bioPanel.add(bioLabel, BorderLayout.WEST);
        bioPanel.add(bioTxtField, BorderLayout.EAST);
        content.add(bioPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel interestsPanel = new JPanel(new BorderLayout());
        JLabel interestsLabel = new JLabel("Interests:");
        interestsTxtField = new JTextField(currentUser.getInterests(), 30);
        interestsPanel.add(interestsLabel, BorderLayout.WEST);
        interestsPanel.add(interestsTxtField, BorderLayout.EAST);
        content.add(interestsPanel);
        content.add(Box.createVerticalStrut(10));

        JLabel header2 = new JLabel("If you are changing username or password, you must enter old password!", SwingConstants.CENTER);
        content.add(header2);
        content.add(Box.createVerticalStrut(10));

        JPanel oldPasswordPanel = new JPanel(new BorderLayout());
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordTxtField = new JTextField("", 12);
        oldPasswordPanel.add(oldPasswordLabel, BorderLayout.WEST);
        oldPasswordPanel.add(oldPasswordTxtField, BorderLayout.EAST);
        content.add(oldPasswordPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        JLabel usernameLabel = new JLabel("New Username:");
        newUsernameTxtField = new JTextField("", 12);
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(newUsernameTxtField, BorderLayout.EAST);
        content.add(usernamePanel);
        content.add(Box.createVerticalStrut(10));

        JPanel newPasswordPanel = new JPanel(new BorderLayout());
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordTxtField = new JTextField("", 12);
        newPasswordPanel.add(newPasswordLabel, BorderLayout.WEST);
        newPasswordPanel.add(newPasswordTxtField, BorderLayout.EAST);
        content.add(newPasswordPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel saveButtonPanel = new JPanel(new BorderLayout());
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(actionListener);
        saveButtonPanel.add(saveChangesButton, BorderLayout.CENTER);
        content.add(saveButtonPanel);

        editProfile.setTitle("Edit Account");
        editProfile.setSize(600, 400);
        editProfile.setLocationRelativeTo(null);
        editProfile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editProfile.pack();
        editProfile.setVisible(true);
    }
}