package frontend;

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import backend.Account;

/**
    * Client class
    *
    * Manages logic of client, shows appropriate GUIs,
    * and makes requests to server
    *
    * @author Team 15-3 CS 180 - Merge
    * @version November 26, 2020
*/

public class Client {

    // io declaration
    private static Socket socket;
    private static ObjectOutputStream writer;
    private static ObjectInputStream reader;

    // server connection constants
    private static final String serverHost = "localhost";
    private static final int serverPort = 4242;
    private static final long refreshRate = 5000; // in milliseconds

    // storing the current client signed in on client side
    private static Account currentUser;

    // gui constants
    private static final Font titleFont = new Font("Arial", 1, 20);
    private static final Font subTitleFont = new Font("Arial", 1, 15);
    private static final EmptyBorder padding = new EmptyBorder(10, 10, 10, 10);

    // edit account fields
    private static JTextField phoneNumberTxtField;
    private static JTextField emailTxtField;
    private static JTextField bioTxtField;
    private static JTextField interestsTxtField;
    private static JTextField newUsernameTxtField;
    private static JTextField oldPasswordTxtField;
    private static JTextField newPasswordTxtField;

    // deleting account, used to determine whether or not
    // to keep updating the windows. If user is deleting, don't update windows
    private static boolean deletingAccount = false;

    // search user fields
    private static JTextField searchField;

    // defines ALL of the possible button actions
    public enum Action {
        ViewProfile, ViewFriends,
        EditAccount, UpdateAccount, DeleteAccount,
        SendFriendRequest, CancelFriendRequest,
        AcceptFriendRequest, DeclineFriendRequest,
        RemoveFriend, ViewSearchMenu, SearchUsers
    }

    /*
        * There are several actions that we can perform after we listen
        * The action depends on what the Action enum assigned to the JAButton
    */
    public static ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JAButton) {
                // We are using the button's text to determine the action required
                JAButton source = (JAButton) e.getSource();
                Action buttonAction = source.getActionType();
                switch (buttonAction) {
                    // if view profile button was pressed, show profile window
                    // based on what account the button is linked to
                    case ViewProfile -> {
                        showProfile(source.getAccountName());
                    }
                    // if view friends button was pressed, show friends list window
                    // based on what account the button is linked to
                    case ViewFriends -> {
                        showFriendsList(source.getAccountName());
                    }
                    // if edit profile button was pressed, show profile window. Can only be shown for current user
                    case EditAccount -> {
                        showEditProfile();
                    }
                    // if saved changes button was pressed, send new info request to server. Can only be used for current user
                    case UpdateAccount -> {
                        Object[] response;
                        // if old password, new password, and new username are blank, that means we're not updating them
                        // send request that only updates email, phoneNumber, bio, and interests
                        if (oldPasswordTxtField.getText().equals("")
                            && newPasswordTxtField.getText().equals("")
                            && newUsernameTxtField.getText().equals("")
                        ) {
                            String[] request = new String[] {
                                "updateAccount", currentUser.getUsername(),
                                emailTxtField.getText(), phoneNumberTxtField.getText(),
                                bioTxtField.getText(), interestsTxtField.getText()
                            };
                            response = sendToServer(request);
                        } else {
                            // else, if they are not blank, we are updating password and username
                            // send a request to include everything
                            String[] request = new String[] {
                                "updateAccount", currentUser.getUsername(),
                                emailTxtField.getText(), phoneNumberTxtField.getText(),
                                bioTxtField.getText(), interestsTxtField.getText(),
                                oldPasswordTxtField.getText(), newUsernameTxtField.getText(), newPasswordTxtField.getText()
                            };
                            response = sendToServer(request);
                        }
                        // check status code to see if successful, and show appropriate messages
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                // System.out.println(currentUser.getUsername());
                                JOptionPane.showMessageDialog(null, "Changes have been successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "usernameNotFound":
                                JOptionPane.showMessageDialog(null, "Username could not be found!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "incorrectPassword":
                                JOptionPane.showMessageDialog(null, "Password was not correct!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "usernameExists":
                                JOptionPane.showMessageDialog(null, "Username already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "invalidUsername":
                                JOptionPane.showMessageDialog(null, "Username is invalid!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "emptyFields":
                                JOptionPane.showMessageDialog(null, "Account information cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                        }
                    }
                    // If user pressed delete account button, confirm if they want to delete. If so, proceed with operation
                    // Can only be shown with current user
                    case DeleteAccount -> {
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account? This cannot be undone.", "Delete Account?", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            String status = (String) sendToServer(new String[] { "deleteAccount", currentUser.getUsername(), currentUser.getPassword() })[0];
                            switch (status) {
                                case "success":
                                    deletingAccount = true;
                                    JOptionPane.showMessageDialog(null, "Account successfully delete! Program will close now...", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    System.exit(1);
                                    break;
                                case "connectionFailed":
                                    showConnectionError();
                                    break;
                            }
                        }
                    }
                    // If user pressed the friend request button, do a server request,
                    // where we send a friend request from the current user to the account linked to the JAButton. 
                    case SendFriendRequest -> {
                        Object[] response = sendToServer(new String[] { "sendFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Unable to send friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the cancel friend request button, do a server request,
                    // where we cancel a friend request sent from current user to the account linked to the JAButton.
                    case CancelFriendRequest -> {
                        Object[] response = sendToServer(new String[] { "cancelFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request canceled!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not cancel friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the accept friend request button, do a server request, and check status
                    case AcceptFriendRequest -> {
                        Object[] response = sendToServer(new String[] { "acceptFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request accepted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not accept friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the decline friend request button, do a server request. Check status
                    case DeclineFriendRequest -> {
                        Object[] response = sendToServer(new String[] { "declineFriendRequest", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend request declined!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not decline friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // If user pressed the remove friend request button, do a server request. Check status
                    case RemoveFriend -> {
                        Object[] response = sendToServer(new String[] { "removeFriend", currentUser.getUsername(), source.getAccountName() });
                        String status = (String) response[0];
                        switch (status) {
                            case "success":
                                currentUser = (Account) response[1];
                                JOptionPane.showMessageDialog(null, "Friend removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            case "connectionFailed":
                                showConnectionError();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Could not remove friend request", "Error", JOptionPane.INFORMATION_MESSAGE);
                                break;
                        }
                    }
                    // if user wishes to search users, show the search menu (displaying search bar)
                    case ViewSearchMenu -> {
                        showSearchMenu();
                    }
                    // once the user has pressed the search button, show the results
                    case SearchUsers -> {
                        showSearchResults(searchField.getText());
                        searchField.setText("");
                    }
                }
            }
        }
    };

    public static void main(String[] args) {
        System.out.println("Starting client...");
        // loop until user cancels at start menu or enters valid user info
        while (true) {
            int loginOrCreate = startingMenu();
            if ((loginOrCreate == 0 && login()) || (loginOrCreate == 1 && createAccount())) {
                break;
            } else if (loginOrCreate == -1) {
                return;
            }
        }
        System.out.println("Logged in as: " + currentUser.getUsername());
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
        String username = JOptionPane.showInputDialog(null, "Username", JOptionPane.QUESTION_MESSAGE);
        // if user clicked cancel, return to start menu
        if (username == null) return false;
        String password = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);
        // if user clicked cancel, return to start menu
        if (password == null) return false;
        Object[] response = sendToServer(new String[] { "loginUser", username, password });
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
                showConnectionError();
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
        Object[] response = sendToServer(accountInfo);
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
            case "connectionFailed":
                showConnectionError();
                break;
        }
        return false;
    }

    /*
        * The main menu that the user will see once successfully logged in
        * Can view friends list, view profile, search new people, and delete account
    */
    public static void showMainMenu() {
        // initial setup
        JFrame mainMenu = new JFrame();
        Container content = mainMenu.getContentPane();
        content.setLayout(new BorderLayout());
        // creating title
        JLabel header = new JLabel("Logged in as: " + currentUser.getUsername(), SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header, BorderLayout.NORTH);
        // create new buttons
        JAButton profileMenuButton = new JAButton("View Profile", currentUser.getUsername(), Action.ViewProfile);
        JAButton friendMenuButton = new JAButton("View Friends", currentUser.getUsername(), Action.ViewFriends);
        JAButton searchMenuButton = new JAButton ("Search for Users", Action.ViewSearchMenu);
        profileMenuButton.addActionListener(actionListener);
        friendMenuButton.addActionListener(actionListener);
        searchMenuButton.addActionListener(actionListener);
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
        mainMenu.setVisible(true);

        // continuosly keep this window updated (in case account info was updated)
        new Thread(() -> {
            while (mainMenu.isVisible() && !deletingAccount) {
                // check to see if our current user was updated/edited, and update header and buttons
                Object[] response = sendToServer(new String[] { "getUser", currentUser.getUsername() });
                String status = (String) response[0];
                if (status.equals("success")) {
                    currentUser = (Account) response[1];
                    header.setText("Logged in as: " + currentUser.getUsername());
                    profileMenuButton.setAccountName(currentUser.getUsername());
                    friendMenuButton.setAccountName(currentUser.getUsername());
                }
                mainMenuPanel.revalidate();
                mainMenuPanel.repaint();
                mainMenu.pack();
                try {
                    Thread.sleep(refreshRate);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        }).start();
    }

    /*
        * Shows a user's profile in a new window
        * If this user is the currentUser, allow editProfile abilities
        * Else, if it is a different user, allow the ability to view their friends request them
        * Has real time updates (based on refresh rate)
    */
    public static void showProfile(String username) {
        // initial setup, layout, and padding
        JFrame profileWindow = new JFrame();
        Container content = profileWindow.getContentPane();
        JPanel panel = new JPanel();
        panel.setBorder(padding);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // setting up header
        JLabel header = new JLabel("Profile of: ", SwingConstants.CENTER);
        header.setFont(titleFont);
        panel.add(header, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(10));

        // instantiation of all fields to show
        JLabel usernameToShow = new JLabel("Username: ", SwingConstants.CENTER);
        JLabel email = new JLabel("Email: ", SwingConstants.CENTER);
        JLabel phoneNumber = new JLabel("Phone Number: ", SwingConstants.CENTER);
        JLabel bio = new JLabel("Biography: ", SwingConstants.CENTER);
        JLabel interests = new JLabel("Interests: ", SwingConstants.CENTER);
        panel.add(usernameToShow);
        panel.add(Box.createVerticalStrut(10));
        panel.add(email);
        panel.add(Box.createVerticalStrut(10));
        panel.add(phoneNumber);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bio);
        panel.add(Box.createVerticalStrut(10));
        panel.add(interests);
        panel.add(Box.createVerticalStrut(10));

        // instantiation of buttons (some may be shown, some not, depending on what user we are seeing)
        JAButton editProfile = new JAButton("Edit Account", Action.EditAccount);
        JAButton deleteAccountButton = new JAButton("Delete Account", Action.DeleteAccount);
        JAButton viewFriends = new JAButton("View Friends", username, Action.ViewFriends);
        JAButton removeFriend = new JAButton("Remove Friend", username, Action.RemoveFriend);
        JAButton acceptFriendRequest = new JAButton("Accept Friend Request", username, Action.AcceptFriendRequest);
        JAButton declineFriendRequest = new JAButton("Decline Friend Request", username, Action.DeclineFriendRequest);
        JAButton requestFriend = new JAButton("Send Friend Request", username, Action.SendFriendRequest);
        JAButton cancelRequest = new JAButton("Cancel Friend Request", username, Action.CancelFriendRequest);
        // we store all buttons in an array for ease later
        JAButton[] buttons = new JAButton[] {
            editProfile, deleteAccountButton, viewFriends,removeFriend,
            acceptFriendRequest, declineFriendRequest, requestFriend, cancelRequest
        };
        // hide them all, add action listner, and add to panel
        for (JAButton button : buttons) {
            button.setVisible(false);
            button.addActionListener(actionListener);
            panel.add(button);
        }

        // displaying the window
        content.add(panel);
        profileWindow.setTitle("Profile Menu");
        profileWindow.setSize(600, 400);
        profileWindow.setLocationRelativeTo(null);
        profileWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileWindow.pack();
        profileWindow.setVisible(true);

        // continuously refresh the content of window until it is closed
        new Thread(() -> {
            while (profileWindow.isVisible() && !deletingAccount) {
                // request the data from server to update any new info
                Object[] response = sendToServer(new String[] { "getUser", username });
                String status = (String) response[0];
                if (status.equals("success")) {
                    Account user = (Account) response[1];
                    // update the fields to show the user info
                    header.setText("Profile of: " + user.getUsername());
                    usernameToShow.setText("Username: " + user.getUsername());
                    email.setText("Email: " + user.getEmail());
                    phoneNumber.setText("Phone Number: " + user.getPhoneNumber());
                    bio.setText("Biography: " + user.getBio());
                    interests.setText("Interests: " + user.getInterests());
                    // hide all buttons. Show only the neccessary ones, depending on what user we're seeing
                    for (JAButton button : buttons) {
                        button.setVisible(false);
                    }
                    // if the user we are viewing is the current user, show edit/delete account features
                    if (username.equals(currentUser.getUsername())) {
                        editProfile.setVisible(true);
                        deleteAccountButton.setVisible(true);
                    } else {
                        // if it is another user, allow the ability to view their friends
                        viewFriends.setVisible(true);
                        // then, check if we are friends with them
                        Object[] isFriendsResponse = sendToServer( new String[] { "isFriendsWith", currentUser.getUsername(), username });
                        String isFriendsStatus = (String) isFriendsResponse[0];
                        if (isFriendsStatus.equals("success")) {
                            // if we are friends with them, add option to remove the friend
                            if ((boolean) isFriendsResponse[1]) {
                                removeFriend.setVisible(true);
                            } else {
                                // if we're not friends with them, check if we have requested user
                                Object[] hasRequestedResponse = sendToServer( new String[] { "hasRequested", currentUser.getUsername(), username });
                                String hasRequestedStatus = (String) hasRequestedResponse[0];
                                if (hasRequestedStatus.equals("success")) {
                                    // if we have friend requested them, give option to cancel the request
                                    if ((boolean) hasRequestedResponse[1]) {
                                        cancelRequest.setVisible(true);
                                    } else {
                                        // if we have not requested them, check if they requested us
                                        hasRequestedResponse = sendToServer(new String[] { "hasRequested", username, currentUser.getUsername() });
                                        hasRequestedStatus = (String) hasRequestedResponse[0];
                                        if (hasRequestedStatus.equals("success")) {
                                            if ((boolean) hasRequestedResponse[1]) {
                                                // if they have requested us, allow option to accept or decline request
                                                acceptFriendRequest.setVisible(true);
                                                declineFriendRequest.setVisible(true);
                                            } else  {
                                                // they have not requested us, we haven't requested them
                                                // Allow option to send a friend request
                                                requestFriend.setVisible(true);
                                            }
                                        } else {
                                            showConnectionError();
                                            profileWindow.setVisible(false);
                                        }
                                    }
                                } else {
                                    showConnectionError();
                                    profileWindow.setVisible(false);
                                }
                            }
                        } else {
                            showConnectionError();
                            profileWindow.setVisible(false);
                        }
                    }
                    profileWindow.pack();
                } else if (status.equals("usernameNotFound")) {
                    
                    JOptionPane.showMessageDialog(null, "Username " + username + " could no longer be found!", "Error", JOptionPane.ERROR_MESSAGE);
                    profileWindow.setVisible(false);
                } else {
                    showConnectionError();
                    profileWindow.setVisible(false);
                }
                // delay refershing by refreshRate (in milliseconds)
                try {
                    Thread.sleep(refreshRate);
                } catch (InterruptedException e) {
                    profileWindow.setVisible(false);
                }
            }
        }).start();
    }

    /*
        * Shows the window to edit current user's profile
        * Note: this only allows the user to edit ONLY their own profile
        * unlike the other functions that can display for different users
    */
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
        JAButton saveChangesButton = new JAButton("Save Changes", Action.UpdateAccount);
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

    /*
        * Friends list window shows a list of the user's current friends,
        * friend requests, and requested friends.
        * Has realtime updates (based on refresh rate)
    */
    public static void showFriendsList(String username) {
        JFrame friendsList = new JFrame();
        Container content = friendsList.getContentPane();
        content.setLayout(new BorderLayout());

        JLabel header = new JLabel("Friends List of ", SwingConstants.CENTER);
        header.setFont(titleFont);
        content.add(header, BorderLayout.NORTH);

        // this panel is common for all users
        JPanel currentFriendsPanel = new JPanel();
        currentFriendsPanel.setBorder(padding);
        currentFriendsPanel.setLayout(new BoxLayout(currentFriendsPanel, BoxLayout.Y_AXIS));
        content.add(currentFriendsPanel, BorderLayout.WEST);

        // instantating friend requests panel. Only shown if it is for current user
        JPanel friendRequestsPanel = new JPanel();
        friendRequestsPanel.setBorder(padding);
        friendRequestsPanel.setLayout(new BoxLayout(friendRequestsPanel, BoxLayout.Y_AXIS));
        content.add(friendRequestsPanel, BorderLayout.CENTER);
        friendRequestsPanel.setVisible(false);

        // instantiating requested friends panel. Only shown if it is for current user
        JPanel requestedFriendsPanel = new JPanel();
        requestedFriendsPanel.setBorder(padding);
        requestedFriendsPanel.setLayout(new BoxLayout(requestedFriendsPanel, BoxLayout.Y_AXIS));
        content.add(requestedFriendsPanel, BorderLayout.EAST);
        requestedFriendsPanel.setVisible(false);

        friendsList.setTitle("Friends List");
        friendsList.setSize(600, 700);
        friendsList.setLocationRelativeTo(null);
        friendsList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        friendsList.setVisible(true);

        // continuously refresh the content of window until it is closed
        new Thread(() -> {
            while (friendsList.isVisible() && !deletingAccount) {
                Object[] response = sendToServer(new String[] { "getUser", username });
                String status = (String) response[0];
                if (status.equals("success")) {
                    Account user = (Account) response[1];
                    header.setText("Friends List of: " + username);
                    // remove all children
                    currentFriendsPanel.removeAll();
                    // add the header
                    JLabel friendsHeader = new JLabel("Friends", SwingConstants.CENTER);
                    friendsHeader.setFont(subTitleFont);
                    currentFriendsPanel.add(friendsHeader, BorderLayout.NORTH);
                    // add all friend profile buttons
                    if (user.getFriends().size() != 0) {
                        for (Account friend : user.getFriends()) {
                            JAButton friendButton = new JAButton(friend.getUsername(), friend.getUsername(), Action.ViewProfile);
                            friendButton.addActionListener(actionListener);
                            currentFriendsPanel.add(friendButton, BorderLayout.CENTER);
                        }
                    } else {
                        JLabel emptyList = new JLabel("No friends :(");
                        currentFriendsPanel.add(emptyList);
                    }
                    // tell Swing that things were updated and needs to be redrawn
                    currentFriendsPanel.revalidate();
                    currentFriendsPanel.repaint();

                    friendRequestsPanel.setVisible(false);
                    requestedFriendsPanel.setVisible(false);
                    // if we are viewing the current user, show their friend requests and requested friends
                    if (user.getUsername().equals(currentUser.getUsername())) {
                        // set up friend requests panel
                        friendRequestsPanel.setVisible(true);
                        friendRequestsPanel.removeAll();
                        // add header
                        JLabel friendRequestsHeader = new JLabel("Friend Requests", SwingConstants.CENTER);
                        friendRequestsHeader.setFont(subTitleFont);
                        friendRequestsPanel.add(friendRequestsHeader);
                        // displaying all friend requests
                        if (user.getFriendRequests().size() != 0) {
                            for (Account friendRequest : user.getFriendRequests()) {
                                JAButton friendButton = new JAButton(friendRequest.getUsername(), friendRequest.getUsername(), Action.ViewProfile);
                                friendButton.addActionListener(actionListener);
                                friendRequestsPanel.add(friendButton, BorderLayout.CENTER);
                            }
                        } else {
                            JLabel emptyList = new JLabel("You currently have no incoming friend requests.");
                            friendRequestsPanel.add(emptyList);
                        }
                        friendRequestsPanel.revalidate();
                        friendRequestsPanel.repaint();

                        // set up requested friends panel
                        requestedFriendsPanel.setVisible(true);
                        requestedFriendsPanel.removeAll();
                        // add header
                        JLabel requestedFriendHeader = new JLabel("Friend Requests", SwingConstants.CENTER);
                        friendRequestsHeader.setFont(subTitleFont);
                        requestedFriendsPanel.add(requestedFriendHeader);
                        // displaying all requested friends
                        if (user.getRequestedFriends().size() != 0) {
                            for (Account requestedFriend : user.getRequestedFriends()) {
                                JAButton friendButton = new JAButton(requestedFriend.getUsername(), requestedFriend.getUsername(), Action.ViewProfile);
                                friendButton.addActionListener(actionListener);
                                requestedFriendsPanel.add(friendButton, BorderLayout.CENTER);
                            }
                        } else {
                            JLabel emptyList = new JLabel("You currently have no outgoing friend requests.");
                            requestedFriendsPanel.add(emptyList);
                        }
                        requestedFriendsPanel.revalidate();
                        requestedFriendsPanel.repaint();
                    }
                    friendsList.pack();
                } else if (status.equals("usernameNotFound")) {
                    JOptionPane.showMessageDialog(null, "Username " + username + " could not be found!", "Error", JOptionPane.ERROR_MESSAGE);
                    friendsList.setVisible(false);
                } else {
                    showConnectionError();
                    friendsList.setVisible(false);
                }
                // delay refresh by refreshRate (in milliseconds)
                try {
                    Thread.sleep(refreshRate);
                } catch (InterruptedException e) {
                    friendsList.setVisible(false);
                }
            }
        }).start();
    }
    
    /*
        Window to show the search bar and search button, but NOT the search results
    */
    public static void showSearchMenu() {
        // initial setup, layout, and padding
        JFrame searchMenu = new JFrame();
        Container content = searchMenu.getContentPane();
        JPanel panel = new JPanel();
        panel.setBorder(padding);
        panel.setLayout(new BorderLayout());

        // title header
        JLabel header = new JLabel("Search for Users", SwingConstants.CENTER);
        header.setFont(titleFont);
        panel.add(header, BorderLayout.NORTH);

        // search panel. Shows label, search bar, and search button
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBorder(padding);
        searchBarPanel.setLayout(new BoxLayout(searchBarPanel, BoxLayout.X_AXIS));
        JLabel searchTitle = new JLabel("Enter username to search, or leave blank to list all users:");
        searchField = new JTextField("", 15);
        JAButton searchButton = new JAButton("Search", Action.SearchUsers);
        searchButton.addActionListener(actionListener);
        searchBarPanel.add(searchTitle);
        searchBarPanel.add(Box.createHorizontalStrut(10));
        searchBarPanel.add(searchField);
        searchBarPanel.add(Box.createHorizontalStrut(10));
        searchBarPanel.add(searchButton);

        // finalizing and then showing the window
        panel.add(searchBarPanel, BorderLayout.CENTER);
        content.add(panel);
        searchMenu.setTitle("Search Menu");
        searchMenu.setSize(600, 400);
        searchMenu.setLocationRelativeTo(null);
        searchMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchMenu.pack();
        searchMenu.setVisible(true);
    }

    /*
        * Window to show the search results (comes after user has pressed the search button)
        * Has real time updates (based on refresh rate)
    */
    public static void showSearchResults(String searchWord) {
        // setting up the window, layout, and padding
        JFrame resultsWindow = new JFrame();
        Container content = resultsWindow.getContentPane();
        JPanel panel = new JPanel();
        panel.setBorder(padding);
        panel.setLayout(new BorderLayout());
        // setting up title
        JLabel header = new JLabel("Search Results for: " + searchWord, SwingConstants.CENTER);
        header.setFont(titleFont);
        panel.add(header, BorderLayout.NORTH);
        // setting up grid panel to display users
        JPanel resultsPanel = new JPanel();
        resultsPanel.setBorder(padding);
        resultsPanel.setLayout(new GridLayout(0, 4));
        panel.add(resultsPanel, BorderLayout.CENTER);
        // showing window
        content.add(panel);
        resultsWindow.setTitle("Search Results");
        resultsWindow.setSize(600, 400);
        resultsWindow.setLocationRelativeTo(null);
        resultsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultsWindow.setVisible(true);

        // continuously refresh the content of window until it is closed
        new Thread(() -> {
            while (resultsWindow.isVisible() && !deletingAccount) {
                Object response[] = sendToServer(new String[] { "searchUsers", searchWord });
                String status = (String) response[0];
                if (status.equals("success")) {
                    // clear previous results
                    resultsPanel.removeAll();
                    ArrayList<Account> users = (ArrayList<Account>) response[1];
                    // If there are results, show them. Otherwise, no results, show a simple message
                    if (users.size() > 0) {
                        // display all users
                        for (Account user : users) {
                            JAButton userButton = new JAButton(user.getUsername(), user.getUsername(), Action.ViewProfile);
                            userButton.addActionListener(actionListener);
                            resultsPanel.add(userButton);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No users found!", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                        resultsWindow.setVisible(false);
                    }
                    resultsPanel.revalidate();
                    resultsPanel.repaint();
                    resultsWindow.pack();
                } else {
                    showConnectionError();
                    resultsWindow.setVisible(false);
                }
                // delay refresh by refreshRate (in milliseconds)
                try {
                    Thread.sleep(refreshRate);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        }).start();
    }

    // shown whenever we are unable to connect to the server
    public static void showConnectionError() {
        JOptionPane.showMessageDialog(null, "Could not connect to server. Please try again later!", "Connection Error!", JOptionPane.ERROR_MESSAGE);
    }

    // try to send data. If connection failed, return connectionFailed status code
    public static Object[] sendToServer(Object[] request) {
        try {
            socket = new Socket(serverHost, serverPort);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            writer.writeObject(request);
            Object[] response = (Object[]) reader.readObject();

            socket.close();
            reader.close();
            writer.close();
            return response;
        } catch (UnknownHostException e) {
            return new String[] { "connectionFailed" };
        } catch (IOException e) {
            return new String[] { "connectionFailed" };
        } catch (ClassNotFoundException e) {
            return new String[] { "connectionFailed" };
        } finally {
            try {
                socket.close();
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}